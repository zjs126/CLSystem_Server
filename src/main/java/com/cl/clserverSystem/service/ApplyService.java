package com.cl.clserverSystem.service;

import com.cl.clserverSystem.entity.Apply;
import com.cl.clserverSystem.entity.Piechart;
import com.cl.clserverSystem.entity.ResponseResult;


/**
 * ClassName: ApplyService
 * Package: com.cl.clserverSystem.service
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 18:53
 * @Version 1.0
 */
public interface ApplyService {
    ResponseResult add(Apply apply);

    ResponseResult update(Apply apply);

    ResponseResult updateApply(Apply apply);

    ResponseResult delete(int id);

    ResponseResult check();

    ResponseResult managerDisplay();

    int searchByCoverid(int coverid);

    ResponseResult audited(int situation);

    ResponseResult tripStart();

    ResponseResult NotripStart();

    Apply searchByApplyid(int applyid);

    Piechart staticForPiechart();
}
