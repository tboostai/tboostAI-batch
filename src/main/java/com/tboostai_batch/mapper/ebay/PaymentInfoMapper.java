package com.tboostai_batch.mapper.ebay;

import com.tboostai_batch.entity.db_model.PaymentInfoEntity;
import com.tboostai_batch.entity.db_model.VehicleBasicInfoEntity;
import com.tboostai_batch.entity.ebay.dto.EbayRespPaymentMethodDTO;
import com.tboostai_batch.entity.inner_model.PaymentInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentInfoMapper {

    @Mapping(target = "paymentMethodType", source = "ebayRespPaymentMethodDTO.paymentMethodType")
    @Mapping(target = "paymentInstructions", source = "ebayRespPaymentMethodDTO.paymentInstructions")
    PaymentInfo toPaymentInfo(EbayRespPaymentMethodDTO ebayRespPaymentMethodDTO);

    List<PaymentInfo> toPaymentInfos(List<EbayRespPaymentMethodDTO> ebayRespPaymentMethodDTOS);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "paymentInstructions", expression = "java(com.tboostai_batch.util.CommonTools.mapListToString(paymentInfo.getPaymentInstructions()))")
    PaymentInfoEntity toPaymentInfoEntity(PaymentInfo paymentInfo);

    List<PaymentInfoEntity> toPaymentInfoEntities(List<PaymentInfo> paymentInfos);
}
