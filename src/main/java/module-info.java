module com.ogr.splithappens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ogr.splithappens to javafx.fxml;
    exports com.ogr.splithappens;
}