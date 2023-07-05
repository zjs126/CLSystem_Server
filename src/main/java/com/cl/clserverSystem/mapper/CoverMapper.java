package com.cl.clserverSystem.mapper;


import com.cl.clserverSystem.entity.Cover;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoverMapper {
    @Insert("insert into cover (user_id, transaction, fal_fee,proof,financing,cover_time) values (#{user_id},#{transaction},#{fal_fee},#{proof},#{financing},#{cover_time})")
    void CoverAdd(Cover cover);

    @Update("update cover set refuse_reason=#{refuse_reason},tag=#{tag} where cover_id=#{cover_id}")
    void CoverCheck(Cover cover);

    @Select("select * from cover where financing=#{financing} and tag='0'")
    List<Cover> searchByFinancing(String financing);

    @Select("select cover_id from  ac where apply_id=#{applyid}")
    List<Integer> searchByApplyid(int applyid);

    @Select("select * from cover where cover_id = #{cover_id}")
    Cover searchByCoverid(int cover_id);

    @Select("Select cover_id from cover where user_id=#{user_id} and transaction=#{transaction} and fal_fee=#{fal_fee} and proof=#{proof} and financing=#{financing}")
    Integer selectByCover(Cover cover);

    @Delete("delete from cover where cover_id=#{coverid}")
    void CoverDelete(int coverid);

    @Select("select * from cover where user_id=#{userid}")
    List<Cover> myself(String userid);

    @Select("select * from cover where cover_id=#{coverid}")
    Cover searchByid(int coverid);
}
