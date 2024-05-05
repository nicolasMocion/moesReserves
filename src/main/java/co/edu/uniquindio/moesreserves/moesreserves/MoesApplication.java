package co.edu.uniquindio.moesreserves.moesreserves;

import co.edu.uniquindio.moesreserves.moesreserves.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MoesApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Moes Reserves");
        mostrarVentanaPrincipal();
    }

    public void mostrarVentanaPrincipal() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MoesApplication.class.getResource("MoesSelect.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            MoesSelectViewController moesSelectViewController = loader.getController();
            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}