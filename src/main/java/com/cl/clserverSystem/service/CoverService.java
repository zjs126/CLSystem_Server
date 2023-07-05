package com.cl.clserverSystem.service;

import com.cl.clserverSystem.entity.Ac;
import com.cl.clserverSystem.entity.Cover;
import com.cl.clserverSystem.entity.ResponseResult;

import java.util.List;

public interface CoverService {

    ResponseResult CoverAdd(Cover cover, int applyid);

    void CoverCheck(Cover cover);

    ResponseResult searchByFinancing(String financing);

    List<Integer> searchByApplyid(int applyid);

    List<Cover> searchByCoverid(Ac ac);

    void CoverDelete(int coverid);

    List<Cover> myself(String userid);

    Cover searchByid(int coverid);

}
