package com.cl.clserverSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Store
 * Package: com.cl.clserverSystem.entity
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 10:42
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private int store_id;
    private String store_name;
    private String image;
    private int price;
}
