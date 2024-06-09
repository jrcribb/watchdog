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
