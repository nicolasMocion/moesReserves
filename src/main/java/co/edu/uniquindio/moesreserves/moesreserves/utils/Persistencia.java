package co.edu.uniquindio.moesreserves.moesreserves.utils;

import co.edu.uniquindio.moesreserves.moesreserves.model.*;
import co.edu.uniquindio.moesreserves.moesreserves.model.MoesReserves;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Persistencia {



    public static final String RUTA_ARCHIVO_EVENTOS = "src/main/resources/persistencia/archivos/archivoEventos.txt";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_RESERVAS = "src/main/resources/persistencia/archivos/archivoReservas.txt";
    public static final String RUTA_ARCHIVO_EMPLEADOS = "src/main/resources/persistencia/archivos/archivoEmpleados.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/MoesLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_MOES_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_MOES_XML = "src/main/resources/persistencia/model.xml";


    public static void cargarDatosArchivos(MoesReserves moesReserves) throws FileNotFoundException, IOException {
        //cargar archivo de clientes
        ArrayList<Evento> eventosCargados = cargarEventos();
        if (eventosCargados.size() > 0)
            moesReserves.getListaEventos().addAll(eventosCargados);

        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if (empleadosCargados.size() > 0)
            moesReserves.getListaEmpleados().addAll(empleadosCargados);

        ArrayList<Reserva> reservasCargadas = cargarReservas();
        if (reservasCargadas.size() > 0)
            moesReserves.getListaReservas().addAll(reservasCargadas);

        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if (usuariosCargados.size() > 0)
            moesReserves.getListaUsuarios().addAll(usuariosCargados);

    }

    public static void guardarEventos(ArrayList<Evento> listaEventos) throws IOException {
        // TODO Auto-generated method stub
        String contenido = "";
        for (Evento evento : listaEventos) {
            contenido += evento.getId() + "@@" + evento.getName() + "@@" + evento.getDescription() + "@@" + evento.getFecha()
                    + "@@" + evento.getMaxCapacity() + "@@" + evento.getEncargado() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EVENTOS, contenido, false);
    }


    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        for (Empleado empleado : listaEmpleados) {
            contenido += empleado.getName() +
                    "@@" + empleado.getId() +
                    "@@" + empleado.getEmail() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
    }

    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        // TODO Auto-generated method stub
        String contenido = "";
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario.getName());
            contenido += usuario.getName() + "@@" + usuario.getId() + "@@" + usuario.getEmail() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }

    public static void guardarReservas(ArrayList<Reserva> listaReservas) throws IOException {
        // TODO Auto-generated method stub
        String contenido = "";
        for (Reserva reserva : listaReservas) {
            contenido += reserva.getId() + "@@" + reserva.getUsuario() + "@@" + reserva.getEvento() + "@@" + reserva.getFechaDeSolicitud() + "@@" + reserva.getEstado() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_RESERVAS, contenido, false);
    }
    //	----------------------LOADS------------------------

    /**
     * @param
     * @param
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Evento> cargarEventos() throws FileNotFoundException, IOException {
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EVENTOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Evento evento = new Evento();
            evento.setId(linea.split("@@")[0]);
            evento.setName(linea.split("@@")[1]);
            evento.setDescription(linea.split("@@")[2]);
            evento.setFecha(linea.split("@@")[3]);
            evento.setMaxCapacity(linea.split("@@")[4]);
            evento.setEncargado(linea.split("@@")[5]);
            eventos.add(evento);
        }
        return eventos;
    }


    public static ArrayList<Empleado> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados = new ArrayList<Empleado>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Empleado empleado = new Empleado();
            empleado.setName(linea.split("@@")[0]);
            empleado.setId(linea.split("@@")[1]);
            empleado.setEmail(linea.split("@@")[2]);
            empleados.add(empleado);
        }
        return empleados;
    }

    public static ArrayList<Reserva> cargarReservas() throws FileNotFoundException, IOException {
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_RESERVAS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Reserva reserva = new Reserva();
            reserva.setId(linea.split("@@")[0]);
            reserva.setUsuario(linea.split("@@")[1]);
            reserva.setEvento(linea.split("@@")[2]);
            reserva.setFechaDeSolicitud(linea.split("@@")[3]);
            reserva.setEstado(linea.split("@@")[4]);
            reservas.add(reserva);
        }
        return reservas;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Usuario usuario = new Usuario();
            usuario.setName(linea.split("@@")[0]);
            usuario.setId(linea.split("@@")[1]);
            usuario.setEmail(linea.split("@@")[2]);

            usuarios.add(usuario);
        }
        return usuarios;
    }

    public static MoesReserves cargarRecursoMoesReservesBinario() {

        MoesReserves moesReserves = null;

        try {
            moesReserves = (MoesReserves) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_MOES_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return moesReserves;
    }

    public static void guardarRecursoMoesReservesBinario(MoesReserves moesReserves) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_MOES_BINARIO, moesReserves);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static MoesReserves cargarRecursoMoesReservesXML() {

        MoesReserves moesReserves = null;

        try {
            moesReserves = (MoesReserves) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_MOES_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return moesReserves;

    }


    public static void guardarRecursoMoesReservesXML(MoesReserves moesReserves) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_MOES_XML, moesReserves);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}



