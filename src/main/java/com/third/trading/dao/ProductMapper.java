package com.third.trading.dao;

import com.third.trading.bean.Product;
import com.third.trading.bean.request.ProductListRequest;
import com.third.trading.bean.response.ProductStatResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByProductId(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> listProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    Integer updateProductStatus(@Param("productId") Integer productId,
                                @Param("status") String status);

    ProductStatResponse statProduct();

    List<Product> listChooseProduct(ProductListRequest request);
}