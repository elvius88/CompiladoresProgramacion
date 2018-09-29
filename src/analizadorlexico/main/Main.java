package analizadorlexico.main;

import analizadorlexico.entidad.AnalizadorLexico;
import analizadorlexico.tabla.TablaSimbolo;

/**
 *
 * @author elvioc
 */
public class Main {

    public static void main(String[] args) {
        TablaSimbolo tablaSimbolo = new TablaSimbolo();
	tablaSimbolo.initTablaSimbolos();
        
        AnalizadorLexico lexer = new AnalizadorLexico();
        lexer.setFuente("C:\\Users\\pc\\Documents\\Facultad\\Compiladores\\tp1 - analizador léxico\\fuente.txt");
        lexer.leerFuente();
        System.exit(0);
    }
}
