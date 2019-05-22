package com.third.enterprise.controller;

import com.third.enterprise.bean.*;
import com.third.enterprise.bean.request.ProductApplyRequest;
import com.third.enterprise.bean.request.ProductCheckRequest;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.ICheckOrderService;
import com.third.enterprise.service.IOrderService;
import com.third.enterprise.service.IProductService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICheckOrderService checkOrderService;

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
    /*@PostMapping("/list/check")
    public UnifiedResult<List<Product>> checkProductList(ProductListRequest request){

        List<Product> productList = productService.listCheckProduct(request);
        if(productList != null && productList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, productList, Page.toPage(productList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }*/

    /*@PostMapping("/check")
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
    }*/

    //下架
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
    public UnifiedResult chooseUser(@RequestParam("orderIds[]") Integer[] orderIds, Integer productId){

        if(StringUtils.isEmpty(orderIds)){
            return UnifiedResultBuilder.errorResult(Constants.PARAMETER_NOT_VALID_ERROR_CODE,
                    Constants.PARAMETER_NOT_VALID_ERROR_MESSAGE);
        }
        //String[] orderIdList = orderIds.split(",");
        if(productService.chooseUser(orderIds, productId)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    /***
     * 结项申请
     */
    @PostMapping("/apply")
    public UnifiedResult productApply(ProductApplyRequest request){

        Order order = orderService.selectById(request.getOrderId());
        if(order == null){
            return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE,
                    Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
        }

        //TODO 保存结算信息
        Product product = productService.findByProductId(request.getProductId());
        if(product != null){
            CheckOrder checkOrder = new CheckOrder();
            checkOrder.setUserId(order.getUserId());
            checkOrder.setProductId(product.getId());
            checkOrder.setOrderId(request.getOrderId());
            checkOrder.setFinishDesc(request.getDeliveryDesc());

            if(checkOrderService.hasOpenCheckOrder(request.getOrderId())){
                return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                        "当前订单有待审核的验收记录！");
            }
            //持续性服务，可多次提交
            if("1".equals(product.getAttr())){
                checkOrderService.saveCheckOrder(checkOrder);
            }else if("2".equals(product.getAttr())){
                //一次性服务，进行中才能提交
                if(Constants.ProductState.ON_DOING.equals(product.getStatus())){
                    checkOrderService.saveCheckOrder(checkOrder);
                }else{
                    return UnifiedResultBuilder.errorResult(Constants.PRODUCT_STATE_ERROR_CODE,
                            Constants.PRODUCT_STATE_ERROR_MESSAGE);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //product.setStatus(Constants.ProductState.WAIT_CHECK);
            product.setDeliveryDesc(request.getDeliveryDesc());
            product.setRealDeliveryTime(format.format(new Date()));
            if(productService.applyProduct(product)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @GetMapping("/file/disable")
    public UnifiedResult disableAttachment(String filePath){

        if(!StringUtils.isEmpty(filePath) && productService.disableAttachment(filePath)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    @GetMapping("/file/list")
    public UnifiedResult listFile(Integer productId){

        if(!StringUtils.isEmpty(productId)){
            List<ProductAttachment> fileList = productService.listFile(productId);
            if(fileList != null && fileList.size() > 0){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, fileList);
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    @GetMapping("/close")
    public UnifiedResult closeProduct(Integer productId){

        if(!StringUtils.isEmpty(productId) && productService.closeProduct(productId)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }
}
