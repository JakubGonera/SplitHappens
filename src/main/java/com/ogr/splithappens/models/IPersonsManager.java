package com.ogr.splithappens.models;

import java.util.List;

public interface IPersonsManager {
    List<IPerson> getPersons();

    IExpenseManager getExpenseManager();

    int getGlobalID();

    void incrementGlobalID();

    void addPerson(String name);

    IPerson getPersonByName(String name);
}
