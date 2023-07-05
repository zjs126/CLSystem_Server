package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.service.CoverService;
import com.cl.clserverSystem.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Autowired
    private CoverService coverService;

//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws Exception {
//        log.info("文件上传：{}，{}，{}",username,age,image);
//
//        String originalFilename=image.getOriginalFilename();
//
//        int index=originalFilename.lastIndexOf(".");
//        String extname = originalFilename.substring(index);
//        String newFilename = UUID.randomUUID().toString()+extname;
//        log.info("新的文件名:{}",newFilename);
//
//        image.transferTo(new File("D:\\360MoveData\\Users\\ASUS\\Documents\\Desktop\\day10-SpringBootWeb案例/"+newFilename));
//        return Result.success();
//        }

    /**
     * 将图片上传到阿里云，返回图片url
     * @param image
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public ResponseResult upload(@RequestPart("image") MultipartFile image) throws Exception {
        System.out.printf("文件上传，文件名：{}",image.getOriginalFilename());

        String url= aliOSSUtils.upload(image);
        System.out.printf("文件上传完成，文件访问的url:%s",url);

        return ResponseResult.okResult(url);
    }
}
