module com.ogr.splithappens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ogr.splithappens.views to javafx.fxml;
//    opens com.ogr.splithappens to javafx.graphics;
    exports com.ogr.splithappens;

}
