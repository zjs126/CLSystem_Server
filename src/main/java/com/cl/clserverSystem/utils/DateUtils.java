package com.cl.clserverSystem.utils;

import org.junit.Test;
import org.springframework.web.bind.annotation.PutMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: DateUtils
 * Description:
 *
 * @Create 2023/5/18 18:11
 * @Version 1.0
 */
public class DateUtils {
    public static Date dataTime(){
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = tempDate.format(new Date());

        Date date = null;
        try {
            date = tempDate.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(date);
        return date;
    }
    @Test
    public void test1(){
        Date date = DateUtils.dataTime();
        System.out.println(date);
    }
}
