package com.cl.clserverSystem.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: BeanCopyUtils
 * Package: com.sangeng.utils
 * Description:
 *
 * @Author waweji
 * @Create 2023/5/11 17:52
 * @Version 1.0
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){

    }
    public static <V> V copyBean(Object source,Class<V> clazz){
        V result= null;
        try {
            result=clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream()
                    .map(o->copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
