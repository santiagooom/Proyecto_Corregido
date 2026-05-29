package modelo;

import java.util.ArrayList;

public class PaqueteTuristico {

    private String nombre;
    private LugarTuristico lugar;
    private GuiaTuristico guia;
    private double precio;
    private String diaSalida;
    private String lugarSalida;
    private String lugarLlegada;
    private String hotel;
    private String diaRetorno;
    private String transporte;
    private String duracion;
    private int cupoMaximo;
    private ArrayList<Turista> inscritos;

    public PaqueteTuristico(String nombre, LugarTuristico lugar, GuiaTuristico guia,
                            double precio, String diaSalida, String lugarSalida,
                            String lugarLlegada, String hotel, String diaRetorno,
                            String transporte, String duracion, int cupoMaximo) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.guia = guia;
        this.precio = precio;
        this.diaSalida = diaSalida;
        this.lugarSalida = lugarSalida;
        this.lugarLlegada = lugarLlegada;
        this.hotel = hotel;
        this.diaRetorno = diaRetorno;
        this.transporte = transporte;
        this.duracion = duracion;
        this.cupoMaximo = cupoMaximo;
        this.inscritos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public LugarTuristico getLugar() {
        return lugar;
    }

    public GuiaTuristico getGuia() {
        return guia;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDiaSalida() {
        return diaSalida;
    }

    public String getDiasSalida() {
        return diaSalida;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public String getLugarLlegada() {
        return lugarLlegada;
    }

    public String getHotel() {
        return hotel;
    }

    public String getDiaRetorno() {
        return diaRetorno;
    }

    public String getTransporte() {
        return transporte;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getCuposDisponibles() {
        return cupoMaximo - inscritos.size();
    }

    public boolean inscribirTurista(Turista turista) {
        if (turista == null) {
            return false;
        }

        if (inscritos.contains(turista)) {
            return false;
        }

        if (getCuposDisponibles() > 0) {
            inscritos.add(turista);
            turista.agregarPaquete(this);
            return true;
        }

        return false;
    }

    public String mostrarInformacion() {
        return "Paquete: " + nombre + "\n"
                + "Región: " + lugar.getCategoria() + "\n"
                + "Ciudades a visitar: " + lugar.getNombre() + "\n"
                + "Ubicación: " + lugar.getUbicacion() + "\n"
                + "Guía asignado: " + guia.getNombre() + "\n"
                + "Idioma del guía: " + guia.getIdioma() + "\n"
                + "Especialidad del guía: " + guia.getEspecialidad() + "\n"
                + "Día de salida: " + diaSalida + "\n"
                + "Lugar de salida: " + lugarSalida + "\n"
                + "Lugar de llegada: " + lugarLlegada + "\n"
                + "Hotel: " + hotel + "\n"
                + "Día de retorno: " + diaRetorno + "\n"
                + "Transporte: " + transporte + "\n"
                + "Duración: " + duracion + "\n"
                + "Precio: $" + precio + "\n"
                + "Cupos disponibles: " + getCuposDisponibles() + "\n";
    }
}