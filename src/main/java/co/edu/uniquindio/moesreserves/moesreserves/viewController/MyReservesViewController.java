package co.edu.uniquindio.moesreserves.moesreserves.viewController;

import co.edu.uniquindio.moesreserves.moesreserves.MoesApplication;
import co.edu.uniquindio.moesreserves.moesreserves.controller.ReservaController;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.EventoDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.ReservaDto;
import co.edu.uniquindio.moesreserves.moesreserves.mapping.dto.UsuarioDto;
import co.edu.uniquindio.moesreserves.moesreserves.model.Evento;
import co.edu.uniquindio.moesreserves.moesreserves.model.MoesReserves;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MyReservesViewController {
    ReservaController reservaControllerService;
    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();
    ObservableList<ReservaDto> userListaReservasDto = FXCollections.observableArrayList();
    ReservaDto reservaSeleccionado;

    private String user;

    public void setUser(String user) {
        this.user = user;
    }
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<ReservaDto> tableReservas;

    @FXML
    private TableColumn<ReservaDto, String> tcId;

    @FXML
    private TableColumn<ReservaDto, String> tcFecha;

    @FXML
    private TableColumn<ReservaDto, String> tcUser;

    @FXML
    private TableColumn<ReservaDto, String> tcEvento;

    @FXML
    private TableColumn<ReservaDto, String> tcEstado;

    @FXML
    void initialize() {
        reservaControllerService = new ReservaController();
        setUser(this.user);
        reservaControllerService.consumirRespuestaCliente();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerReservas();
        userListaReservasDto = filterReserves(listaReservasDto, this.user, userListaReservasDto);
        tableReservas.getItems().clear();
        tableReservas.setItems(userListaReservasDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fechaDeSolicitud()));
        tcUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usuario()));
        tcEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().evento()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado()));
    }
    private void obtenerReservas() {
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
    }

    @FXML
    void eliminarReservaAction(ActionEvent event) {
        eliminarReserva();
    }
    @FXML
    void guardarReservaAction(ActionEvent event){
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        saveObservableListToCSV(userListaReservasDto, stage);
    }

    private void listenerSelection() {
        tableReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionado = newSelection;

        });
    }


    private void eliminarReserva() {
        boolean reservaEliminado = false;
        if(reservaSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al reserva?")){
                reservaEliminado = reservaControllerService.eliminarReserva(reservaSeleccionado.id());
                if(reservaEliminado == true){
                    userListaReservasDto.remove(reservaSeleccionado);
                    listaReservasDto.remove(reservaSeleccionado);
                    reservaSeleccionado = null;
                    tableReservas.getSelectionModel().clearSelection();
                    mostrarMensaje("Notificación reserva", "reserva eliminada", "La reserva se ha eliminado con éxito", AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación reserva", "reserva no eliminada", "La reserva no se puede eliminar", AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación reserva", "reserva no seleccionada", "Seleccionado una reserva de la lista", AlertType.WARNING);
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<ReservaDto> filterReserves(ObservableList<ReservaDto> listaReservasDto, String user, ObservableList<ReservaDto> listaUserReservasDto) {

        for (int i = 0; i < listaReservasDto.size(); i++) {

            if (listaReservasDto.get(i).getUser().equals(user)) {

                listaUserReservasDto.add(listaReservasDto.get(i));
            }

        }
        return listaUserReservasDto;

    }

    public static void saveObservableListToCSV(ObservableList<ReservaDto> data, Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        if (selectedFile != null) {
            try (FileWriter writer = new FileWriter(selectedFile)) {
                // Write header
                writer.append("Id,Usuario,Evento,Fecha,Estado\n");

                // Write data
                for (ReservaDto reserva : data) {
                    writer.append(reserva.getId())
                            .append(',')
                            .append(reserva.usuario())
                            .append(',')
                            .append(reserva.evento())
                            .append(',')
                            .append(reserva.fechaDeSolicitud())
                            .append(',')
                            .append(reserva.estado())
                            .append('\n');
                }
                System.out.println("CSV file was saved successfully!");
            } catch (IOException e) {
                System.err.println("An error occurred while saving the CSV file: " + e.getMessage());
            }
        }
    }

}
