package modelo;

public class GuiaTuristico {

    private String nombre;
    private String idioma;
    private String especialidad;

    public GuiaTuristico(String nombre, String idioma, String especialidad) {
        this.nombre = nombre;
        this.idioma = idioma;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}