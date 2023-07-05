package com.cl.clserverSystem.mapper;

import com.cl.clserverSystem.entity.Store;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: StoreMapper
 * Package: com.cl.clserverSystem.mapper
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 10:46
 * @Version 1.0
 */
public interface StoreMapper {
    List<Store> display();
    @Select("select * from store where store_id = #{storeId}")
    Store selectById(int storeId);
}
