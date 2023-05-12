package com.ogr.splithappens.models;

public class Tuple{
    public String name;
    int id;
    int balance;
    Tuple(String name, int id){
        balance = 0;
        this.name = name;
        this.id = id;
    }
}