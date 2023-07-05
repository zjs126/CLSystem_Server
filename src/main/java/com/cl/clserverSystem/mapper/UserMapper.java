package com.cl.clserverSystem.mapper;

import com.cl.clserverSystem.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: userMapper
 * Package: com.cl.clserver_system.mapper
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/28 10:06
 * @Version 1.0
 */
public interface UserMapper {


    User selectByusername(String username);

    void register(User user);

    void update(User user);

    void setAuthority(User user);

    List<User> findAll();

    List<User> SearchManager();

    List<User> SearchFinancing();

    User searchByUseid(int id);

    int searchByApplyid(int id);

    void IntegrarChange(int number,String id);

    void updateIntegral(@Param("userId") int userId, @Param("addnumber") int addnumber);

    User searchByEmail(String tos);

    int displayIntegral(int id);
}
