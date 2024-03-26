package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.controller.MoesController;
import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IMoesControllerService;
import javafx.fxml.FXML;
public class MoesViewController {

    IMoesControllerService moesControllerService;

    @FXML
    void initialize(){
        moesControllerService = new MoesController();
    }
}
