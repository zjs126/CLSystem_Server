package com.cl.clserverSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cover {
    @Id
    private int cover_id;
    private int user_id;
    private String transaction;
    private int fal_fee;
    private String refuse_reason;
    private int tag;
    private String proof;
    private int financing;
    private Date cover_time;
}
