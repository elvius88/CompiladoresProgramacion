package compiladores.main;

import compiladores.analizadorlexico.lexer.AnalizadorLexico;
import compiladores.analizadorlexico.lexer.Entrada;
import compiladores.analizadorlexico.lexer.Token;
import compiladores.analizadorsintactico.parser.AnalizadorSintactico;
import compiladores.tabla.TablaSimbolo;
import java.util.Map;

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
        AnalizadorLexico lexer = new AnalizadorLexico(tablaSimbolo);
        //Se le pasa el fuente a leer
        lexer.setFuente("fuente.txt");
        //Se inicia la lectura del fuente
        lexer.leerFuente();
//        for (Token token : lexer.getArrayTokens()) {
//            System.out.println(token);
//        }
//        for (Map.Entry<String, Entrada> en : tablaSimbolo.getTabla().entrySet()) {
//            String key = en.getKey();
//            Entrada value = en.getValue();
//            System.out.println("KEY: " + key + ", VALUE: " + value);
//            
//        }
        AnalizadorSintactico parser = new AnalizadorSintactico(tablaSimbolo, lexer);
        if (!lexer.isError()){
            System.out.println("\033[32m1) Análisis léxico sin errores.\033[30m");
//            parser = new AnalizadorSintactico(tablaSimbolo, lexer);
            parser.init();
        }
        if (!parser.isError()){
            System.out.println("\033[32m2) Análisis sintáctico sin errores.\033[30m");
        }
        //Termina de ejecutarse el programa
        System.exit(0);
    }
}
