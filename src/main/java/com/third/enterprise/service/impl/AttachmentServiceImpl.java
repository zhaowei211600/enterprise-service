package com.third.enterprise.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.Attachment;
import com.third.enterprise.bean.request.AttachmentListRequest;
import com.third.enterprise.dao.AttachmentMapper;
import com.third.enterprise.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements IAttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentMapper attachmentMapper;


    @Override
    public Attachment selectAttachment(Integer attachmentId) {
        return attachmentMapper.selectByPrimaryKey(attachmentId);
    }

    @Override
    public List<Attachment> listAttachment(AttachmentListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return attachmentMapper.listAttachment(request);
    }
}
