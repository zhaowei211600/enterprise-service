package com.third.enterprise.service;


import com.third.enterprise.bean.Attachment;
import com.third.enterprise.bean.request.AttachmentListRequest;

import java.util.List;

public interface IAttachmentService {

    Attachment selectAttachment(Integer attachmentId);

    List<Attachment> listAttachment(AttachmentListRequest request);
}
