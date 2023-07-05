package com.cl.clserverSystem.service.Impl;

import com.cl.clserverSystem.entity.*;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.mapper.AcMapper;
import com.cl.clserverSystem.mapper.ApplyMapper;
import com.cl.clserverSystem.mapper.UaMapper;
import com.cl.clserverSystem.service.ApplyService;
import com.cl.clserverSystem.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: ApplyServiceImpl
 * Package: com.cl.clserverSystem.service.Impl
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 18:52
 * @Version 1.0
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Resource
    private UaMapper uaMapper;

    @Resource
    private AcMapper acMapper;

    public ResponseResult add(Apply apply) {
        Integer id = applyMapper.selectByApply(apply);
        if(!Objects.isNull(id)){
                    return ResponseResult.errorResult(AppHttpCodeEnum.Apply_EXIST);
        }
        applyMapper.add(apply);
        Integer ApplyId = applyMapper.selectByApply(apply);
        System.out.println(ApplyId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int UserId = loginUser.getUser().getUser_id();
        Ua ua = new Ua(UserId,ApplyId);
        uaMapper.add(ua);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(Apply apply) {
        applyMapper.update(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult updateApply(Apply apply) {
        int id = applyMapper.selectByApply(apply);
        apply.setApply_id(id);
        applyMapper.updateApply(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult delete(int id) {
        uaMapper.delete(id);
        applyMapper.delete(id);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        //System.out.println(id);
//        List<Integer> applyIds = uaMapper.searchById(id);
//        System.out.println("输出"+applyIds);
//        List<Apply> applyList = applyMapper.selectByApplyIds(applyIds) ;
        User user=applyMapper.selectByUserId(id);
        System.out.println(user.getApplyList());
        return ResponseResult.okResult(user.getApplyList());
    }

    @Override
    public ResponseResult managerDisplay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        List<Apply> applyList=applyMapper.selectByManagerId(id);
        return ResponseResult.okResult(applyList);
    }

    @Override
    public int searchByCoverid(int coverid) {
        return acMapper.searchByCoverid(coverid);
    }

    @Override
    public ResponseResult audited(int situation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        List<Apply> applyList=applyMapper.ApplyUadited(id,situation);
        return ResponseResult.okResult(applyList);
    }

    @Override
    public ResponseResult tripStart() {
        Date date = DateUtils.dataTime();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        List<Apply> applyList=applyMapper. tripStart(id,date);
        return ResponseResult.okResult(applyList);
    }

    @Override
    public ResponseResult NotripStart() {
        Date date = DateUtils.dataTime();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        List<Apply> applyList=applyMapper.NotripStart(id,date);
        return ResponseResult.okResult(applyList);
    }

    @Override
    public Apply searchByApplyid(int applyid) {
        return applyMapper.searchByApplyid(applyid);
    }

    @Override
    public Piechart staticForPiechart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        User user=applyMapper.selectByUserId(id);
        int zero=0;
        int one=0;
        int two=0;
        for(Apply apply:user.getApplyList()){
            if(apply.getSituation()==0){
                zero++;
            }else if(apply.getSituation()==1){
                one++;
            }else if(apply.getSituation()==2){
                two++;
            }
        }
        Piechart piechart=new Piechart(zero,one,two);
        return piechart;
    }
}
