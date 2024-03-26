module co.edu.uniquindio.moesreserves.moesreserves {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.mapstruct;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

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