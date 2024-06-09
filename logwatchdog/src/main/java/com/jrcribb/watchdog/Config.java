package com.jrcribb.watchdog;
// src/main/java/Config.java
import java.util.List;

import com.google.gson.Gson;

class Config {
    String version;
    String logfile;
    int offset;
    int antiguedadLogs;
    int anchoVista;
    String fechaHora;
    boolean ignorarDesconocidos;
    boolean mostrarNormales;
    boolean mostrarErrores;
    boolean mostrarAvisos;
    List<String> patronesNormales;
    List<String> patronesAnomalias;
    List<String> patronesAvisos;

    Config(String jsonData) {
        Gson gson = new Gson();
        Config config = gson.fromJson(jsonData, Config.class);
        this.version = config.version;
        this.logfile = config.logfile;
        this.offset = config.offset;
        this.antiguedadLogs = config.antiguedadLogs;
        this.anchoVista = config.anchoVista;
        this.fechaHora = config.fechaHora;
        this.ignorarDesconocidos = config.ignorarDesconocidos;
        this.mostrarNormales = config.mostrarNormales;
        this.mostrarErrores = config.mostrarErrores;
        this.mostrarAvisos = config.mostrarAvisos;
        this.patronesNormales = config.patronesNormales;
        this.patronesAnomalias = config.patronesAnomalias;
        this.patronesAvisos = config.patronesAvisos;
    }
}
