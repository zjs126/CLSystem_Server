package com.cl.clserverSystem.entity;

import lombok.Data;

@Data
public class Piechart {
    private int zero;
    private int one;
    private int two;
    public Piechart(int a, int b, int c){
        zero=a;
        one=b;
        two=c;
    }
}
