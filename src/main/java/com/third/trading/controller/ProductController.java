package com.third.trading.controller;

import com.third.trading.bean.Product;
import com.third.trading.bean.request.ProductApplyRequest;
import com.third.trading.bean.request.ProductCheckRequest;
import com.third.trading.bean.request.ProductListRequest;
import com.third.trading.bean.response.UnifiedResult;
import com.third.trading.bean.response.UnifiedResultBuilder;
import com.third.trading.service.IProductService;
import com.third.trading.util.Constants;
import com.third.trading.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/operation/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    /**
     * 项目发布列表和统计列表
     */
    @PostMapping("/list/publish")
    public UnifiedResult<List<Product>> listProduct(ProductListRequest request){

        List<Product> productList = productService.listPublishProduct(request);
        if(productList != null && productList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productList, Page.toPage(productList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    /**
     * 项目接单确认列表
     */
    @PostMapping("/list/choose")
    public UnifiedResult<List<Product>> listChooseProduct(ProductListRequest request){

        List<Product> productList = productService.listChooseProduct(request);
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

    /**
     * 项目验收列表
     */
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
    public UnifiedResult checkProduct(ProductCheckRequest request){

        Product product = productService.findByProductId(request.getProductId());
        if(product != null){
            if(!Constants.ProductState.WAIT_CHECK.equals(product.getStatus())){
                return UnifiedResultBuilder.errorResult(Constants.PRODUCT_STATE_ERROR_CODE,
                        Constants.PRODUCT_STATE_ERROR_MESSAGE);
            }
            if(StringUtils.isEmpty(request.getRealCost())){
                product.setRealCost(product.getBudget());
            }else{
                product.setRealCost(new BigDecimal(request.getRealCost()));
            }
            product.setCheckDesc(request.getCheckDesc());
            product.setStatus(Constants.ProductState.ALREADLY_CHECKED);
            if(productService.updateProduct(product)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @PostMapping("/revoke")
    public UnifiedResult revokeProduct(Integer productId){

        Product product = productService.findByProductId(productId);
        if(product != null){
            //项目待接单状态才能撤回
            if(Constants.ProductState.WAIT_ORDER.equals(product.getStatus())){
                if(productService.revokeProduct(productId)){
                    return UnifiedResultBuilder.defaultSuccessResult();
                }
            }
            return UnifiedResultBuilder.errorResult(Constants.PRODUCT_STATE_ERROR_CODE,
                    Constants.PRODUCT_STATE_ERROR_MESSAGE);
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @PostMapping("/find")
    public UnifiedResult findProduct(Integer productId){

        Product product = productService.findByProductId(productId);
        if(product != null){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, product);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/stat")
    public UnifiedResult statProduct(){
        return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productService.statProduct());
    }

    @RequestMapping("/choose")
    public UnifiedResult chooseUser(Integer productId, Integer userId , Integer orderId){

        if(productService.chooseUser(productId, userId, orderId)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @PostMapping("/apply")
    public UnifiedResult productApply(ProductApplyRequest request){

        Product product = productService.findByProductId(request.getProductId());
        if(product != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            product.setStatus(Constants.ProductState.WAIT_CHECK);
            product.setDeliveryDesc(request.getDeliveryDesc());
            product.setRealDeliveryTime(format.format(new Date()));
            if(productService.applyProduct(product)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
