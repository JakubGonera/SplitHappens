package com.ogr.splithappens.models;

import java.util.List;

public interface IPersonsManager {
    List<IPerson> getPersons();
    IExpenseManager getExpenseManager();
    int getGlobalID();
    void incrementGlobalID();


    /**
     * Attempts to add a person
     * @param name name of the person
     * @return if successful returns the sreated person
     */
    void addPerson(String name);

    IPerson getPersonByName(String name);
}
