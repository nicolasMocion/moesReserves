package co.edu.uniquindio.moesreserves.moesreserves.controller;

import co.edu.uniquindio.moesreserves.moesreserves.controller.Service.IMoesControllerService;
public class MoesController implements IMoesControllerService {

    ModelFactoryController modelFactoryController;

    public MoesController(){
        System.out.println("Llamando al singleton desde moesServiceController");
        modelFactoryController = ModelFactoryController.getInstance();
    }

}
