package negocio;

import java.util.ArrayList;
import modelo.GuiaTuristico;
import modelo.LugarTuristico;
import modelo.PaqueteTuristico;
import modelo.Recomendacion;
import modelo.Turista;

public class AgenciaTuristica {

    private ArrayList<Turista> turistas;
    private ArrayList<PaqueteTuristico> paquetes;
    private ArrayList<Recomendacion> recomendaciones;

    public AgenciaTuristica() {
        turistas = new ArrayList<>();
        paquetes = new ArrayList<>();
        recomendaciones = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {

        GuiaTuristico guiaCosta = new GuiaTuristico(
                "Santiago Viteri",
                "Español",
                "Región Costa"
        );

        GuiaTuristico guiaSierra = new GuiaTuristico(
                "Mateo Benitez",
                "Español",
                "Región Sierra"
        );

        GuiaTuristico guiaOriente = new GuiaTuristico(
                "David Valle",
                "Español",
                "Región Oriente"
        );

        GuiaTuristico guiaGalapagos = new GuiaTuristico(
                "Junhao Zhao",
                "Español / Inglés",
                "Región Galápagos"
        );

        LugarTuristico lugarCosta = new LugarTuristico(
                "Manta, Puerto López e Isla de la Plata",
                "costa",
                "Manabí, Ecuador"
        );

        LugarTuristico lugarSierra = new LugarTuristico(
                "Latacunga, Quilotoa y Baños de Agua Santa",
                "sierra",
                "Cotopaxi, Tungurahua y Sierra Centro"
        );

        LugarTuristico lugarOriente = new LugarTuristico(
                "Puyo, Pastaza y comunidad amazónica",
                "oriente",
                "Pastaza, Amazonía ecuatoriana"
        );

        LugarTuristico lugarGalapagos = new LugarTuristico(
                "Santa Cruz, Puerto Ayora e Isla Santa Fe",
                "galapagos",
                "Galápagos, Ecuador"
        );

        paquetes.add(new PaqueteTuristico(
                "Costa",
                lugarCosta,
                guiaCosta,
                260.00,
                "Jueves",
                "Terminal Terrestre Quitumbe, Quito - 06h00",
                "Terminal Terrestre de Puerto López, Manabí",
                "Hotel turístico estándar en Puerto López",
                "Domingo - 17h00, retorno hacia Quito",
                "Bus interprovincial + transporte turístico local",
                "4 días / 3 noches",
                12
        ));

        paquetes.add(new PaqueteTuristico(
                "Sierra",
                lugarSierra,
                guiaSierra,
                220.00,
                "Viernes",
                "Terminal Terrestre Quitumbe, Quito - 07h00",
                "Terminal Terrestre de Baños de Agua Santa",
                "Hotel turístico estándar en Baños",
                "Lunes - 16h00, retorno hacia Quito",
                "Bus interprovincial + transporte turístico local",
                "4 días / 3 noches",
                15
        ));

        paquetes.add(new PaqueteTuristico(
                "Oriente",
                lugarOriente,
                guiaOriente,
                310.00,
                "Jueves",
                "Terminal Terrestre Quitumbe, Quito - 06h30",
                "Terminal Terrestre de Puyo, Pastaza",
                "Lodge amazónico turístico en Puyo",
                "Domingo - 18h00, retorno hacia Quito",
                "Bus interprovincial + transporte turístico local",
                "4 días / 3 noches",
                10
        ));

        paquetes.add(new PaqueteTuristico(
                "Galápagos",
                lugarGalapagos,
                guiaGalapagos,
                1150.00,
                "Jueves",
                "Aeropuerto Internacional Mariscal Sucre, Quito - 08h00",
                "Aeropuerto Ecológico Galápagos Seymour, Isla Baltra",
                "Hotel turístico estándar en Puerto Ayora",
                "Domingo - 16h00, vuelo de retorno hacia Quito",
                "Vuelo nacional + traslado terrestre y marítimo",
                "4 días / 3 noches",
                12
        ));
    }

    public boolean registrarTurista(Turista turista) {
        if (turista == null) {
            return false;
        }

        if (existeCorreo(turista.getCorreo())) {
            return false;
        }

        turistas.add(turista);
        return true;
    }

    public boolean existeCorreo(String correo) {
        if (correo == null) {
            return false;
        }

        for (Turista turista : turistas) {
            if (turista.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }

        return false;
    }

    public Turista buscarTuristaPorCorreo(String correo) {
        if (correo == null) {
            return null;
        }

        for (Turista turista : turistas) {
            if (turista.getCorreo().equalsIgnoreCase(correo)) {
                return turista;
            }
        }

        return null;
    }

    public ArrayList<PaqueteTuristico> getPaquetes() {
        return paquetes;
    }

    public ArrayList<Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

    public boolean inscribirTurista(Turista turista, int posicionPaquete) {
        if (turista == null) {
            return false;
        }

        if (posicionPaquete < 0 || posicionPaquete >= paquetes.size()) {
            return false;
        }

        PaqueteTuristico paquete = paquetes.get(posicionPaquete);
        return paquete.inscribirTurista(turista);
    }

    public boolean registrarRecomendacion(Turista turista, int posicionPaqueteRealizado,
                                          String comentario, int calificacion) {

        if (turista == null) {
            return false;
        }

        if (posicionPaqueteRealizado < 0 || posicionPaqueteRealizado >= turista.getPaquetesRealizados().size()) {
            return false;
        }

        if (comentario == null || comentario.trim().isEmpty()) {
            return false;
        }

        if (calificacion < 1 || calificacion > 5) {
            return false;
        }

        PaqueteTuristico paquete = turista.getPaquetesRealizados().get(posicionPaqueteRealizado);

        Recomendacion recomendacion = new Recomendacion(
                turista,
                paquete,
                comentario.trim(),
                calificacion
        );

        recomendaciones.add(recomendacion);
        return true;
    }

    public String generarHistorial(Turista turista) {
        StringBuilder texto = new StringBuilder();

        texto.append("HISTORIAL DEL TURISTA\n\n");
        texto.append("Nombre: ").append(turista.getNombre()).append("\n");
        texto.append("Correo: ").append(turista.getCorreo()).append("\n");
        texto.append("Idioma: ").append(turista.getIdioma()).append("\n\n");

        texto.append("Regiones de interés:\n");

        for (String interes : turista.getIntereses()) {
            texto.append("- ").append(interes).append("\n");
        }

        texto.append("\nPaquetes realizados:\n");

        if (turista.getPaquetesRealizados().isEmpty()) {
            texto.append("No tiene paquetes registrados.\n");
        } else {
            for (PaqueteTuristico paquete : turista.getPaquetesRealizados()) {
                texto.append("- ").append(paquete.getNombre()).append("\n");
                texto.append("  Ciudades a visitar: ").append(paquete.getLugar().getNombre()).append("\n");
                texto.append("  Guía: ").append(paquete.getGuia().getNombre()).append("\n");
                texto.append("  Día de salida: ").append(paquete.getDiaSalida()).append("\n");
                texto.append("  Lugar de salida: ").append(paquete.getLugarSalida()).append("\n");
                texto.append("  Lugar de llegada: ").append(paquete.getLugarLlegada()).append("\n");
                texto.append("  Hotel: ").append(paquete.getHotel()).append("\n");
                texto.append("  Retorno: ").append(paquete.getDiaRetorno()).append("\n");
                texto.append("  Precio: $").append(paquete.getPrecio()).append("\n\n");
            }
        }

        return texto.toString();
    }
}