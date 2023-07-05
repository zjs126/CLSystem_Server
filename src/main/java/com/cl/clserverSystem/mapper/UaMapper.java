package com.cl.clserverSystem.mapper;

import com.cl.clserverSystem.entity.Ua;

/**
 * ClassName: UaMapper
 * Package: com.cl.clserverSystem.mapper
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 19:50
 * @Version 1.0
 */
public interface UaMapper {
    void add(Ua ua);

    void delete(int id);

    int selectbyApplyId(int applyid);
}
