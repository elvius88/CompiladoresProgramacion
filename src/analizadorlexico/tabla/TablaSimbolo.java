package analizadorlexico.tabla;

import analizadorlexico.entidad.Entrada;
import analizadorlexico.enums.TokenEnum;
import java.util.HashMap;

/**
 *
 * @author elvioc
 */
public class TablaSimbolo implements TablaSimboloInterface {

    private HashMap<String, Entrada> tabla; 

    public TablaSimbolo() {
        tabla = new HashMap<>();
    }

    public HashMap<String, Entrada> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<String, Entrada> tabla) {
        this.tabla = tabla;
    }
    
    /**
     * Insertar una entrada en la tabla
     * 
     * @param e 
     */
    @Override
    public void insertar(Entrada e) {
        tabla.put(e.getLexema(), e);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Entrada buscar(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param s
     * @param compLex
     */
    @Override
    public void insertarTablaSimbolos(String s, int compLex) {
        Entrada e = new Entrada();
        e.setComponenteLexico(compLex);
        e.setLexema(s);
        insertar(e);
    }

    /**
     * 
     */
    @Override
    public void initTablaSimbolos() {
        insertarTablaSimbolos(TokenEnum.LLAVE_IZQ.getNombreToken(), TokenEnum.LLAVE_IZQ.getId());
        insertarTablaSimbolos(TokenEnum.LLAVE_DER.getNombreToken(), TokenEnum.LLAVE_DER.getId());
        insertarTablaSimbolos(TokenEnum.CORCHETE_IZQ.getNombreToken(), TokenEnum.CORCHETE_IZQ.getId());
        insertarTablaSimbolos(TokenEnum.CORCHETE_DER.getNombreToken(), TokenEnum.CORCHETE_DER.getId());
        insertarTablaSimbolos(TokenEnum.COMA.getNombreToken(), TokenEnum.COMA.getId());
        insertarTablaSimbolos(TokenEnum.DOS_PUNTOS.getNombreToken(), TokenEnum.DOS_PUNTOS.getId());
        insertarTablaSimbolos(TokenEnum.PR_BOOLEANO_FALSE.getNombreToken(), TokenEnum.PR_BOOLEANO_FALSE.getId());
        insertarTablaSimbolos(TokenEnum.PR_BOOLEANO_TRUE.getNombreToken(), TokenEnum.PR_BOOLEANO_TRUE.getId());
    }
    
}
