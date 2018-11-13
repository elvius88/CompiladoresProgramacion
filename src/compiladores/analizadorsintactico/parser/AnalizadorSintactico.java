package compiladores.analizadorsintactico.parser;

import compiladores.analizadorlexico.lexer.AnalizadorLexico;
import compiladores.analizadorlexico.lexer.Token;
import compiladores.enums.TokenEnum;
import compiladores.tabla.TablaSimbolo;
import java.util.ArrayList;

/**
 *
 * @author elvioc
 */
public class AnalizadorSintactico {

    private Token token;
    private AnalizadorLexico analizadorLexico;
    private TablaSimbolo tablaSimbolo;
    private ArrayList<Token> arrayTokens; //Arreglo de tokens
    private boolean error;
    private int posicion;
    private int[] syncToken;

    public AnalizadorSintactico(TablaSimbolo tablaSimbolo, AnalizadorLexico analizadorLexico) {
        this.tablaSimbolo = tablaSimbolo;
        this.analizadorLexico = analizadorLexico;
    }

    public void init() {
        this.arrayTokens = analizadorLexico.getArrayTokens();
        this.token = arrayTokens.get(0);
        this.posicion = 0;
        this.error = false;
        json();
    }

    public void json() {
        element();
    }

    public void element() {
        if (token.getComponenteLexico() == TokenEnum.CORCHETE_IZQ.getId()) {
            array();
        } else if (token.getComponenteLexico() == TokenEnum.LLAVE_IZQ.getId()) {
            object();
        } else {
            error("Error sintactico en el ELEMENT: Se esperaba " + TokenEnum.CORCHETE_IZQ.getNombreToken() + " o " 
                    + TokenEnum.LLAVE_IZQ.getNombreToken() + ", vino " + token.getPunteroEntrada().getLexema());
        }
    }

    public void array() {
        if (TokenEnum.CORCHETE_IZQ.getId() == token.getComponenteLexico()) {
            match("[");
            arrayPrima();
        } else {
            error("Error sintactico en el ARRAY: Se esperaba " + TokenEnum.CORCHETE_IZQ.getNombreToken() + ", vino " 
                    + token.getPunteroEntrada().getLexema());
        }
    }

    public void arrayPrima() {
        if (TokenEnum.CORCHETE_IZQ.getId() == token.getComponenteLexico() || TokenEnum.LLAVE_IZQ.getId() == token.getComponenteLexico()) {
            elementList();
            match("]");
        } else if(TokenEnum.CORCHETE_DER.getId() == token.getComponenteLexico()){
            match("]");
        } else {
            error("Error sintactico en el ARRAY_PRIMA: Se esperaba " + TokenEnum.CORCHETE_IZQ.getNombreToken() + " o " 
                    + TokenEnum.LLAVE_IZQ.getNombreToken() + ", vino " + token.getPunteroEntrada().getLexema());
        }
    }

    public void elementList() {
        element();
        elementListPrima();
    }

    public void elementListPrima() {
        if (TokenEnum.COMA.getId() == token.getComponenteLexico()) {
            match(",");
            element();
            elementListPrima();
        }
    }

    public void object() {
        if (TokenEnum.LLAVE_IZQ.getId() == token.getComponenteLexico()) {
            match("{");
            objectPrima();
        } else {
            error("Error sintactico en el OBJECT: Se esperaba " + TokenEnum.LLAVE_IZQ.getNombreToken() + ", vino " 
                    + token.getPunteroEntrada().getLexema());
        }
    }

    public void objectPrima() {
        if (TokenEnum.STRING.getId() == token.getComponenteLexico()) {
            attributeList();
            match("}");
        } else if(TokenEnum.LLAVE_DER.getId() == token.getComponenteLexico()){
            match("}");
        } else {
            error("Error sintactico en el ARRAY_PRIMA: Se esperaba " + TokenEnum.CORCHETE_IZQ.getNombreToken() + " o " 
                    + TokenEnum.LLAVE_IZQ.getNombreToken() + ", vino " + token.getPunteroEntrada().getLexema());
        }
    }

    public void attributeList() {
        attribute();
        attributeListPrima();
    }

    public void attributeListPrima() {
        syncToken = new int[]{'}'};
        if (TokenEnum.COMA.getId() == token.getComponenteLexico()) {
            match(",");
            attribute();
            attributeListPrima();
        }
    }

    public void attribute() {
        if (token.getComponenteLexico() == TokenEnum.STRING.getId()) {
            attributeName();
            match(":");
            attributeValue();
        } else {
            error("Error sintactico en el ATTRIBUTE: Se esperaba " 
                    + TokenEnum.STRING.getNombreToken() + ", vino '" + token.getPunteroEntrada().getLexema() +"'.");
        }
    }

    public void attributeName() {
        if (token.getPunteroEntrada().getComponenteLexico() == TokenEnum.STRING.getId() && token.getComponenteLexico() != TokenEnum.COMA.getId()) {
            match(token.getPunteroEntrada().getLexema());
        } else {
            error("Error sintactico en el ATTRIBUTE_NAME: Se esperaba " 
                    + TokenEnum.STRING.getNombreToken() + ", vino '" + token.getPunteroEntrada().getLexema() +"'.");
        }
    }

    public void attributeValue() {
        if (TokenEnum.PR_BOOLEANO_FALSE.getId() == token.getComponenteLexico()) {
            match(token.getPunteroEntrada().getLexema());
        } else if (TokenEnum.PR_BOOLEANO_TRUE.getId() == token.getComponenteLexico()) {
            match(token.getPunteroEntrada().getLexema());
        } else if (TokenEnum.PR_NULL.getId() == token.getComponenteLexico()) {
            match(token.getPunteroEntrada().getLexema());
        } else if (TokenEnum.NUM.getId() == token.getComponenteLexico()) {
            match(token.getPunteroEntrada().getLexema());
        } else if (TokenEnum.STRING.getId() == token.getComponenteLexico()) {
            match(token.getPunteroEntrada().getLexema());
        } else if (TokenEnum.LLAVE_IZQ.getId() == token.getComponenteLexico() || TokenEnum.CORCHETE_IZQ.getId() == token.getComponenteLexico()) {
            element();
        } else {
            error("Error sintactico en el ATTRIBUTE_VALUE: Se esperaba " 
                    + TokenEnum.PR_BOOLEANO_FALSE.getNombreToken() + ", " + TokenEnum.PR_BOOLEANO_TRUE.getNombreToken() + ", "
                    + TokenEnum.PR_NULL.getNombreToken() + ", " + TokenEnum.NUM.getNombreToken() + ", " + TokenEnum.STRING.getNombreToken() + "o ELEMENT, vino '"
                    + token.getPunteroEntrada().getLexema() + "'");
        }
    }

    public void match(String expToken) {
        if (expToken.equals(token.getPunteroEntrada().getLexema())) {
            getToken();
        } else {
            error("Error sintactico en el matching: Se esperaba '" 
                    + expToken + "', vino '" + token.getPunteroEntrada().getLexema() +"'.");
        }
    }

    public void getToken() {
        posicion++;
        if (posicion < arrayTokens.size()) {
            token = arrayTokens.get(posicion);
        }
    }

    public void error(String mensajeError) {
        this.error = true;
        System.err.println(mensajeError);
    }

    public void scan() {
        boolean sync = false;
        do {
            for (int i = 0; i < syncToken.length; i++) {
                if (token.getComponenteLexico() == syncToken[i]) {
                    sync = true;
                    break;
                }
            }
            getToken();
        } while (token.getComponenteLexico() != TokenEnum.EOF.getId() && !sync);
        if (token.getComponenteLexico() == TokenEnum.EOF.getId()) {
            System.err.println("Error inesperado. Se llegó al final del archivo.");
            System.exit(0);
        }
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
