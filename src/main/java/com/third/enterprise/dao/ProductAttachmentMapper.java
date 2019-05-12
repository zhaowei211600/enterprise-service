package com.third.enterprise.dao;


import com.third.enterprise.bean.ProductAttachment;
import org.apache.ibatis.annotations.Param;

public interface ProductAttachmentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductAttachment record);

    int insertSelective(ProductAttachment record);

    ProductAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductAttachment record);

    int updateByPrimaryKey(ProductAttachment record);

    boolean enableAttachment(@Param("productId") Integer productId,
                             @Param("list") String[] fileList);
}