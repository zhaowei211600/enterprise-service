package com.third.enterprise.controller;


import com.third.enterprise.bean.Attachment;
import com.third.enterprise.bean.request.AttachmentListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.integration.IFileService;
import com.third.enterprise.service.IAttachmentService;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation/attachment/order")
public class AttachmentController {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileService fileService;

    @PostMapping("/list")
    public UnifiedResult listAttachment(@RequestBody AttachmentListRequest request){

        List<Attachment> attachmentList = attachmentService.listAttachment(request);
        if(attachmentList != null && attachmentList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    attachmentList,
                    Page.toPage(attachmentList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }


}
