package com.cl.clserverSystem.service;

import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.Store;

/**
 * ClassName: StoreService
 * Package: com.cl.clserverSystem.service
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 10:45
 * @Version 1.0
 */
public interface StoreService {
    ResponseResult display();

    ResponseResult buy(Store store);

    ResponseResult userStore(int id);
}
