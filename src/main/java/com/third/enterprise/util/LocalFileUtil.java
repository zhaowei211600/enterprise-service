package com.third.enterprise.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class LocalFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileUtil.class);

    public static boolean saveFile(InputStream inputStream,String filePath, String fileName) {

        OutputStream os = null;
        try {
            byte[] bs = new byte[1024];
            int len;
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return true;
        } catch (IOException e) {
            logger.error("文件保存出错：{}", ErrorUtil.getErrorStackInfo(e));
        } catch (Exception e) {
            logger.error("文件保存出错：{}", ErrorUtil.getErrorStackInfo(e));
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                logger.error("文件流关闭异常：{}", ErrorUtil.getErrorStackInfo(e));
            }
        }
        return false;
    }

}
