package analizadorlexico.main;

import analizadorlexico.entidad.AnalizadorLexico;
import analizadorlexico.tabla.TablaSimbolo;

/**
 *
 * @author elvioc
 */
public class Main {

    /**
     * Método main del compilador
     * 
     * @param args 
     */
    public static void main(String[] args) {
        //Instancia la tabla de símbolos
        TablaSimbolo tablaSimbolo = new TablaSimbolo();
	//Inicializa la tabla de símbolos
        tablaSimbolo.initTablaSimbolos();
        //Inicializa el análisis sintáctico
        AnalizadorLexico lexer = new AnalizadorLexico();
        //Se le pasa el fuente a leer
        lexer.setFuente("fuente.txt");
        //Se inicia la lectura del fuente
        lexer.leerFuente();
        //Termina de ejecutarse el programa
        System.exit(0);
    }
}
