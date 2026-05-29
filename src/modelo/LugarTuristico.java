package modelo;

public class LugarTuristico {

    private String nombre;
    private String categoria;
    private String ubicacion;

    public LugarTuristico(String nombre, String categoria, String ubicacion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}