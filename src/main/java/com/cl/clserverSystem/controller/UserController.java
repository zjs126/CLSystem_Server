package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.Apply;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.User;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.service.UserService;
import com.cl.clserverSystem.utils.DateUtils;
import com.cl.clserverSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: UserController
 * Package: com.cl.clserver_system.controller
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/28 10:03
 * @Version 1.0
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**登录函数
     *
     * @RequestBody user
     * @return ResponseResult
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 测试用函数
     */
//    @GetMapping("/hello")
//    public ResponseResult Hello(){
//        return ResponseResult.okResult("hello");
//    }

    /**
     *登出
     */
    @RequestMapping("/login/out")
    public ResponseResult loginout() {
        return userService.loginout();
    }

    /**
     * 注册
     */
    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }


    /**
     * 跟新用户信息，根据token解析use_id 进行用户信息更改（用户名和email）
     * @param user
     * @return
     */
    @PostMapping("/user/update")
    public ResponseResult update(@RequestBody User user){
        return userService.update(user);
    }


    /**
     * 查询所有员工保证经理和财务
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/user/findall")
    public ResponseResult findAll(HttpServletRequest request) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String authority = claims.getSubject();
        if(!authority.equals("1")){
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTHORITY_ERROR);
        }
        return userService.findAll();
    }

    /**
     * 经理给员工授予权限的函数
     * controller层根据jwt令牌中的id 和 权限信息，检索是否为经理
     * 然后根据传过来的userid 和authority修改权限
     */
    @PostMapping("/SetAuthority")
    public ResponseResult setAuthority(HttpServletRequest request,@RequestBody User user) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String authority = claims.getSubject();
        if(!authority.equals("1")){
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTHORITY_ERROR);
        }
        userService.setAuthority(user);
        return ResponseResult.okResult();
    }

    /**
     * 筛选全部经理
     * @return
     */
    @GetMapping("/user/searchManager")
    public ResponseResult SearchManager(){
        List<User> userList=userService.SearchManager();
        return ResponseResult.okResult(userList);
    }

    /**
     * 筛选全部财务
     * @return
     */
    @GetMapping("/user/searchFinancing")
    public ResponseResult SearchFinancing(){
        List<User> userList=userService.SearchFinancing();
        return ResponseResult.okResult(userList);
    }

    /**
     * 返回自己全部信息，用于个人中心之类的
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/user/myself")
    public ResponseResult myself(HttpServletRequest request )throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id=claims.getId();
        User user=userService.myself(Integer.parseInt(id));
        return ResponseResult.okResult(user);
    }

    @GetMapping("/user/manager/{id}")
    public ResponseResult searchByManagerid(@PathVariable("id") int id){
     User user=userService.searchByManagerid(id);
     return ResponseResult.okResult (user);
    }

    @GetMapping("/user/searchByApplyid/{applyid}")
    public ResponseResult searchByApplyid(@PathVariable("applyid") int id){
        User user=userService.searchByApplyid(id);
        return ResponseResult.okResult(user);
    }

    @PostMapping("/user/integral/{number}")
    public ResponseResult IntegrarChange(HttpServletRequest request,@PathVariable("number") int number) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id=claims.getId();
        userService.IntegrarChange(number,id);
        return ResponseResult.okResult();
    }
}
