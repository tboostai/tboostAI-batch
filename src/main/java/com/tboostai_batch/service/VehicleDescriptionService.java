package com.tboostai_batch.service;

import com.tboostai_batch.entity.inner_model.FormattedDescription;
import com.tboostai_batch.util.CommonTools;
import com.tboostai_batch.util.WebClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleDescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDescriptionService.class);
    private final WebClientUtils webClientUtils;
    private final String tboostAILlmHost;

    public VehicleDescriptionService(WebClientUtils webClientUtils,
                                     @Value("${Spring.microserver.service.tboostai.llm.host}") String tboostAILlmHost) {
        this.webClientUtils = webClientUtils;
        this.tboostAILlmHost = tboostAILlmHost;
    }

    public FormattedDescription generateDescription(String originalDescriptionText) {
        if (originalDescriptionText == null || originalDescriptionText.isEmpty()) {
            return null;
        }
        String descObj = CommonTools.getDescriptionFromHTML(originalDescriptionText);
        logger.info("Description Object is {}", descObj);
        Map<String, String> request = new HashMap<>();
        request.put("description", descObj);
        Mono<String> respFromLlmService = webClientUtils.sendPostRequestInternal(tboostAILlmHost, request, String.class);

        String respStr = respFromLlmService.block();
        logger.info("Response from llm service : {}", respStr);
        return CommonTools.jsonStringToObj(respStr, FormattedDescription.class);
    }
}
