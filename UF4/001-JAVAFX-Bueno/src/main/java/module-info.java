module ivan.quiroz.javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens ivan.quiroz.javafx to javafx.fxml;
    exports ivan.quiroz.javafx;
    exports ivan.quiroz.javafx.Controllers;
    opens ivan.quiroz.javafx.Controllers to javafx.fxml;
}