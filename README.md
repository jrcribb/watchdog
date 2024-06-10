# Analizador automatizado de logs de sistemas informáticos

Está compuesto de un sistema analizador basado en expresiones regulares regexp que toman un archivo de log indicado para efectuar procesos de parsing y análisis buscando patrones que puedan permitir la clasificación de los incidentes en críticos, normales, etc.

## Configuración

Se configura mediante archivos JSON a fin de que no sea necesaria una implementación de ningún servidor de BD para contener sus parametrizaciones. Todo se realiza desde la línea de comandos y sólo requiere un editor de textos.

## Patrones de reconocimiento

Los patrones de reconocimiento se configuran como expresiones regexp. No hace falta ser especialista en expresiones regulares. Se puede usar algún entorno de IA, como ser ChatGPT, y hacer la siguiente pregunta:

Usuario:

> Tengo el texto "El sistema no puede hallar el archivo xyz.file, revise la configuración" y quiero que me generes un patrón regexp que me permita identificar si la línea contiene el texto indicado teniendo en cuenta que "zyz.file" puede decir otra cosa, pero siempre será un nombre de archivo.

ChatGPT:

> Para crear un patrón regexp que identifique una línea que contiene el texto "El sistema no puede hallar el archivo xyz.file, revise la configuración", teniendo en cuenta que "xyz.file" puede ser cualquier nombre de archivo, puedes utilizar la siguiente expresión regular:
> 
> ```regexp
> El sistema no puede hallar el archivo \S+\.\S+, revise la configuraci[oó]n
> ```
> 
> Explicación:
> 
> - "El sistema no puede hallar el archivo" es el texto fijo que debe aparecer al inicio.
>   
> - "\S+" busca una secuencia de uno o más caracteres que no sean espacios (parte del nombre del archivo).
>   
> - "\." busca un punto literal (separador de nombre y extensión).
>   
> - "\S+" busca una secuencia de uno o más caracteres que no sean espacios (parte de la extensión del archivo).
>   
> - ", revise la configuraci[oó]n" es el texto fijo que sigue al nombre del archivo. 
>   Nota: Se incluye configuraci[oó]n para considerar ambas formas, con o sin acento en la "ó".
>   
> 
> Esta expresión regular encontrará cualquier línea que siga el patrón especificado, sin importar el nombre específico del archivo.

## Finalidad del programa

La finalidad del programa es servir de apoyo al analista de soporte para efectuar análisis de logs de miles de líneas de log en segundos y obtener solamente lo que es de su interés.

## Ejemplo de configuración

A los efectos de la prueba se ha incorporado un ejemplo real de configuración en el archivo `lwd.json`

```XML
{
    "version": "1.0",
    "logfile": "logquartz.txt",
    "offset": 2,
    "antiguedadLogs": 0,
    "anchoVista": 80,
    "fechaHora": "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{4}.*",
    "ignorarDesconocidos": true,
    "mostrarNormales": false,
    "mostrarErrores": true,
    "mostrarAvisos": true,
    "patronesNormales": [
        "Quartz.Core.QuartzSchedulerThread Batch acquisition of ",
        "Quartz.Core.JobRunShell Calling Execute on job (.+?)",
        "SziCom.Componentes.Common.Logging.Logger El Job (.+?)",
        "SziCom.Componentes.Common.Logging.Logger Finaliza el Proceso MAIL Mail",
        "Quartz.Core.JobRunShell Trigger instruction : NoInstruction",
        "Quartz.Core.QuartzSchedulerThread Batch acquisition of",
        "SziCom.Componentes.Common.Logging.Logger SCADA Horario: inicia el proceso",
        "SziCom.Componentes.Common.Logging.Logger SCADA Horario: Proceso del archivo",
        "SziCom.Degas.Quartz.Program Se detiene el Quartz Service. Codigo de Salida: 0",
        "Quartz.Simpl.TaskSchedulingThreadPool Shutting down threadpool...",
        "Quartz.Simpl.TaskSchedulingThreadPool Shutdown of threadpool complete.",
        "Quartz.Core.QuartzScheduler Scheduler DegasQuartzScheduler_\\$_NON_CLUSTERED",
        "SziCom.Componentes.Common.Logging.Logger Se detiene el servicioSziCom.Degas.Quartz.Servicios.DegasQuartzService",
        "SziCom.Componentes.Common.Logging.Logger Se ha detenido el servicio quartz",
        "Topshelf.Runtime.Windows.WindowsServiceHost \\[Topshelf\\]",
        "SziCom.Componentes.Common.Logging.Logger SCADA Horario: finalizó el proceso",
        "SziCom.Componentes.Common.Logging.Logger Inicia el Proceso MAIL Mail",
        "SziCom.Componentes.Common.Logging.Logger Ejecutando el Job (.+?) no se obtuvieron datos desde ABI",
        "SziCom.Componentes.Common.Logging.Logger El Job (.+?) ha finalizado su ejecución",
        "Quartz.Core.JobRunShell Calling Execute on job (.+?)",
        "SziCom.Componentes.Common.Logging.Logger El Job (.+?) se ejecutará a continuación",
        "SziCom.Componentes.Common.Logging.Logger Inicia el Proceso TEOS UploadMpm",
        "SziCom.Componentes.Common.Logging.Logger El dia operativo es (.+?)",
        "SziCom.Componentes.Common.Logging.Logger La fecha y hora del proceso es (.+?)",
        "SziCom.Componentes.Common.Logging.Logger Inicia el Proceso TEOS UploadCDO",
        "SziCom.Componentes.Common.Logging.Logger El dia operativo para generar archivo CDO (.+?)",
        "SziCom.Componentes.Common.Logging.Logger Ruta FTP =  ftp://ftp.enargas.gob.ar/TEOS/(.+?).txt",
        "SziCom.Degas.Jobs.DegasQuartzJob The HTTP status code of the response was not expected \\((\\d+)\\)\\.",
        "SziCom.Componentes.Common.Logging.Logger El Job (.+?) ha finalizado su ejecución",
        "SziCom.Componentes.Common.Logging.Logger SCADA Horario --> Linea:"
      ],
      "patronesAnomalias": [
        "https://abiiws.tgn.com.ar/api/nomination",
        "SCADA: No está definida la RTU con nemónico",
        "String was not recognized as a valid DateTime.",
        "SziCom.Degas.Jobs.DegasQuartzJob Unable to connect to the remote serverSystem.Net.WebException: Unable to connect to the remote server",
        "SziCom.Degas.Jobs.DegasQuartzJob An error occurred while updating the entries",
        "SziCom.Degas.Quartz.Program Se detiene el Quartz",
        "SziCom.Degas.Jobs.DegasQuartzJob Network error: Connection to (.+?) timed out",
        "SziCom.Degas.Jobs.DegasQuartzJob An error occurred while sending the request.System.Net.Http.HttpRequestException",
        "SziCom.Degas.Jobs.DegasQuartzJob The network path was not found"
      ],
      "patronesAvisos": [
          "https://abiiws.tgn.com.ar/api/nomination",
          "SziCom.Degas.Jobs.DegasQuartzJob No existen Temperaturas reales para el Día Operativo (.+?)",
          "SziCom.Componentes.Common.Logging.Logger No hay Temperatura Real en el Servicio Meteorológico para la localidad (.+?) para el día (.+?).",
          "SziCom.Componentes.Common.Logging.Logger Se genero el CDO para el dia (.+?)",
          "SziCom.Degas.Quartz.Program Se detiene el Quartz"
      ]
  }
```
