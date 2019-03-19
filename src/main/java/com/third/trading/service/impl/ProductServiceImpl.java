package com.third.trading.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.trading.bean.Order;
import com.third.trading.bean.Product;
import com.third.trading.bean.request.ProductListRequest;
import com.third.trading.bean.response.ProductStatResponse;
import com.third.trading.dao.OrderMapper;
import com.third.trading.dao.ProductMapper;
import com.third.trading.service.GenerateProductNumberService;
import com.third.trading.service.IProductService;
import com.third.trading.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private GenerateProductNumberService generateProductNumberService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Product> listPublishProduct(ProductListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return productMapper.listProduct(request);
    }

    @Override
    public List<Product> listCheckProduct(ProductListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return productMapper.listCheckProduct(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveProduct(Product product) {
        product.setProductId(generateProductNumberService.generateProductNumberNumber());
        product.setStatus("1");
        product.setPublishStatus("1");
        if(productMapper.insertSelective(product) > 0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProduct(Product product) {
        if(productMapper.updateByPrimaryKeySelective(product) > 0){
            if(orderMapper.updateOrderStatus(product.getOrderId(), Constants.OrderState.ALREADLY_CHECKED) > 0){
                 return true;
            }
        }
        return false;
    }

    @Override
    public Product findByProductId(Integer productId) {
        return productMapper.selectByProductId(productId);
    }

    @Override
    public boolean updateStatus(Integer productId, String status) {
        if(productMapper.updateProductStatus(productId, status) > 0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean chooseUser(Integer productId, Integer userId, Integer orderId) {

        Product product = productMapper.selectByProductId(productId);
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(product != null && order != null){
            product.setOrderId(orderId);
            product.setOwnerId(userId);
            product.setExpectCost(order.getExpectCost());
            product.setStatus(Constants.ProductState.ON_DOING);
            if(productMapper.updateByPrimaryKeySelective(product) > 0){
                if(orderMapper.updateOrderStatus(orderId , Constants.OrderState.CHECKED) > 0){
                    orderMapper.failedOtherOrder(productId, userId);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean revokeProduct(Integer productId) {
        if(productMapper.updateProductStatus(productId, Constants.PublishState.REVOKE) > 0){
            return true;
        }
        return false;
    }

    @Override
    public ProductStatResponse statProduct() {
        return productMapper.statProduct();
    }

    @Override
    public List<Product> listChooseProduct(ProductListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return productMapper.listChooseProduct(request);
    }

    @Override
    public boolean applyProduct(Product product) {
        if(productMapper.updateByPrimaryKeySelective(product) > 0){
             if(orderMapper.updateOrderStatus(product.getOrderId(), Constants.OrderState.WAIT_CHECK) > 0){
                  return true;
             }
        }
        return false;
    }
}
