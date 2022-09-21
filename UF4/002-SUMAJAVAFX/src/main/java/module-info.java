module ivan.quiroz.sumajavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ivan.quiroz.sumajavafx to javafx.fxml;
    exports ivan.quiroz.sumajavafx;
}