package modelo;

public class Recomendacion {

    private Turista turista;
    private PaqueteTuristico paquete;
    private String comentario;
    private int calificacion;

    public Recomendacion(Turista turista, PaqueteTuristico paquete, String comentario, int calificacion) {
        this.turista = turista;
        this.paquete = paquete;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public String mostrarInformacion() {
        return "Turista: " + turista.getNombre() + "\n"
                + "Paquete recomendado: " + paquete.getNombre() + "\n"
                + "Lugar: " + paquete.getLugar().getNombre() + "\n"
                + "Región: " + paquete.getLugar().getCategoria() + "\n"
                + "Guía: " + paquete.getGuia().getNombre() + "\n"
                + "Calificación: " + calificacion + "/5\n"
                + "Comentario: " + comentario + "\n";
    }
}