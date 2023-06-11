package com.ogr.splithappens.model;

import java.io.Serializable;

public class Pair<T, U> implements Serializable {
    public T first;
    public U second;

    Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
