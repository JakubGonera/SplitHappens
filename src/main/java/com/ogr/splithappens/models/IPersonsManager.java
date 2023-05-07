package com.ogr.splithappens.models;

import java.util.List;

public interface IPersonsManager {
    List<IPerson> getPersons();
    void addPerson(String name);
}
