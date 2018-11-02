package compiladores.analizadorsintactico.parser;

import compiladores.analizadorlexico.lexer.Token;
import compiladores.enums.TokenEnum;

/**
 *
 * @author elvioc
 */
public class AnalizadorSintactico {
    
    private Token token;
    
    public void json(){
        element();
    }
    
    public void element(){
        switch(token.getPunteroEntrada().getLexema()){
            case "[":
                array();
            case "{":
                object();
        }
    }
    public void array(){
        if ('[' == token.getComponenteLexico()) {
            match("[");
            arrayPrima();
        } else {
            error();
        }
    }
    public void arrayPrima(){
        if (null == token.getPunteroEntrada().getLexema()) {
            error();
        } else switch (token.getPunteroEntrada().getLexema()) {
            case "[":
            case "{":
                elementList();
                break;
            case "]":
                match("]");
                break;
            default:
                error();
                break;
        }
    }
    public void object(){
        if ('{' == token.getComponenteLexico()) {
            match("{");
            objectPrima();
        } else {
            error();
        }
    }
    public void objectPrima(){
        attributeList();
        match("}");
    }
    public void elementList(){
        element();
        elementListPrima();
    }
    public void elementListPrima(){
        if (',' == token.getComponenteLexico()) {
            match(",");
            element();
            elementListPrima();
        }
    }
    public void attributeList(){
        attribute();
        attributeListPrima();
    }
    public void attributeListPrima(){
        if (',' == token.getComponenteLexico()) {
            match(",");
            attribute();
            attributeListPrima();
        }
    }
    public void attribute(){
        attributeName();
        dosPuntosLexema();
        attributeValue();
    }
    public void attributeName(){
        if (token.getPunteroEntrada().getLexema().matches("*")) {
            match(token.getPunteroEntrada().getLexema());
        }else{
            error();
        }
    }
    public void attributeValue(){
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
            error();
        }
    }
    public void dosPuntosLexema(){
        if (':' == token.getComponenteLexico()) {
            match(":");
        }else {
            error();
        }
    }
    public void match(String expToken){
        if (expToken.equals(token.getPunteroEntrada().getLexema())) {
            getToken();
        }else{
            error();
        }
    }
    public void getToken(){}
    public void error(){}
    
}
