package com.third.enterprise.controller;


import com.third.enterprise.bean.ProductAttachment;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.integration.IFileService;
import com.third.enterprise.service.IProductService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.ErrorUtil;
import com.third.enterprise.util.FileTypeEnum;
import com.third.enterprise.util.FileTypeJudgeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("/user/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    @Autowired
    private IProductService productService;

    private static final String UPLOAD_FILE_PREFIX = "user_";

    private static final String[] ALL_FILE_TYPE = {"png","jpeg","jpg"};


    @RequestMapping("/upload")
    public UnifiedResult fileUpload(@RequestParam("file") MultipartFile file){

        logger.info("文件上传大小：{}", file.getSize());
        String fileSuffix = "";
        try {
            FileTypeEnum fileTypeEnum = FileTypeJudgeUtil.getType(file.getInputStream());
            if(fileTypeEnum != null){
                fileSuffix = fileTypeEnum.toString();
            }
        } catch (IOException e) {
            logger.error("获取上传文件的后缀出错:{}", ErrorUtil.getErrorStackInfo(e));
        }
        if(!StringUtils.isEmpty(fileSuffix)){
            fileSuffix = fileSuffix.toLowerCase();
        }else {
            //未匹配出实际的格式
            String originalName = file.getOriginalFilename();
            if(!StringUtils.isEmpty(originalName)){
                fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
            }
        }

        if(Arrays.asList(ALL_FILE_TYPE).contains(fileSuffix)){
            if(fileService.uploadFile(file, generateFileName(fileSuffix))){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        logger.info("上传文件后缀不合法:{}", file.getOriginalFilename());
        return UnifiedResultBuilder.errorResult(Constants.FILE_HANDLE_ERROR_CODE,
                Constants.FILE_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/attachment")
    public UnifiedResult uploadAttachment(@RequestParam("file") MultipartFile file,
                                          @RequestParam("productId") Integer productId) throws IOException {

        logger.info("文件上传大小：{}", file.getSize());
        String fileSuffix = "";

        //未匹配出实际的格式
        String originalName = file.getOriginalFilename();
        if(!StringUtils.isEmpty(originalName)){
            fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
        }
        HashMap<String,String> fileAttr = new HashMap<>(2);
        String fileName = generateFileName(fileSuffix);
        if(fileService.uploadFile(file, fileName)){
            fileAttr.put("fileName", fileName);
            fileAttr.put("originalName", originalName);
            ProductAttachment attachment = new ProductAttachment();
            attachment.setProductId(productId);
            attachment.setFileName(originalName);
            attachment.setFilePath(fileName);
            attachment.setStatus("1");
            if(productService.saveAttachment(attachment)){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                        fileAttr);
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.FILE_HANDLE_ERROR_CODE,
                Constants.FILE_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/download")
    public UnifiedResult downloadFile(String fileName){
        String fileBase64Str = fileService.getImgBase64Str(fileName);
        if(!StringUtils.isEmpty(fileBase64Str)){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MESSAGE, fileBase64Str);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    public void fileDownload(String fileName, HttpServletResponse res) throws Exception {

        ByteArrayInputStream byteArrayInputStream =  fileService.download(fileName);
        if(byteArrayInputStream != null){
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            String downloadFile = System.currentTimeMillis() + "." + suffix;
            res.setHeader("content-type", "application/octet-stream");
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + downloadFile);
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = res.getOutputStream();
                bis = new BufferedInputStream(byteArrayInputStream);
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static String generateFileName(String fileSuffix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());
        Random rad = new Random();
        String random = ""+rad.nextInt(10)+rad.nextInt(10);
        return UPLOAD_FILE_PREFIX  + time + random + "." +fileSuffix;
    }

}
