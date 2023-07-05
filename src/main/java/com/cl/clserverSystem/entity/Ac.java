package com.cl.clserverSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ac {
    private int apply_id;
    List<Integer> cover_id_list;
}
