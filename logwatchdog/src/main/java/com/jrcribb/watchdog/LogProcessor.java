package com.jrcribb.watchdog;
// src/main/java/LogProcessor.java
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

abstract class LogProcessor {
    protected Config config;
    protected int lineas;
    protected String recentFechaHora;
    protected Pattern patronFechaHora;

    public LogProcessor(Config config) {
        this.config = config;
        this.lineas = 0;
        this.recentFechaHora = "";
        this.patronFechaHora = Pattern.compile(config.fechaHora);
    }

    public abstract void analizarLog();

    protected void printLogMultiLinea(String tipo, String linea) {
        int tamañoTrozo = config.anchoVista - tipo.length() - 2;
        String relleno = " ".repeat(tipo.length() + 2);
        List<String> trozos = dividirStringPorEspacios(linea, tamañoTrozo);
        if (trozos != null) {
            for (int i = 0; i < trozos.size(); i++) {
                if (i == 0) {
                    System.out.printf("%s: %s\n", tipo, trozos.get(i));
                } else {
                    System.out.printf("%s%s\n", relleno, trozos.get(i));
                }
            }
        }
    }

    protected List<String> dividirStringPorEspacios(String str, int tamTrozo) {
        List<String> trozos = new ArrayList<>();
        String[] palabras = str.split("\\s+");
        StringBuilder trozoActual = new StringBuilder();
        for (String palabra : palabras) {
            if (trozoActual.length() + palabra.length() > tamTrozo) {
                trozos.add(trozoActual.toString());
                if (palabra.length() > tamTrozo) {
                    trozos.add(palabra.substring(0, tamTrozo - 1));
                    trozoActual = new StringBuilder(palabra.substring(tamTrozo - 1) + " ");
                } else {
                    trozoActual = new StringBuilder(palabra + " ");
                }
            } else {
                trozoActual.append(palabra).append(" ");
            }
        }
        trozos.add(trozoActual.toString().trim());
        return trozos;    
    }

    protected boolean coincideConPatrones(String linea, List<String> patrones) {
        if (patrones!=null) {
            for (String patron : patrones) {
                if (Pattern.compile(patron).matcher(linea).find()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean esReciente(String fechahora, int offset) {
        int myOffset = config.antiguedadLogs - config.offset;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        LocalDateTime tiempo;
        try {
            tiempo = LocalDateTime.parse(fechahora, formatter);
        } catch (Exception e) {
            System.err.println("Error al parsear la cadena de fecha y hora: " + e.getMessage());
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tiempo, now);
        return diff.toHours() < myOffset;
    }

//    protected String esRecienteElLog(String fechaStr) {
//        int myOffset = config.antiguedadLogs - config.offset;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
//        LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter);
//        LocalDateTime now = LocalDateTime.now();
//        Duration diff = Duration.between(fecha, now.plusHours(myOffset));
//        if (diff.toMinutes() < 1) {
//            return String.format("Hace %d segundos\n", diff.getSeconds());
//        } else if (diff.toHours() < 1) {
//            long minutos = diff.toMinutes();
//            long segundos = diff.getSeconds() % 60;
//            return String.format("Hace %d minutos y %d segundos\n", minutos, segundos);
//        } else {
//            long horas = diff.toHours();
//            if (horas > 1) {
//                return String.format("Hace más de %d horas y probablemente está detenido el servicio\n", horas);
//            } else {
//                return String.format("Hace %d hora y probablemente está detenido el servicio\n", horas);
//            }
//        }
//    }
}
