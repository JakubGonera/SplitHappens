package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.IPersonsManager;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class ViewModel implements IViewModel {
    private final IPersonsManager personsManager;
    // Remember to change personsList value whenever needed by personsList.setValue(personsManager.getPersons)
    private final SimpleObjectProperty<List<IPerson>> personsList;
    ViewModel(IPersonsManager personsManager){
        this.personsManager = personsManager;
        this.personsList = new SimpleObjectProperty<>(this, "personsList", personsManager.getPersons());
    }

    @Override
    public ReadOnlyProperty<List<IPerson>> getPersonsList() {
        return personsList;
    }
}
