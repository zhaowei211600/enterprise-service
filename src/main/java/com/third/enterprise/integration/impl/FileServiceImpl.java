package com.third.enterprise.integration.impl;

import com.third.enterprise.integration.IFileService;
import com.third.enterprise.util.ErrorUtil;
import com.third.enterprise.util.LocalFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("fileService")
public class FileServiceImpl implements IFileService{

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.path}")
    private String localFilePath;

    @Override
    public boolean uploadFile(MultipartFile file, String fileName) {

        try {
            return LocalFileUtil.saveFile(file.getInputStream(),localFilePath, fileName);
        } catch (IOException e) {
            logger.error("文件上传错误：{}", ErrorUtil.getErrorStackInfo(e));
        }
        return false;
    }
}
