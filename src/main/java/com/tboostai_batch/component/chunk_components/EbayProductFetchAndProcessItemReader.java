package com.tboostai_batch.component.chunk_components;

import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EbayProductFetchAndProcessItemReader implements ItemReader<List<EbayRespBasicDTO>> {
    private static final Logger logger = LoggerFactory.getLogger(EbayProductFetchAndProcessItemReader.class);

    private final RedisService redisService;

    public EbayProductFetchAndProcessItemReader(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public List<EbayRespBasicDTO> read() {
        logger.info("Read Product Data Chunk");
        List<EbayRespBasicDTO> currentEbayEntities = redisService.getItemDetailsFromRedis();
        if (currentEbayEntities == null || currentEbayEntities.isEmpty()) {
            logger.info("Failed to get current ebay entities from redis because of null data");
            return null;
        }
        logger.info("{} ebay entities from redis", currentEbayEntities.size());
        return currentEbayEntities;
    }
}
