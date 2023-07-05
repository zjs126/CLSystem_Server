package com.cl.clserverSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;


/**
 * ClassName: user
 * Package: com.cl.clserverSystem.entity
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/28 10:26
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int user_id;
    private String username;
    private String email;
    private String password;
    private int integral;
    private int authority;
    private List<Apply> applyList;
}
