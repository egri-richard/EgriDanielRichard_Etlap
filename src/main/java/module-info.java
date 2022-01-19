module com.petrik.egridanielrichard_etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.petrik.egridanielrichard_etlap to javafx.fxml;
    exports com.petrik.egridanielrichard_etlap;
}