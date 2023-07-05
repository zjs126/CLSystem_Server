package com.cl.clserverSystem.controller;

import com.cl.clserverSystem.entity.Apply;
import com.cl.clserverSystem.entity.Cover;
import com.cl.clserverSystem.entity.Piechart;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.service.ApplyService;
import com.cl.clserverSystem.utils.DateUtils;
import com.cl.clserverSystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: ApplyController
 * Package: com.cl.clserverSystem.controller
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 18:51
 * @Version 1.0
 */
@RestController
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    /**
     * 员工添加申请表
     *
     * @param apply
     * @return 成功返回 200
     * 如果已经存返回 501,"申请表已存在"
     */
    @PostMapping("/apply/add")
    public ResponseResult add(@RequestBody Apply apply) {
        apply.setApply_time(DateUtils.dataTime());
        return applyService.add(apply);
    }

    /**
     * 员工删除申请表
     *
     * @return 200
     */
    @PostMapping("/apply/delete/{applyid}")
    public ResponseResult delete(@PathVariable("applyid") int applyid) {

        return applyService.delete(applyid);
    }

    /**
     * 员工对自己填写的申请表进行修改
     *
     * @param apply
     * @return 200
     */
    @PostMapping("/apply/updateApply")
    public ResponseResult updateApply(@RequestBody Apply apply) {
        apply.setApply_time(DateUtils.dataTime());
        return applyService.updateApply(apply);
    }

    /**
     * 经理进行审批申请表
     *
     * @param apply
     * @return 200
     */
    @PostMapping("/apply/approval")
    public ResponseResult approval(@RequestBody Apply apply) {
        return applyService.update(apply);
    }

    /**
     * 员工对自己填写的申请表检查
     *
     * @param
     * @return data为List<Apply>
     */
    @GetMapping("/apply/check")
    public ResponseResult check() {
        return applyService.check();
    }

    /**
     * 经理要处理的申请表
     *
     * @param
     * @return data为List<Apply>
     */
    @GetMapping("/apply/managerDisplay")
    public ResponseResult managerDisplay(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String authority = claims.getSubject();
        if (!authority.equals(("1"))) {
            return ResponseResult.errorResult(600, "权限不足");
        }
        return applyService.managerDisplay();
    }

    @GetMapping("/apply/{coverid}")
    public ResponseResult searchByCoverid(@PathVariable("coverid") int coverid){
        int applyid=applyService.searchByCoverid(coverid);
        return ResponseResult.okResult(applyid);
    }
    /**
     * 申请表认证情况分类
     *
     * @param
     * @return data为List<Apply>
     */
    @GetMapping("/apply/audited/{situation}")
    public  ResponseResult audited(@PathVariable("situation") int situation){
        return applyService.audited(situation);
    }
    /**
     * 出差进度查询在出差中
     *
     * @param
     * @return data为List<Apply>
     */
    @GetMapping("/apply/tripStart")
    public  ResponseResult tripStart(){
        return applyService.tripStart();
    }
    /**
     * 出差进度查询未开始
     *
     * @param
     * @return data为List<Apply>
     */
    @GetMapping("/apply/NoTripStart")
    public  ResponseResult NotripStart(){
        return applyService.NotripStart();
    }

    @GetMapping("/apply/searchByApplyid/{Applyid}")
    public ResponseResult searchByApplyid(@PathVariable("Applyid") int applyid){
        Apply apply=applyService.searchByApplyid(applyid);
        return ResponseResult.okResult(apply);
    }

    @GetMapping("/apply/Piechart")
    public ResponseResult staticForPiechart() throws Exception {
        Piechart piechart=applyService.staticForPiechart();
        return ResponseResult.okResult(piechart);
    }
}
