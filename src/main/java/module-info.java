module com.ogr.splithappens {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ogr.splithappens to javafx.fxml;
    opens com.ogr.splithappens.view to javafx.fxml;
//    opens com.ogr.splithappens.views.InvalidNamePopupView to javafx.fxml;
    exports com.ogr.splithappens;

}
