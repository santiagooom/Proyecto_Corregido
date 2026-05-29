package interfaz;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import modelo.PaqueteTuristico;
import modelo.Recomendacion;
import modelo.Turista;
import negocio.AgenciaTuristica;

public class Main {

    static AgenciaTuristica agencia = new AgenciaTuristica();

    public static void main(String[] args) {
        int opcion;

        do {
            opcion = leerEntero(
                    "SISTEMA DE AGENCIA TURÍSTICA DEL ECUADOR\n"
                            + "MENÚ PRINCIPAL\n\n"
                            + "1. Registrarme como turista\n"
                            + "2. Paquetes disponibles\n"
                            + "3. Recomendar e inscribir paquete\n"
                            + "4. Consultar mi historial y dejar recomendación\n"
                            + "5. Portal de guías\n"
                            + "6. Salir\n\n"
                            + "Elige una opción:"
            );

            if (opcion == -1) {
                opcion = 6;
            }

            switch (opcion) {
                case 1:
                    registrarTurista();
                    break;
                case 2:
                    verDisponibilidadPaquetes();
                    break;
                case 3:
                    recomendarEInscribirPaquete();
                    break;
                case 4:
                    consultarHistorialYRecomendar();
                    break;
                case 5:
                    portalGuias();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
                    break;
            }

        } while (opcion != 6);
    }

    public static void registrarTurista() {
        String nombre = JOptionPane.showInputDialog("Ingresa tu nombre:");
        String correo = JOptionPane.showInputDialog("Ingresa tu correo:");
        String idioma = JOptionPane.showInputDialog("Ingresa tu idioma:");

        if (nombre == null || correo == null || idioma == null
                || nombre.trim().isEmpty()
                || correo.trim().isEmpty()
                || idioma.trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Faltan datos. No se registró el turista.");
            return;
        }

        correo = correo.trim();

        if (agencia.existeCorreo(correo)) {
            JOptionPane.showMessageDialog(null,
                    "Ese correo ya se encuentra registrado.\n"
                            + "No pueden existir dos turistas con el mismo correo."
            );
            return;
        }

        String interesesTexto = JOptionPane.showInputDialog(
                "En nuestros paquetes tenemos las siguientes regiones del Ecuador:\n\n"
                        + "- Costa\n"
                        + "- Sierra\n"
                        + "- Oriente\n"
                        + "- Galápagos\n\n"
                        + "Ingresa la región de tu interés.\n"
                        + "Si deseas más de una, sepáralas por coma.\n\n"
                        + "Ejemplo: costa, galapagos"
        );

        if (interesesTexto == null || interesesTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar al menos una región de interés.");
            return;
        }

        Turista turista = new Turista(nombre.trim(), correo, idioma.trim());

        String[] intereses = interesesTexto.split(",");
        boolean tieneRegionValida = false;
        StringBuilder regionesInvalidas = new StringBuilder();

        for (String interes : intereses) {
            String region = normalizarTexto(interes.trim());

            if (esRegionValida(region)) {
                turista.agregarInteres(region);
                tieneRegionValida = true;
            } else if (!region.isEmpty()) {
                regionesInvalidas.append("- ").append(interes.trim()).append("\n");
            }
        }

        if (!tieneRegionValida) {
            JOptionPane.showMessageDialog(null,
                    "No ingresaste ninguna región válida.\n\n"
                            + "Regiones válidas:\n"
                            + "- costa\n"
                            + "- sierra\n"
                            + "- oriente\n"
                            + "- galapagos"
            );
            return;
        }

        boolean registrado = agencia.registrarTurista(turista);

        if (registrado) {
            String mensaje = "Turista registrado correctamente.";

            if (regionesInvalidas.length() > 0) {
                mensaje += "\n\nEstas regiones no se guardaron porque no son válidas:\n"
                        + regionesInvalidas;
            }

            JOptionPane.showMessageDialog(null, mensaje);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ese correo ya se encuentra registrado.\n"
                            + "No se pudo registrar el turista."
            );
        }
    }

    public static void verDisponibilidadPaquetes() {
        ArrayList<PaqueteTuristico> paquetes = agencia.getPaquetes();

        if (paquetes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No existen paquetes turísticos registrados.");
            return;
        }

        StringBuilder texto = new StringBuilder();

        texto.append("DISPONIBILIDAD DE PAQUETES TURÍSTICOS\n\n");

        for (int i = 0; i < paquetes.size(); i++) {
            PaqueteTuristico paquete = paquetes.get(i);

            texto.append(i + 1).append(". ").append(paquete.getNombre()).append("\n");
            texto.append("Región: ").append(paquete.getLugar().getCategoria()).append("\n");
            texto.append("Ciudades a visitar: ").append(paquete.getLugar().getNombre()).append("\n");
            texto.append("Ubicación: ").append(paquete.getLugar().getUbicacion()).append("\n");
            texto.append("Guía asignado: ").append(paquete.getGuia().getNombre()).append("\n");
            texto.append("Día de salida: ").append(paquete.getDiaSalida()).append("\n");
            texto.append("Lugar de salida: ").append(paquete.getLugarSalida()).append("\n");
            texto.append("Lugar de llegada: ").append(paquete.getLugarLlegada()).append("\n");
            texto.append("Hotel: ").append(paquete.getHotel()).append("\n");
            texto.append("Día de retorno: ").append(paquete.getDiaRetorno()).append("\n");
            texto.append("Transporte: ").append(paquete.getTransporte()).append("\n");
            texto.append("Duración: ").append(paquete.getDuracion()).append("\n");
            texto.append("Precio: $").append(paquete.getPrecio()).append("\n");
            texto.append("Cupos disponibles: ").append(paquete.getCuposDisponibles()).append("\n");


            texto.append("\n");
        }

        mostrarTextoConScroll(texto.toString(), "Paquetes disponibles");
    }

    public static void recomendarEInscribirPaquete() {
        Turista turista = pedirTurista();

        if (turista == null) {
            return;
        }

        ArrayList<PaqueteTuristico> paquetes = agencia.getPaquetes();

        if (paquetes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No existen paquetes turísticos registrados.");
            return;
        }

        String[] opciones = new String[paquetes.size()];

        for (int i = 0; i < paquetes.size(); i++) {
            PaqueteTuristico paquete = paquetes.get(i);

            String recomendado = "";

            if (turista.tieneInteres(paquete.getLugar().getCategoria())) {
                recomendado = "[RECOMENDADO] ";
            }

            opciones[i] = recomendado
                    + paquete.getNombre()
                    + " | Guía: " + paquete.getGuia().getNombre()
                    + " | " + paquete.getDuracion()
                    + " | $" + paquete.getPrecio()
                    + " | Cupos: " + paquete.getCuposDisponibles();
        }

        JComboBox<String> comboPaquetes = new JComboBox<>(opciones);

        int seleccion = JOptionPane.showConfirmDialog(
                null,
                comboPaquetes,
                "Selecciona un paquete turístico",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (seleccion != JOptionPane.OK_OPTION) {
            return;
        }

        int indicePaquete = comboPaquetes.getSelectedIndex();
        PaqueteTuristico paqueteElegido = paquetes.get(indicePaquete);

        JTextArea areaDetalle = new JTextArea(paqueteElegido.mostrarInformacion());
        areaDetalle.setEditable(false);
        areaDetalle.setLineWrap(true);
        areaDetalle.setWrapStyleWord(true);
        areaDetalle.setRows(16);
        areaDetalle.setColumns(48);

        JScrollPane scroll = new JScrollPane(areaDetalle);

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                scroll,
                "Detalle del paquete seleccionado. ¿Deseas inscribirte?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        boolean inscrito = agencia.inscribirTurista(turista, indicePaquete);

        if (inscrito) {
            JOptionPane.showMessageDialog(null,
                    "Inscripción realizada correctamente.\n\n"
                            + "Turista: " + turista.getNombre() + "\n"
                            + "Paquete: " + paqueteElegido.getNombre() + "\n"
                            + "Guía asignado: " + paqueteElegido.getGuia().getNombre() + "\n"
                            + "Salida: " + paqueteElegido.getLugarSalida() + "\n"
                            + "Llegada: " + paqueteElegido.getLugarLlegada() + "\n"
                            + "Hotel: " + paqueteElegido.getHotel() + "\n"
                            + "Retorno: " + paqueteElegido.getDiaRetorno() + "\n"
                            + "Cupos disponibles: " + paqueteElegido.getCuposDisponibles()
            );
        } else {
            JOptionPane.showMessageDialog(null,
                    "No se pudo inscribir.\n"
                            + "Puede que el paquete ya no tenga cupos disponibles\n"
                            + "o que el turista ya esté inscrito en ese paquete."
            );
        }
    }

    public static void consultarHistorialYRecomendar() {
        Turista turista = pedirTurista();

        if (turista == null) {
            return;
        }

        String historial = agencia.generarHistorial(turista);
        mostrarTextoConScroll(historial, "Historial del turista");

        if (turista.getPaquetesRealizados().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Aún no has realizado ningún paquete.\n"
                            + "Por eso todavía no puedes dejar una recomendación."
            );
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Deseas dejar una recomendación sobre uno de tus paquetes realizados?",
                "Registrar recomendación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            registrarRecomendacionDelTurista(turista);
        }
    }

    public static void registrarRecomendacionDelTurista(Turista turista) {
        ArrayList<PaqueteTuristico> paquetesRealizados = turista.getPaquetesRealizados();
        String[] opciones = new String[paquetesRealizados.size()];

        for (int i = 0; i < paquetesRealizados.size(); i++) {
            opciones[i] = paquetesRealizados.get(i).getNombre()
                    + " | Guía: " + paquetesRealizados.get(i).getGuia().getNombre();
        }

        JComboBox<String> comboPaquetes = new JComboBox<>(opciones);

        int seleccion = JOptionPane.showConfirmDialog(
                null,
                comboPaquetes,
                "Selecciona el paquete que deseas recomendar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (seleccion != JOptionPane.OK_OPTION) {
            return;
        }

        int indicePaquete = comboPaquetes.getSelectedIndex();

        String comentario = JOptionPane.showInputDialog("Escribe tu recomendación sobre el paquete:");
        int calificacion = leerEntero("Calificación del 1 al 5:");

        boolean registrado = agencia.registrarRecomendacion(
                turista,
                indicePaquete,
                comentario,
                calificacion
        );

        if (registrado) {
            JOptionPane.showMessageDialog(null, "Recomendación registrada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "No se pudo registrar la recomendación.\n"
                            + "Verifica que el comentario no esté vacío y que la calificación sea del 1 al 5."
            );
        }
    }

    public static void portalGuias() {
        boolean accesoPermitido = validarGuia();

        if (!accesoPermitido) {
            return;
        }

        int opcion;

        do {
            opcion = leerEntero(
                    "PORTAL DE GUÍAS\n\n"
                            + "1. Ver recomendaciones de turistas\n"
                            + "2. Ver paquetes disponibles\n"
                            + "3. Volver al menú principal\n\n"
                            + "Elige una opción:"
            );

            if (opcion == -1) {
                opcion = 3;
            }

            switch (opcion) {
                case 1:
                    verRecomendaciones();
                    break;
                case 2:
                    verDisponibilidadPaquetes();
                    break;
                case 3:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
                    break;
            }

        } while (opcion != 3);
    }

    public static boolean validarGuia() {
        String[] nombresGuias = {
                "Santiago Viteri",
                "Mateo Benitez",
                "David Valle",
                "Junhao Zhao"
        };

        String[] clavesGuias = {
                "A00128541",
                "PENDIENTE_MATEO",
                "PENDIENTE_DAVID",
                "PENDIENTE_JUNHAO"
        };

        JComboBox<String> comboGuias = new JComboBox<>(nombresGuias);

        int seleccion = JOptionPane.showConfirmDialog(
                null,
                comboGuias,
                "Selecciona el guía",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (seleccion != JOptionPane.OK_OPTION) {
            return false;
        }

        int indiceGuia = comboGuias.getSelectedIndex();

        if (clavesGuias[indiceGuia].startsWith("PENDIENTE")) {
            JOptionPane.showMessageDialog(null,
                    "La credencial de este guía aún no está configurada.\n"
                            + "Por ahora solo Santiago Viteri tiene acceso registrado."
            );
            return false;
        }

        String claveIngresada = JOptionPane.showInputDialog(
                "Ingresa la clave o ID Banner de " + nombresGuias[indiceGuia] + ":"
        );

        if (claveIngresada == null || claveIngresada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Clave inválida.");
            return false;
        }

        if (claveIngresada.trim().equalsIgnoreCase(clavesGuias[indiceGuia])) {
            JOptionPane.showMessageDialog(null,
                    "Acceso permitido.\n"
                            + "Bienvenido, " + nombresGuias[indiceGuia] + "."
            );
            return true;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Acceso denegado.\n"
                            + "La clave no corresponde al guía seleccionado."
            );
            return false;
        }
    }

    public static void verRecomendaciones() {
        ArrayList<Recomendacion> recomendaciones = agencia.getRecomendaciones();

        if (recomendaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay recomendaciones registradas.");
            return;
        }

        StringBuilder texto = new StringBuilder("RECOMENDACIONES DE TURISTAS\n\n");

        for (Recomendacion recomendacion : recomendaciones) {
            texto.append(recomendacion.mostrarInformacion()).append("\n");
        }

        mostrarTextoConScroll(texto.toString(), "Recomendaciones registradas");
    }

    public static Turista pedirTurista() {
        String correo = JOptionPane.showInputDialog("Ingresa tu correo:");

        if (correo == null || correo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Correo inválido.");
            return null;
        }

        Turista turista = agencia.buscarTuristaPorCorreo(correo.trim());

        if (turista == null) {
            JOptionPane.showMessageDialog(null, "Turista no encontrado.");
        }

        return turista;
    }

    public static int leerEntero(String mensaje) {
        try {
            String texto = JOptionPane.showInputDialog(mensaje);

            if (texto == null || texto.trim().isEmpty()) {
                return -1;
            }

            return Integer.parseInt(texto.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean esRegionValida(String region) {
        return region.equals("costa")
                || region.equals("sierra")
                || region.equals("oriente")
                || region.equals("galapagos");
    }

    public static String normalizarTexto(String texto) {
        return texto.toLowerCase()
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("ñ", "n");
    }

    public static void mostrarTextoConScroll(String texto, String titulo) {
        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setRows(17);
        areaTexto.setColumns(52);

        JScrollPane scroll = new JScrollPane(areaTexto);

        JOptionPane.showMessageDialog(
                null,
                scroll,
                titulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}