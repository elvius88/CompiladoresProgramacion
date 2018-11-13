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
        if (token.getComponenteLexico() == '{' || token.getComponenteLexico() == '[') {
            element();
        } else {
            error("'{' o '['");
        }
    }

    public void element() {
        syncToken = new int[]{']', '}', ','};
        switch (token.getPunteroEntrada().getLexema()) {
            case "[":
                array();
                break;
            case "{":
                object();
                break;
        }
    }

    public void array() {
        match("[");
        arrayPrima();
    }

    public void arrayPrima() {
        if (!"]".equals(token.getPunteroEntrada().getLexema())) {
            elementList();
        }
        match("]");
    }

    public void object() {
        match("{");
        objectPrima();
    }

    public void objectPrima() {
        if (!"}".equals(token.getPunteroEntrada().getLexema())) {
            attributeList();
        }
        match("}");
    }

    public void elementList() {
        element();
        elementListPrima();
    }

    public void elementListPrima() {
        syncToken = new int[]{']'};
        if (',' == token.getComponenteLexico() && arrayTokens.get(posicion - 1).getComponenteLexico() != '[') {
            match(",");
            element();
            elementListPrima();
        }
    }

    public void attributeList() {
        attribute();
        attributeListPrima();
    }

    public void attributeListPrima() {
        syncToken = new int[]{'}'};
        if (',' == token.getComponenteLexico()) {
            match(",");
            attribute();
            attributeListPrima();
        }
    }

    public void attribute() {
        syncToken = new int[]{',','}', TokenEnum.STRING.getId()};
        if (token.getComponenteLexico() != TokenEnum.COMA.getId()) {
            attributeName();
            match(":");
            attributeValue();
        }else{
            error(TokenEnum.STRING.getNombreToken());
        }
    }

    public void attributeName() {
        if (token.getPunteroEntrada().getComponenteLexico() == TokenEnum.STRING.getId() && token.getComponenteLexico() != TokenEnum.COMA.getId()) {
            match(token.getPunteroEntrada().getLexema());
        } else {
            error(TokenEnum.STRING.getNombreToken());
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
        } else if ('{' == token.getComponenteLexico() || '[' == token.getComponenteLexico()) {
            element();
        } else {
            error(TokenEnum.PR_BOOLEANO_FALSE.getNombreToken() + ", " + TokenEnum.PR_BOOLEANO_TRUE.getNombreToken() + ", "
                    + TokenEnum.PR_NULL.getNombreToken() + ", " + TokenEnum.NUM.getNombreToken() + ", " + TokenEnum.STRING.getNombreToken() + ", ELEMENT");
        }
    }

    public void match(String expToken) {
        if (expToken.equals(token.getPunteroEntrada().getLexema())) {
            getToken();
        } else {
            error(expToken);
        }
    }

    public void getToken() {
        posicion++;
        if (posicion < arrayTokens.size()) {
            token = arrayTokens.get(posicion);
        }
    }

    public void error(String expectedToken) {
        this.error = true;
        System.err.printf("Error sintáctico. Se esperaba el token \'%s\', vino el el token \'%s\'.\n", expectedToken, token.getPunteroEntrada().getLexema());
        scan();
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
