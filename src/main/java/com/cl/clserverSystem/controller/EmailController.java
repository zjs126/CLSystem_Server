package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.LoginUser;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.ToEmail;
import com.cl.clserverSystem.service.UserService;
import com.cl.clserverSystem.utils.JwtUtil;
import com.cl.clserverSystem.utils.RedisCache;
import com.cl.clserverSystem.utils.VerCodeGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * TODO 邮箱验证码
 *
 * @author DB
 * <br>CreateDate 2021/9/13 0:35
 */
@RestController
public class EmailController {
    //	引入邮件接口
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;

    //	获得发件人信息
    @Value("${spring.mail.username}")
    private String from;
    @PostMapping("/Email/judge")
    public ResponseResult EmailRegister(ToEmail toEmail) {
        System.out.println(toEmail.getTos());

//        创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);

        message.setTo(toEmail.getTos());

        message.setSubject("您本次的验证码是");

        String verCode = VerCodeGenerateUtil.generateVerCode();

        message.setText("尊敬的xxx,您好:\n"
                + "\n本次请求的邮件验证码为:" + verCode + ",本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）");

        mailSender.send(message);
        return ResponseResult.okResult(verCode);
    }

    @PostMapping("/Email/login")
    public ResponseResult EmailLoin(ToEmail toEmail) {
        System.out.println(toEmail.getTos());

        LoginUser loginUser=new LoginUser(userService.searchByEmail(toEmail.getTos()));
        System.out.println(loginUser.getUser());
        String id = Integer.toString(loginUser.getUser().getUser_id());
        String authority=Integer.toString(loginUser.getUser().getAuthority());
        String jwt= JwtUtil.createJWT(id,authority);
        redisCache.setCacheObject("login:"+id,loginUser);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("authorithy",authority);
        return new ResponseResult(200,"登陆成功",map);
    }

}
