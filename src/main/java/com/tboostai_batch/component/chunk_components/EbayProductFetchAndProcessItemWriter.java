package com.tboostai_batch.component.chunk_components;

import com.tboostai_batch.config.MapperManager;
import com.tboostai_batch.config.RepoManager;
import com.tboostai_batch.entity.db_model.*;
import com.tboostai_batch.entity.inner_model.EbayCompleteInfo;
import com.tboostai_batch.mapper.ebay.*;
import com.tboostai_batch.repo.*;
import com.tboostai_batch.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class EbayProductFetchAndProcessItemWriter implements ItemWriter<List<EbayCompleteInfo>> {

    private static final Logger logger = LoggerFactory.getLogger(EbayProductFetchAndProcessItemWriter.class);
    private final RedisService redisService;
    private final MapperManager mapperManager;
    private final RepoManager repoManager;

    public EbayProductFetchAndProcessItemWriter(RedisService redisService, MapperManager mapperManager, RepoManager repoManager) {
        this.redisService = redisService;
        this.mapperManager = mapperManager;
        this.repoManager = repoManager;
    }

    @Override
    @Transactional
    public void write(Chunk<? extends List<EbayCompleteInfo>> chunk) {
        logger.info("Write Product Data Chunk");
        List<? extends List<EbayCompleteInfo>> ebayCompleteInfoList = chunk.getItems();

        if (ebayCompleteInfoList.isEmpty()) {
            return;
        }

        // 提前获取所有 Mapper 和 Repo 实例
        AvailabilityMapper availabilityMapper = mapperManager.getMapper(AvailabilityMapper.class);
        AvailabilityRepo availabilityRepo = repoManager.getRepo(AvailabilityRepo.class);

        EbayAdditionalInfoMapper additionalInfoMapper = mapperManager.getMapper(EbayAdditionalInfoMapper.class);
        EbayAdditionalInfoRepo additionalInfoRepo = repoManager.getRepo(EbayAdditionalInfoRepo.class);

        LocationMapper locationMapper = mapperManager.getMapper(LocationMapper.class);
        LocationRepo locationRepo = repoManager.getRepo(LocationRepo.class);

        PaymentInfoMapper paymentInfoMapper = mapperManager.getMapper(PaymentInfoMapper.class);
        PaymentInfoRepo paymentInfoRepo = repoManager.getRepo(PaymentInfoRepo.class);

        VehiclePostInfoMapper postInfoMapper = mapperManager.getMapper(VehiclePostInfoMapper.class);
        PostRepo postRepo = repoManager.getRepo(PostRepo.class);

        EbaySellerMapper sellerMapper = mapperManager.getMapper(EbaySellerMapper.class);
        SellerRepo sellerRepo = repoManager.getRepo(SellerRepo.class);

        TaxMapper taxMapper = mapperManager.getMapper(TaxMapper.class);
        TaxRepo taxRepo = repoManager.getRepo(TaxRepo.class);

        VehicleInfoEntityMapper vehicleInfoMapper = mapperManager.getMapper(VehicleInfoEntityMapper.class);
        VehicleBasicInfoRepo vehicleInfoRepo = repoManager.getRepo(VehicleBasicInfoRepo.class);

        VehiclePriceMapper vehiclePriceMapper = mapperManager.getMapper(VehiclePriceMapper.class);
        VehiclePriceRepo vehiclePriceRepo = repoManager.getRepo(VehiclePriceRepo.class);

        // 批量保存集合初始化
        List<AvailabilityEntity> availabilityEntities = new ArrayList<>();
        List<LocationEntity> locationEntities = new ArrayList<>();
        List<SellerEntity> sellerEntities = new ArrayList<>();
        List<TaxEntity> allTaxEntities = new ArrayList<>();
        List<VehicleBasicInfoEntity> vehicleBasicInfoEntities = new ArrayList<>();
        List<VehiclePriceEntity> allVehiclePriceEntities = new ArrayList<>();
        List<EbayAdditionalInfoEntity> additionalInfoEntities = new ArrayList<>();
        List<PaymentInfoEntity> allPaymentInfoEntities = new ArrayList<>();
        List<PostEntity> postEntities = new ArrayList<>();

        // 处理每个 ebayCompleteInfo 实例并收集数据
        for (List<EbayCompleteInfo> list : ebayCompleteInfoList) {
            if (list == null || list.isEmpty()) {
                continue;
            }

            for (EbayCompleteInfo ebayCompleteInfo : list) {
                if (ebayCompleteInfo == null) {
                    continue;
                }

                // 收集 availability 数据
                if (ebayCompleteInfo.getAvailability() != null) {
                    availabilityEntities.addAll(availabilityMapper.toAvailabilityEntities(ebayCompleteInfo.getAvailability()));
                }

                // 收集 location 数据
                if (ebayCompleteInfo.getLocation() != null) {
                    LocationEntity locationEntity = locationMapper.toLocationEntity(ebayCompleteInfo.getLocation());
                    locationEntities.add(locationEntity);
                }

                // 收集 seller 数据
                if (ebayCompleteInfo.getSeller() != null) {
                    SellerEntity sellerEntity = sellerMapper.toSellerEntity(ebayCompleteInfo.getSeller());
                    sellerEntities.add(sellerEntity);
                }

                // 收集 vehicle 信息和价格
                if (ebayCompleteInfo.getVehicleBasicInfo() != null) {
                    VehicleBasicInfoEntity vehicleBasicInfoEntity = vehicleInfoMapper.toVehicleBasicInfoEntity(ebayCompleteInfo.getVehicleBasicInfo());
                    vehicleBasicInfoEntity.setLocationEntity(locationEntities.getLast());
                    vehicleBasicInfoEntity.setSeller(sellerEntities.getLast());
                    vehicleBasicInfoEntities.add(vehicleBasicInfoEntity);

                    // 设置 Availability 信息
                    if (!availabilityEntities.isEmpty()) {
                        AvailabilityEntity availabilityEntity = availabilityEntities.getLast();
                        availabilityEntity.setVehicleBasicInfo(vehicleBasicInfoEntity);
                        vehicleBasicInfoEntity.setAvailability(availabilityEntity);
                    }

                    // 收集 price 数据并关联 vehicle
                    if (ebayCompleteInfo.getVehiclePrices() != null) {
                        List<VehiclePriceEntity> vehiclePriceEntities = vehiclePriceMapper.toVehiclePriceEntities(ebayCompleteInfo.getVehiclePrices());
                        vehiclePriceEntities.forEach(priceEntity -> priceEntity.setVehicle(vehicleBasicInfoEntity));
                        allVehiclePriceEntities.addAll(vehiclePriceEntities);
                    }
                }

                // 收集 additional info 数据并关联 vehicle
                if (ebayCompleteInfo.getAdditionalInfo() != null) {
                    EbayAdditionalInfoEntity ebayAdditionalInfoEntity = additionalInfoMapper.toEbayAdditionalInfoEntity(ebayCompleteInfo.getAdditionalInfo());
                    ebayAdditionalInfoEntity.setVehicle(vehicleBasicInfoEntities.isEmpty() ? null : vehicleBasicInfoEntities.getLast());
                    additionalInfoEntities.add(ebayAdditionalInfoEntity);
                }

                // 收集 payment info 数据并关联 vehicle
                if (ebayCompleteInfo.getPaymentInfo() != null) {
                    List<PaymentInfoEntity> paymentInfoEntities = paymentInfoMapper.toPaymentInfoEntities(ebayCompleteInfo.getPaymentInfo());
                    if (!vehicleBasicInfoEntities.isEmpty()) {
                        paymentInfoEntities.forEach(paymentInfoEntity -> paymentInfoEntity.setVehicle(vehicleBasicInfoEntities.getLast()));
                    }
                    allPaymentInfoEntities.addAll(paymentInfoEntities);
                }

                // 收集 post info 数据并关联 seller
                if (ebayCompleteInfo.getVehiclePostInfo() != null) {
                    PostEntity postEntity = postInfoMapper.toPostEntity(ebayCompleteInfo.getVehiclePostInfo());
                    if (!sellerEntities.isEmpty()) {
                        postEntity.setSeller(sellerEntities.getLast());
                        postEntity.setVehicle(vehicleBasicInfoEntities.getLast());
                    }
                    postEntities.add(postEntity);
                }

                // 收集 tax 数据并关联 vehicle
                if (ebayCompleteInfo.getTax() != null) {
                    List<TaxEntity> taxEntities = taxMapper.toTaxEntities(ebayCompleteInfo.getTax());
                    taxEntities.forEach(taxEntity -> taxEntity.setVehicle(vehicleBasicInfoEntities.getLast()));
                    if (!taxEntities.isEmpty()) {
                        allTaxEntities.addAll(taxEntities);
                    }
                }

            }
        }

        logger.info("EbayProductFetchAndProcessItemWriter - Total vehicle will be stored in DB: {}", vehicleBasicInfoEntities.size());
        saveEntities(availabilityEntities, availabilityRepo);
        saveEntities(locationEntities, locationRepo);
        saveEntities(sellerEntities, sellerRepo);
        saveEntities(allTaxEntities, taxRepo);
        saveEntities(vehicleBasicInfoEntities, vehicleInfoRepo);
        saveEntities(allVehiclePriceEntities, vehiclePriceRepo);
        saveEntities(additionalInfoEntities, additionalInfoRepo);
        saveEntities(allPaymentInfoEntities, paymentInfoRepo);
        saveEntities(postEntities, postRepo);
        // 清理 Redis 数据
        redisService.clearItemDetailsFromRedis();

    }

    private <T> void saveEntities(List<T> entities, JpaRepository<T, ?> repository) {
        if (entities != null && !entities.isEmpty()) {
            repository.saveAll(entities);
        }
    }
}
