package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.Ac;
import com.cl.clserverSystem.entity.Cover;
import com.cl.clserverSystem.entity.Piechart;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.service.CoverService;
import com.cl.clserverSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CoverCotroller {
    @Autowired
    private CoverService coverService;

    /**
     * 用户填写报销表
     * @param cover
     * @return
     */
    @PostMapping("/Cover/add/{applyid}")
    public ResponseResult CoverAdd(@RequestBody Cover cover, @PathVariable("applyid")int applyid){
        return coverService.CoverAdd(cover,applyid);
    }

    /**
     * 审批后将审批结果返回更新数据库表中状态
     * @param cover
     * @return
     */
    @PostMapping("Cover/check")
    public  ResponseResult CoverCheck(@RequestBody Cover cover){
        coverService.CoverCheck(cover);
        return ResponseResult.okResult();
    }

    /**
     * 根据财务查询，他要需要审批的报销
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("Cover/searchByFinancing")
    public ResponseResult searchByFinancing(HttpServletRequest request) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String authority = claims.getSubject();
        if(!authority.equals("2")){
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTHORITY_ERROR);
        }
        String financing=claims.getId();
        return coverService.searchByFinancing(financing);
    }

    @GetMapping("Cover/searchByApplyid")
    public ResponseResult searchByApplyid(@RequestBody Ac ac){
        int applyid=ac.getApply_id();
        ac.setCover_id_list(coverService.searchByApplyid(applyid));
        List<Cover> coverList=coverService.searchByCoverid(ac);
        return ResponseResult.okResult(coverList);
    }

    @DeleteMapping("/Cover/CoverDelete/{coverid}")
    public ResponseResult CoverDelete(@PathVariable("coverid") int coverid){
        coverService.CoverDelete(coverid);
        return ResponseResult.okResult();
    }

    @GetMapping("/Cover/myself")
    public ResponseResult myself(HttpServletRequest request) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String userid= claims.getId();
        List<Cover> coverList=coverService.myself(userid);
        return ResponseResult.okResult(coverList);
    }

    @GetMapping("Cover/{coverid}")
    public ResponseResult searchByCoverid(@PathVariable("coverid") int coverid){
        Cover cover=coverService.searchByid(coverid);
        return ResponseResult.okResult(cover);
    }

    @GetMapping("/Cover/Piechart")
    public ResponseResult staticForPiechart(HttpServletRequest request) throws Exception {
        String token =request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String userid= claims.getId();
        List<Cover> coverList=coverService.myself(userid);
        int zero=0;
        int one=0;
        int two=0;
        for(Cover cover:coverList){
            if(cover.getTag()==1){
                one++;
            }else if(cover.getTag()==2){
                two++;
            }else if(cover.getTag()==0){
                zero++;
            }
        }
        Piechart piechart=new Piechart(zero,one,two);
        return ResponseResult.okResult(piechart);
    }
}
