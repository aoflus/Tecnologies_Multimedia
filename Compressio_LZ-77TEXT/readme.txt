LZ-77 TEXT Álvaro Ortega, Víctor Fernandez.

1-Comprimid el archivo “hamlet_short.txt” con distintos valores de Mdes y Ment entre 4 y 4096 y analizad el factor de compresión y el tiempo invertido para conseguirlo. ¿Cuál es el mejor factor de compresión que obtenéis y con qué valores de Mdes y Ment? ¿Cómo varia (cómo escala) el tiempo de cálculo necesario al aumentar Mdes y Ment? ¿Qué combinación de Mdes y Ment elegiríais?

El archivo hamlet como cadena de bits ocupa 8296.
La mejor compresión la alcanzamos con MDES 512 MENT 512 y es de un valor de 7364 y un tiempo de 18 ms. El factor de compresión que alcanzamos es de 1.12. Elegiria esta combinación de entrada y dezlizante ya que tarda menos de la media y conseguimos algo de compresión. El tiempo varia irregularmente en función de MDES y MENT. Cuando los tamaños de las ventanas son iguales el algoritmo tarda mucho más que la media, al igual que cuando hay muchisima diferencia entre MDES y MENT, conforme estos valores se van igualando el tiempo se reduce drásticamente. Valores muy pequeños también perjudican el tiempo de ejecución, hasta llegar a un MDES de aproximadamente 64. El mejor tiempo conseguido es de 13 ms con MDES 1024 y MENT 256, y su compresión es de 9977.

2-Comprimid ahora el archivo “quijote_short.txt” y analizad para qué combinación de Mdes y Ment se obtiene el mejor factor de compresión. ¿Es el mismo que en el caso anterior? Proponed varias razones que expliquen esta diferencia.

El tamaño de cadena de bits del texto del quijote es de 8176. Los mejores resultados obtenidos son los siguientes:
Con MDES 4096 y MENT 16 conseguimos una compresión de 8897 en un tiempo de 22.
Con MDES 1024 y MENT 32 conseguimos una compresion de 8457 en un tiempo de 29.
Con MDES 2048 y MENT 32 conseguimos una compresión de 7883, en un tiempo de 24.
Con MDES 4096 y MENT 32 conseguimos una compresion de 7746 en un tiempo de 37.
No es el mismo caso que en el anterior ejercicio, ya que comprobamos que cuanta más diferencia hay entre MDES y MENT mejores resultados obtenemos. En el caso contrario hemos obtenido un resultado muy bueno con valores iguales de MENT y MDES.
La diferencia es la estructura, en el primer texto encuentra más coincidencias en pequeños tramos ya que son frases cortas, los nombres se van repitiendo a lo largo de todo el texto, entonces una ventana pequeña se adapta mejor a la estructura. En cambio, para el texto del quijote que es un texto grande sin gran repetición de ninguna frase ni caracter, al compresor le va mejor con valores pequeños de MENT y valores grandes de MDES.
