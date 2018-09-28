package analizadorlexico.tabla;

import analizadorlexico.entidad.Entrada;
import java.util.HashMap;

/**
 *
 * @author elvioc
 */
public class TablaSimbolo implements TablaSimboloInterface {

    private HashMap<Integer, Entrada> tabla; 

    public TablaSimbolo() {
        tabla = new HashMap<>();
    }

    public HashMap<Integer, Entrada> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<Integer, Entrada> tabla) {
        this.tabla = tabla;
    }
    
    /**
     * Insertar una entrada en la tabla
     * 
     * @param e 
     */
    @Override
    public void insertar(Entrada e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     */
    @Override
    public void initTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     */
    @Override
    public void initTablaSimbolos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
