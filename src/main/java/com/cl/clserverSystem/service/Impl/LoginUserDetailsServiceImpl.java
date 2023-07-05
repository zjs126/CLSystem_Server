package com.cl.clserverSystem.service.Impl;


import com.cl.clserverSystem.entity.LoginUser;
import com.cl.clserverSystem.entity.User;
import com.cl.clserverSystem.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: LoginUserServiceImpl
 * Package: com.waweji.Service.impl
 * Description:
 *
 * @Author waweji
 * @Create 2023/5/24 21:15
 * @Version 1.0
 */
@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        User user=userMapper.selectByusername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        return new LoginUser(user);
    }
}
