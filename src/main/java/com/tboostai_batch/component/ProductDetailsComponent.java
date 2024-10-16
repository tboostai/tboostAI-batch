package com.tboostai_batch.component;

import com.tboostai_batch.entity.ebay.dto.EbayRespBasicDTO;
import com.tboostai_batch.service.RedisService;
import com.tboostai_batch.util.CommonTools;
import com.tboostai_batch.util.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;

import static com.tboostai_batch.common.GeneralConstants.*;

@Component
public class ProductDetailsComponent {

    private static final Logger logger = LoggerFactory.getLogger(ProductDetailsComponent.class);
    private final WebClientUtils webClientUtils;
    private final RedisService redisService;

    @Value("${EBAY_SEARCH_BY_ITEM_ID}")
    private String ebayGetItemByIdApiUrl;
    @Value("${EBAY_HOST}")
    private String ebayHost;

    public ProductDetailsComponent(WebClientUtils webClientUtils, RedisService redisService) {
        this.webClientUtils = webClientUtils;
        this.redisService = redisService;
    }

    public List<EbayRespBasicDTO> getProductDetails(String accessToken) {

        List<String> currentItemIds = redisService.getCurrentBatch();

        logger.info("{} items stored by step 2", currentItemIds.size());

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTPS)
                .host(ebayHost);


        HttpHeaders headers = CommonTools.generateEbayBasicHeaders(accessToken);

        // 创建每个 itemId 的请求 Mono
        List<Mono<EbayRespBasicDTO>> requestMonos = currentItemIds.stream()
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(itemId -> {
                    UriComponentsBuilder globalUriComponentBuilder = uriComponentsBuilder.cloneBuilder();
                    String currentPath = ebayGetItemByIdApiUrl.concat(itemId);
                    String currentUrl = globalUriComponentBuilder.path(currentPath).build(false).toUriString();

                    return webClientUtils.sendGetRequestExternal(currentUrl, EbayRespBasicDTO.class, headers, 3, 3);
                })
                .toList();

        logger.info("Requested EbayRespBasicDTO size is {}", requestMonos.size());

        // 使用 Mono.zip 或 Flux 合并所有请求
        Mono<List<EbayRespBasicDTO>> allResultsMono = Flux.fromIterable(requestMonos)
                .flatMap(mono -> mono)
                .collectList(); // 收集所有的 Mono 到一个 List

        // 返回或处理所有获取到的产品信息
        List<EbayRespBasicDTO> ebayRespBasicDTOS = allResultsMono.block();

        // Store to redis
        if (Objects.nonNull(ebayRespBasicDTOS) && !ebayRespBasicDTOS.isEmpty()) {
            redisService.storeItemDetailsInRedis(ebayRespBasicDTOS);
        }
        return ebayRespBasicDTOS;
    }
}
