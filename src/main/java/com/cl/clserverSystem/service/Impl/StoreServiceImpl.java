package com.cl.clserverSystem.service.Impl;

import com.cl.clserverSystem.entity.LoginUser;
import com.cl.clserverSystem.entity.ResponseResult;
import com.cl.clserverSystem.entity.Store;
import com.cl.clserverSystem.entity.Us;
import com.cl.clserverSystem.entity.Vo.IdAndNumber;
import com.cl.clserverSystem.entity.Vo.StoreVo;
import com.cl.clserverSystem.enums.AppHttpCodeEnum;
import com.cl.clserverSystem.mapper.StoreMapper;
import com.cl.clserverSystem.mapper.UsMapper;
import com.cl.clserverSystem.mapper.UserMapper;
import com.cl.clserverSystem.service.StoreService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: StoreServiceImpl
 * Package: com.cl.clserverSystem.service.Impl
 * Description:
 *
 * @Author waweji
 * @Create 2023/7/5 10:45
 * @Version 1.0
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private UsMapper usMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public ResponseResult display() {
        List<Store> stores =  storeMapper.display();
        return ResponseResult.okResult(stores);
    }

    @Override
    public ResponseResult buy(Store store) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int id = loginUser.getUser().getUser_id();
        int storeId = store.getStore_id();
        int integral = store.getPrice();
        integral = -1 *integral;
        Us us = usMapper.selectbyId(id,storeId);
        int i=1;
        if(Objects.isNull(us)){
            usMapper.updateAll(id,storeId,i);
        }else{
            usMapper.updateInt(id,storeId,i);
        }
        userMapper.updateIntegral(id,integral);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult userStore(int id) {
        List<IdAndNumber> idAndNumbers =usMapper.selectByUserId(id);
        System.out.println(idAndNumbers);
        List<StoreVo> storeVos = new ArrayList<>() ;
        for(IdAndNumber idAndNumber : idAndNumbers){
            int number = idAndNumber.getNumber();
            int storeId = idAndNumber.getStore_id();
            Store store = storeMapper.selectById(storeId);
            StoreVo storeVo =new StoreVo(number,store);
            storeVos.add(storeVo);
        }
        return ResponseResult.okResult(storeVos);
    }

}
