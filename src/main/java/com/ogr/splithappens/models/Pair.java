package com.ogr.splithappens.models;

public class Pair <T,U> {
    T first;
    U second;
    Pair(){
        first = null;
        second = null;
    }
    Pair(T first, U second){
        this.first = first;
        this.second = second;
    }
}
