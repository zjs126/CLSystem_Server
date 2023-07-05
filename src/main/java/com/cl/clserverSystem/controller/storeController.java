package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.LoginUser;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.Store;
import com.cl.clserverSystem.mapper.UserMapper;
import com.cl.clserverSystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: storeController
 * Package: com.cl.clserverSystem.controller
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 10:41
 * @Version 1.0
 */
@RestController
public class storeController {
    @Autowired
    private StoreService storeService;
    @Resource
    private UserMapper userMapper;
    @GetMapping("/store/display")
    public ResponseResult display(){
        return storeService.display();
    }
    @PostMapping("/store/buy")
    public ResponseResult buy(@RequestBody Store store){
        return storeService.buy(store);
    }
    @GetMapping("/store/displayIntegral")
    public ResponseResult displayIntegral(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
       int Integral = userMapper.displayIntegral(id);
       return ResponseResult.okResult(Integral);
    }
    @GetMapping("/store/userStore")
    public ResponseResult userStore(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        return storeService.userStore(id);
    }
}
