package com.third.enterprise.service;

import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.ProductAttachment;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.bean.response.ProductStatResponse;

import java.util.List;

public interface IProductService {

    List<Product> listPublishProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    boolean saveProduct(Product product);

    boolean updateProduct(Product product);

    Product findByProductId(Integer productId);

    boolean updateStatus(Integer productId, String status);

    boolean chooseUser(Integer[] orderIdList);

    boolean revokeProduct(Integer productId);

    ProductStatResponse statProduct();

    List<Product> listChooseProduct(ProductListRequest request);

    boolean applyProduct(Product product);

    boolean saveAttachment(ProductAttachment attachment);

}
