package com.cl.clserverSystem.controller;

import com.aliyuncs.utils.StringUtils;
import com.baidu.aip.face.AipFace;
import com.cl.clserverSystem.entity.LoginUser;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.User;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.service.UserService;
import com.cl.clserverSystem.utils.JwtUtil;
import com.cl.clserverSystem.utils.RedisCache;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;

@RestController
public class FaceRecognition {

    @Autowired
    private AipFace aipFace;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;
    /**
     * 注册人脸
     *
     * @param userName
     * @param imageUrl
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "face/register", method = RequestMethod.POST)
    public ResponseResult register(String userName, String imageUrl) throws IOException {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(imageUrl)) {
            System.out.println(userName);
            System.out.println(imageUrl);
            return ResponseResult.errorResult(AppHttpCodeEnum.Invalid_Param);
        }

        try {
            // 检查用户名是否已存在
            User existingUser = userService.searchByUsername(userName);
            if (existingUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.Invalid_User);
            }

            // 向百度云人脸库注册人脸
            String faceBase = downloadImage(imageUrl);
            registerFace(aipFace, faceBase, userName);

            return ResponseResult.okResult();
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.Internal_Error);
        }
    }

    /**
     * 登录人脸
     *
     * @param imageUrl
     * @return
     */
    @RequestMapping(value = "face/login", method = RequestMethod.POST)
    public ResponseResult login(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.Invalid_Param);
        }

        try {
            // 进行人像数据对比
            String  username= verifyUser(imageUrl, aipFace);
            System.out.println(username);
            if(username=="失败"){
                return ResponseResult.errorResult(AppHttpCodeEnum.Invalid_User);
            }
            LoginUser loginUser=new LoginUser(userService.searchByUsername(username));
            System.out.println(loginUser.getUser());
            String id = Integer.toString(loginUser.getUser().getUser_id());
            String authority=Integer.toString(loginUser.getUser().getAuthority());
            String jwt= JwtUtil.createJWT(id,authority);
            redisCache.setCacheObject("login:"+id,loginUser);
            HashMap<String,String> map = new HashMap<>();
            map.put("token",jwt);
            map.put("authorithy",authority);
            return new ResponseResult(200,"登陆成功",map);
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.Internal_Error);
        }
    }

    /**
     * 人脸比对
     *
     * @param imageUrl 图片的URL
     * @return 相似度分数
     */
    public String verifyUser(String imageUrl, AipFace client) throws IOException {
        System.out.println(imageUrl);
        String imgBase64 = downloadImage(imageUrl);
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.search(imgBase64, "BASE64", "user_01", options);
        JSONObject user = (JSONObject) res.getJSONObject("result").getJSONArray("user_list").get(0);
        System.out.println(user);
        System.out.println("人脸比对结果：" + user.toString());
        Double score = user.getDouble("score");
        if (score > 95) {
            String username = user.getString("user_id");
            return username;
        } else {
            return "失败";
        }
    }

    /**
     * 注册人脸
     *
     * @param client    AipFace对象
     * @param faceBase  人脸的Base64编码
     * @param username  用户名
     * @return 注册是否成功
     */
    public boolean registerFace(AipFace client, String faceBase, String username) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        JSONObject res = client.addUser(faceBase, "BASE64", "user_01", username, options);
        System.out.println("注册的人脸识别数据：" + res.toString(2));
        return true;
    }

    /**
     * 使用URL下载图像并返回Base64编码的字符串
     *
     * @param imageUrl 图像的URL
     * @return Base64编码的图像字符串
     * @throws IOException
     */
    private String downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }
}
