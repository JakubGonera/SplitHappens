package com.ogr.splithappens.models;

import java.util.List;

/**
 * Person interface providing simple and detailed balance information for use in ViewModel
 */
public interface IPerson {
    record detailedBalance(String name, int id, int balance) {
    }


    String getName();

    int getID();

    int getBalance();

    List<detailedBalance> getDetailedBalances();
}
