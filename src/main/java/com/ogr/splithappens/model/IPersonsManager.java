package com.ogr.splithappens.model;

import java.util.List;

public interface IPersonsManager {
    List<Person> getPersons();

    IExpenseManager getExpenseManager();

    int getGlobalID();

    void incrementGlobalID();

    void addPerson(String name);

    Person getPersonByName(String name);
}
