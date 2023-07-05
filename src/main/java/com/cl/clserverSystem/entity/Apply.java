package com.cl.clserverSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: Apply
 * Package: com.cl.clserverSystem.entity
 * Description:
 *
 * @Author waweji
 * @Create 2023/6/29 18:40
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {
    private int apply_id;
    private String origin_place;
    private String target_place;
    private Date start_time;
    private Date end_time;
    private String reason;
    private int pre_fee;
    private String reject_reason;
    private int situation;
    private String type;
    private int manager_id;
    private Date apply_time;
}
