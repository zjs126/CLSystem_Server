package com.cl.clserverSystem.service;

import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.User;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.cl.clserver_system.service
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/28 10:04
 * @Version 1.0
 */
public interface UserService {
    ResponseResult login(User user);

    ResponseResult loginout();

    ResponseResult register(User user);

    ResponseResult update(User user);

    void setAuthority(User user);

    ResponseResult findAll();

    List<User> SearchManager();

    List<User> SearchFinancing();

    User myself(int id);

    User searchByManagerid(int id);

    User searchByApplyid(int id);

    void IntegrarChange(int number,String id);

    User searchByEmail(String tos);

    User searchByUsername(String userName);
}
