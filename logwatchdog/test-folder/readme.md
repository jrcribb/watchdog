# Instrucciones de prueba

## Carpeta de prueba

La carpeta test-folder contiene todo lo necesario para efectuar la prueba del programa:

- Archivo JSON con la configuración: `lwd.json`
- Archivo LOG de prueba: `logquartz.txt` - Es un archivo real generado por al una aplicación que realiza un procesamiento de tareas tipo CRON, que son específicas de un software comercial para el ámbito de distribución de gas por red para la industria
- Archivo JAR del programa: `logwatchdog-1.0-SNAPSHOT-jar-with-dependencies`.
Como su nombre lo indica es el archivo JAR generado con MAVEN usando `mvn clean package`

## Ejecución

Para probar el programa ejecutar `logwatchdog.cmd` en la carpeta `test-folder` estando presente el archivo `lwd.json` que contiene la configuración y el archivo `logquartz.txt` que contiene un log de ejemplo.
