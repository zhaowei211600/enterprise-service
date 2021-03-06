package com.third.enterprise.service;

import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.request.ProductListRequest;

import java.util.List;

public interface IProductService {

    List<Product> listPublishProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    boolean saveProduct(Product product);

    boolean updateProduct(Product product);

    Product findByProductId(Integer productId);

    boolean updateStatus(Integer productId, String status);
}
