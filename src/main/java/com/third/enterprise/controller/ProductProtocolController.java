package com.third.enterprise.controller;

import com.third.enterprise.bean.ProductProtocol;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IProtocolService;
import com.third.enterprise.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/operation/protocol")
public class ProductProtocolController {

    private static final Logger logger = LoggerFactory.getLogger(ProductProtocolController.class);

    @Autowired
    private IProtocolService protocolService;

    @GetMapping("/find")
    public UnifiedResult queryProtocol(Integer productId){

        if(productId != null){
            ProductProtocol productProtocol = protocolService.selectByProductId(productId);
            if(productProtocol != null){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productProtocol);
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    @PostMapping(value = "/save")
    public UnifiedResult saveProtocol(@RequestBody ProductProtocol productProtocol){

        if(productProtocol != null){
            if(protocolService.saveProtocol(productProtocol)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

}
