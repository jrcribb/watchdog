package com.jrcribb.watchdog;
// src/main/java/LogWatchdog.java
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
//import java.util.regex.Pattern;

class LogWatchdog extends LogProcessor {

    public LogWatchdog(Config config) {
        super(config);
    }

    @Override
    public void analizarLog() {
        String ultimaLineaMaster = "";
        boolean lineaMaster = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(config.logfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineaMaster = false;
                lineas++;
                Matcher matcher = patronFechaHora.matcher(line);
                if (matcher.find()) {
                    recentFechaHora = matcher.group(0).substring(0, 19);
                    ultimaLineaMaster = line.substring(20);
                    lineaMaster = true;
                }
                if ((config.antiguedadLogs == 0) || esReciente(recentFechaHora, config.offset)) {
                    if (coincideConPatrones(line, config.patronesNormales)) {
                        if (config.mostrarNormales) {
                            printLogMultiLinea("NORMAL", line);
                        }
                    } else {
                        String mensaje = "";
                        if (coincideConPatrones(line, config.patronesAnomalias)) {
                            if (config.mostrarErrores) {
                                if (patronFechaHora.matcher(line).find()) {
                                    mensaje = line;
                                } else {
                                    mensaje = recentFechaHora + ultimaLineaMaster + " \n" + line;
                                }
                                printLogMultiLinea("ERROR", mensaje);
                            }
                        } else {
                            if (coincideConPatrones(line, config.patronesAvisos)) {
                                if (config.mostrarAvisos) {
                                    if (patronFechaHora.matcher(line).find()) {
                                        mensaje = line;
                                    } else {
                                        mensaje = recentFechaHora + ultimaLineaMaster + " \n" + line;
                                    }
                                    printLogMultiLinea("AVISO", mensaje);
                                }
                            } else {
                                if (!config.ignorarDesconocidos && lineaMaster) {
                                    mensaje = line;
                                    printLogMultiLinea("DUDAS", mensaje);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String configFile = "lwd.json";
        Config config = obtenerConfigDesdeArchivo(configFile);
        if (config == null) {
            System.err.println("Error al obtener configuración");
            return;
        }
        LogProcessor processor = new LogWatchdog(config);
        System.out.printf("lwd (%s) - Log watchdog\n", config.version);
        System.out.printf("- Procesando log: %s\n", config.logfile);
        processor.analizarLog();
        System.out.printf("- Procesadas %d líneas\n", processor.lineas);
        System.out.printf("- Última actualización del log %s.\n", processor.recentFechaHora);
    }

    static Config obtenerConfigDesdeArchivo(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Config.class);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
