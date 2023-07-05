package com.cl.clserverSystem.service.Impl;

import com.cl.clserverSystem.entity.*;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.mapper.UserMapper;
import com.cl.clserverSystem.service.UserService;
import com.cl.clserverSystem.utils.JwtUtil;
import com.cl.clserverSystem.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: userServiceImpl
 * Package: com.cl.clserver_system.service.Impl
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/28 10:05
 * @Version 1.0
 */
@Service
public class userServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new
                UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authentication =authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser= (LoginUser) authentication.getPrincipal();
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
    @Override
    public ResponseResult loginout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        redisCache.deleteObject("login:"+id);
        return ResponseResult.okResult(200,"成功退出");
    }

    @Override
    public ResponseResult register(User user) {
        BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
        String password=user.getPassword();
        String encode =passwordEncoder.encode(password);
        user.setPassword(encode);
        User exist = userMapper.selectByusername(user.getUsername());
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名已经存在");
        }
        userMapper.register(user);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        user.setUser_id(id);
        userMapper.update(user);
        return ResponseResult.okResult();
    }

    @Override
    public void setAuthority(User user) {
        userMapper.setAuthority(user);
    }

    @Override
    public ResponseResult findAll() {
        List<User> userList=userMapper.findAll();
        return  ResponseResult.okResult(userList);
    }

    @Override
    public List<User> SearchManager() {
        return userMapper.SearchManager();
    }

    @Override
    public List<User> SearchFinancing() {
        return userMapper.SearchFinancing();
    }

    @Override
    public User myself(int id) {
        return userMapper.searchByUseid(id);
    }

    @Override
    public User searchByManagerid(int id) {
        return userMapper.searchByUseid(id);
    }

    @Override
    public User searchByApplyid(int id) {
        int user_id=userMapper.searchByApplyid(id);
        return userMapper.searchByUseid(user_id);
    }

    @Override
    public void IntegrarChange(int number,String id) {
        userMapper.IntegrarChange(number,id);
    }

    @Override
    public User searchByEmail(String tos) {
        return userMapper.searchByEmail(tos);
    }

    @Override
    public User searchByUsername(String userName) {
        return userMapper.selectByusername(userName);
    }

}
