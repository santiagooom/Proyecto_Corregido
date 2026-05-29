package modelo;

import java.util.ArrayList;

public class Turista {

    private String nombre;
    private String correo;
    private String idioma;
    private ArrayList<String> intereses;
    private ArrayList<PaqueteTuristico> paquetesRealizados;

    public Turista(String nombre, String correo, String idioma) {
        this.nombre = nombre;
        this.correo = correo;
        this.idioma = idioma;
        this.intereses = new ArrayList<>();
        this.paquetesRealizados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getIdioma() {
        return idioma;
    }

    public ArrayList<String> getIntereses() {
        return intereses;
    }

    public ArrayList<PaqueteTuristico> getPaquetesRealizados() {
        return paquetesRealizados;
    }

    public void agregarInteres(String interes) {
        if (interes != null && !interes.isEmpty() && !intereses.contains(interes)) {
            intereses.add(interes.toLowerCase());
        }
    }

    public boolean tieneInteres(String categoria) {
        for (String interes : intereses) {
            if (interes.equalsIgnoreCase(categoria)) {
                return true;
            }
        }
        return false;
    }

    public void agregarPaquete(PaqueteTuristico paquete) {
        if (paquete != null && !paquetesRealizados.contains(paquete)) {
            paquetesRealizados.add(paquete);
        }
    }
}