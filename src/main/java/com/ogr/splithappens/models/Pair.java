package com.ogr.splithappens.models;

import java.io.Serializable;

public class Pair<T, U> implements Serializable {
    T first;
    U second;

    Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}
