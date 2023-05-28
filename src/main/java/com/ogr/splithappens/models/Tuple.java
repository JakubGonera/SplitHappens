package com.ogr.splithappens.models;

import java.io.Serializable;

public class Tuple implements Serializable {
    public String name;
    int id;
    int balance;
    Tuple(String name, int id){
        balance = 0;
        this.name = name;
        this.id = id;
    }
}