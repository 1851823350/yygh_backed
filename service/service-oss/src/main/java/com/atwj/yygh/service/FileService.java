package com.atwj.yygh.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-11 9:45
 */
public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
