package com.cl.clserverSystem.mapper;

import com.cl.clserverSystem.entity.Apply;
import com.cl.clserverSystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ApplyMapper
 * Package: com.cl.clserverSystem.mapper
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 18:54
 * @Version 1.0
 */

@Mapper
public interface ApplyMapper {
    void add(Apply apply);

    Integer selectByApply(Apply apply);

    List<Apply> selectByManagerId(int id);


    void update(Apply apply);

    void updateApply(Apply apply);

    void delete(int id);

    User selectByUserId(int id);

    List<Apply> ApplyUadited(@Param("id") int id, @Param("situation") int situation);

    List<Apply> tripStart(@Param("id")int id,@Param("date")Date date);

    List<Apply> NotripStart(@Param("id")int id,@Param("date")Date date);

    @Select("select * from apply where apply_id=#{applyid}")
    Apply searchByApplyid(int applyid);
}
