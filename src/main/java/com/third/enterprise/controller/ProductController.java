package com.third.enterprise.controller;

import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IProductService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operation/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    @PostMapping("/list/publish")
    public UnifiedResult<List<Product>> listProduct(ProductListRequest request){

        List<Product> productList = productService.listPublishProduct(request);
        if(productList != null && productList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productList, Page.toPage(productList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/save")
    public UnifiedResult saveProduct(@Valid Product product,
                                     BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
        }

        if(product.getId() == null || product.getId() == 0){
            if(productService.saveProduct(product)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }else{
            if(productService.updateProduct(product)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @PostMapping("/list/check")
    public UnifiedResult<List<Product>> checkProductList(ProductListRequest request){

        List<Product> productList = productService.listCheckProduct(request);
        if(productList != null && productList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productList, Page.toPage(productList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/check")
    public UnifiedResult checkProduct(Integer productId){

        Product product = productService.findByProductId(productId);
        if(product != null){
            if(!Constants.ProductState.WAIT_CHECK.equals(product.getStatus())){
                return UnifiedResultBuilder.errorResult(Constants.PRODUCT_STATE_ERROR_CODE,
                        Constants.PRODUCT_STATE_ERROR_MESSAGE);
            }
            if(productService.updateStatus(productId, Constants.ProductState.ALREADLY_CHECKED)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

}
