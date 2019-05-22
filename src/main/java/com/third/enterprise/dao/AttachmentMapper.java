package com.third.enterprise.dao;

import com.third.enterprise.bean.Attachment;
import com.third.enterprise.bean.request.AttachmentListRequest;

import java.util.List;

public interface AttachmentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    List<Attachment> listAttachment(AttachmentListRequest request);

}