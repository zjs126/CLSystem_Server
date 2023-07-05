package com.cl.clserverSystem.mapper;

import com.cl.clserverSystem.entity.Us;
import com.cl.clserverSystem.entity.Vo.IdAndNumber;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName: UsMapper
 * Package: com.cl.clserverSystem.mapper
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 14:41
 * @Version 1.0
 */
public interface UsMapper {

    @Select("select *  from us where user_id = #{id} and store_id = #{storeId}")
    Us selectbyId(@Param("id") int id, @Param("storeId") int storeId);

    @Insert("insert into us (user_id,store_id,number) values(#{id},#{storeId},#{i}) ")
    void updateAll(@Param("id")int id, @Param("storeId")int storeId,  @Param("i")int i);
    @Update("update us set number = number+#{i} where user_id = #{id} and store_id = #{storeId}")
    void updateInt(@Param("id")int id, @Param("storeId")int storeId,  @Param("i")int i);
    @Select("select store_id,number from us where user_id=#{id}")
    List<IdAndNumber> selectByUserId(int id);
}
