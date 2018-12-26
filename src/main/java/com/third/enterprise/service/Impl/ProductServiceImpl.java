package com.third.enterprise.service.Impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.dao.ProductMapper;
import com.third.enterprise.service.IProductService;
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
    private ProductMapper productMapper;

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
        if(productMapper.insertSelective(product) > 0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProduct(Product product) {
        if(productMapper.updateByPrimaryKeySelective(product) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Product findByProductId(Integer productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public boolean updateStatus(Integer productId, String status) {
        if(productMapper.updateProductStatus(productId, status) > 0){
            return true;
        }
        return false;
    }
}
