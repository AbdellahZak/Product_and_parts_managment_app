module abdo.abdoc842 {
    requires javafx.controls;
    requires javafx.fxml;


    opens abdo.abdoc482 to javafx.fxml;
    exports abdo.abdoc482;
}