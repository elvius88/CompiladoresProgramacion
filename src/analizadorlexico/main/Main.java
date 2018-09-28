package analizadorlexico.main;

import analizadorlexico.tabla.TablaSimbolo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author elvioc
 */
public class Main {

    public static void main(String[] args) {
        TablaSimbolo tablaSimbolo = new TablaSimbolo();
        tablaSimbolo.initTabla();
	tablaSimbolo.initTablaSimbolos();
        
        
        String nombreFichero = "/home/elvioc/Descargas/fuente.txt";
        //Declarar una variable FileReader
        FileReader fr = null;
        try {
            //Abrir el fichero indicado en la variable nombreFichero
            fr = new FileReader(nombreFichero);
            //Leer el primer carácter
            //Se debe almacenar en una variable de tipo int
            int caract = fr.read();
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caract != -1) {
                //Mostrar en pantalla el carácter leído convertido a char
                System.out.print((char) caract);
                //Leer el siguiente carácter
                caract = fr.read();
            }
        } catch (FileNotFoundException e) {
            //En caso de no encontrar el fichero lanza la excepción
            //Mostrar el error producido por la excepción
            System.err.println("Error: Fichero no encontrado.");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            //Operaciones en caso de error general
            System.err.println("Error de lectura del fichero.");
            System.err.println(e.getMessage());
        } finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero.");
                System.err.println(e.getMessage());
            }
        }
    }
}
