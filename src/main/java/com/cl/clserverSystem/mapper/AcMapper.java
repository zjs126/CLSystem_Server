package com.cl.clserverSystem.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AcMapper {

    @Insert("insert into ac (apply_id, cover_id) values (#{applyid},#{coverid})")
    void add(@Param("applyid") int applyid, @Param("coverid") int coverid);

    @Delete("delete from ac where cover_id=#{coverid}")
    void deleteByCoverid(int coverid);

    @Select("select apply_id from ac where cover_id=#{coverid}")
    int searchByCoverid(@Param("coverid") int coverid);
}
