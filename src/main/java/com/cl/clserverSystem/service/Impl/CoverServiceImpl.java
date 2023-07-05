package com.cl.clserverSystem.service.Impl;

import com.cl.clserverSystem.entity.*;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.mapper.*;
import com.cl.clserverSystem.service.CoverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CoverServiceImpl implements CoverService {
    @Resource
    private CoverMapper coverMapper;
    @Resource
    private AcMapper acMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private ApplyMapper applyMapper;
    @Resource
    private UaMapper uaMapper;

    @Override
    public ResponseResult CoverAdd(Cover cover, int applyid) {
        Date date = new Date();
        cover.setCover_time(date);
        Integer id = coverMapper.selectByCover(cover);
        if (!Objects.isNull(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.Cover_EXIST);
        }
        coverMapper.CoverAdd(cover);
        int coverid = coverMapper.selectByCover(cover);
        acMapper.add(applyid, coverid);
        return ResponseResult.okResult();
    }

    @Override
    public void CoverCheck(Cover cover) {
        coverMapper.CoverCheck(cover);
        int applyid = acMapper.searchByCoverid(cover.getCover_id());
        Ac ac = new Ac();
        ac.setApply_id(applyid);
        ac.setCover_id_list(coverMapper.searchByApplyid(applyid));
        List<Cover> coverList = searchByCoverid(ac);
        int sum=0,cnt=0;
        for(Cover cover1 : coverList){
            if(cover1.getTag() ==1) cnt++;
            sum+=cover1.getFal_fee();
        }
        Apply apply = applyMapper.searchByApplyid(applyid);
        sum -= apply.getPre_fee();
        if(cnt == coverList.size()){
            int userId = uaMapper.selectbyApplyId(applyid);
            sum = sum * (int)((apply.getEnd_time().getTime()-apply.getStart_time().getTime())/(60*60*1000*24*1000));
            userMapper.updateIntegral(userId,sum);
        }
    }

    @Override
    public ResponseResult searchByFinancing(String financing) {
        List<Cover> coverList = coverMapper.searchByFinancing(financing);
        return ResponseResult.okResult(coverList);
    }

    @Override
    public List<Integer> searchByApplyid(int applyid) {
        return coverMapper.searchByApplyid(applyid);
    }

    @Override
    public List<Cover> searchByCoverid(Ac ac) {
        List<Cover> coverList = new ArrayList<>();
        for (int cover_id : ac.getCover_id_list()) {
            Cover cover = coverMapper.searchByCoverid(cover_id);
            coverList.add(cover);
        }
        return coverList;
    }

    @Override
    public void CoverDelete(int coverid) {
        coverMapper.CoverDelete(coverid);
        acMapper.deleteByCoverid(coverid);
    }

    @Override
    public List<Cover> myself(String userid) {
        return coverMapper.myself(userid);
    }

    @Override
    public Cover searchByid(int coverid) {
        return coverMapper.searchByid(coverid);
    }
}
