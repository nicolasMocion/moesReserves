module co.edu.uniquindio.moesreserves.moesreserves {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.logging;

    opens co.edu.uniquindio.moesreserves.moesreserves to javafx.fxml;
    exports co.edu.uniquindio.moesreserves.moesreserves;
    exports co.edu.uniquindio.moesreserves.moesreserves.viewController;
    opens co.edu.uniquindio.moesreserves.moesreserves.viewController to javafx.fxml;
    exports co.edu.uniquindio.moesreserves.moesreserves.controller;
    exports co.edu.uniquindio.moesreserves.moesreserves.mapping.dto;
    exports co.edu.uniquindio.moesreserves.moesreserves.mapping.mappers;
    exports co.edu.uniquindio.moesreserves.moesreserves.model;
    opens co.edu.uniquindio.moesreserves.moesreserves.controller to javafx.fxml;

}