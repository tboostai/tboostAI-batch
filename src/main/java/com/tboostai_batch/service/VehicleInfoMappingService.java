package com.tboostai_batch.service;

import com.tboostai_batch.common.VehicleSource;
import com.tboostai_batch.common.VehicleSpecificsField;
import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.entity.ebay.dto.EbayRespImageDTO;
import com.tboostai_batch.entity.ebay.dto.EbayRespLocalizedAspectDTO;
import com.tboostai_batch.entity.inner_model.FormattedDescription;
import com.tboostai_batch.entity.inner_model.VehicleBasicInfo;
import com.tboostai_batch.mapper.ebay.*;
import com.tboostai_batch.util.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.tboostai_batch.util.CommonTools.parseInteger;

@Service
public class VehicleInfoMappingService {

    private final LocationMapper locationMapper;

    private final VehicleImageMapper vehicleImageMapper;

    private final PaymentInfoMapper paymentInfoMapper;

    private final EbaySellerMapper ebaySellerMapper;

    private final TaxMapper taxMapper;

    private final VehicleDescriptionService descriptionService;

    public VehicleInfoMappingService(LocationMapper locationMapper, VehicleImageMapper vehicleImageMapper, PaymentInfoMapper paymentInfoMapper, EbaySellerMapper ebaySellerMapper, TaxMapper taxMapper, VehicleDescriptionService descriptionService) {
        this.locationMapper = locationMapper;
        this.vehicleImageMapper = vehicleImageMapper;
        this.paymentInfoMapper = paymentInfoMapper;
        this.ebaySellerMapper = ebaySellerMapper;
        this.taxMapper = taxMapper;
        this.descriptionService = descriptionService;
    }

    public void mapLocalizedAspects(VehicleBasicInfo vehicleBasicInfo, EbayRespBasicDTO ebayRespBasicDTO) {
        List<EbayRespLocalizedAspectDTO> localizedAspects = ebayRespBasicDTO.getLocalizedAspects();

        CommonTools.setIfNotNull(vehicleBasicInfo::setMake, getValueByEnum(localizedAspects, VehicleSpecificsField.MAKE));
        CommonTools.setIfNotNull(vehicleBasicInfo::setModel, getValueByEnum(localizedAspects, VehicleSpecificsField.MODEL));
        CommonTools.setIfNotNull(vehicleBasicInfo::setYear, parseInteger(getValueByEnum(localizedAspects, VehicleSpecificsField.YEAR)));
        CommonTools.setIfNotNull(vehicleBasicInfo::setTrim, getValueByEnum(localizedAspects, VehicleSpecificsField.TRIM));
        CommonTools.setIfNotNull(vehicleBasicInfo::setVin, getValueByEnum(localizedAspects, VehicleSpecificsField.VIN));
        CommonTools.setIfNotNull(vehicleBasicInfo::setMileage, parseInteger(getValueByEnum(localizedAspects, VehicleSpecificsField.MILEAGE)));
        CommonTools.setIfNotNull(vehicleBasicInfo::setExteriorColor, getValueByEnum(localizedAspects, VehicleSpecificsField.EXTERIOR_COLOR));
        CommonTools.setIfNotNull(vehicleBasicInfo::setInteriorColor, getValueByEnum(localizedAspects, VehicleSpecificsField.INTERIOR_COLOR));
        CommonTools.setIfNotNull(vehicleBasicInfo::setBodyType, getValueByEnum(localizedAspects, VehicleSpecificsField.BODY_TYPE));
        CommonTools.setIfNotNull(vehicleBasicInfo::setCylinder, parseInteger(getValueByEnum(localizedAspects, VehicleSpecificsField.CYLINDER)));
        CommonTools.setIfNotNull(vehicleBasicInfo::setTransmission, getValueByEnum(localizedAspects, VehicleSpecificsField.TRANSMISSION));
        CommonTools.setIfNotNull(vehicleBasicInfo::setDrivetrain, getValueByEnum(localizedAspects, VehicleSpecificsField.DRIVETRAIN));
        CommonTools.setIfNotNull(vehicleBasicInfo::setEngineType, getValueByEnum(localizedAspects, VehicleSpecificsField.ENGINE_TYPE));
        CommonTools.setIfNotNull(vehicleBasicInfo::setCylinderInfo, getValueByEnum(localizedAspects, VehicleSpecificsField.CYLINDER_INFO));
        CommonTools.setIfNotNull(vehicleBasicInfo::setWarranty, getValueByEnum(localizedAspects, VehicleSpecificsField.WARRANTY));
        CommonTools.setIfNotNull(vehicleBasicInfo::setDoors, CommonTools.parseInteger(getValueByEnum(localizedAspects, VehicleSpecificsField.DOORS)));
        CommonTools.setIfNotNull(vehicleBasicInfo::setVehicleTitle, getValueByEnum(localizedAspects, VehicleSpecificsField.TITLE));

        // 使用 Service 生成描述
        FormattedDescription description = descriptionService.generateDescription(ebayRespBasicDTO.getDescription());
        if (description != null) {
            vehicleBasicInfo.setDescription(description.getOriginalDescription());
            vehicleBasicInfo.setAiDescription(description.getSummarized());
            vehicleBasicInfo.setExtractedFeatures(description.getExtractedFeatures());
        }
        vehicleBasicInfo.setCondition(ebayRespBasicDTO.getCondition());
        vehicleBasicInfo.setSourceId(VehicleSource.EBAY.getId());
    }

    // 获取Enum对应的值
    private String getValueByEnum(List<EbayRespLocalizedAspectDTO> aspects, VehicleSpecificsField field) {
        return aspects.stream()
                .filter(aspect -> aspect.getName().equalsIgnoreCase(field.getAspectName()))
                .map(EbayRespLocalizedAspectDTO::getValue)
                .findFirst()
                .orElse(null);
    }
}

