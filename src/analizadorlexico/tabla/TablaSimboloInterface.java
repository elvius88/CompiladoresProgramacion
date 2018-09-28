package analizadorlexico.tabla;

import analizadorlexico.entidad.Entrada;

/**
 *
 * @author elvioc
 */
public interface TablaSimboloInterface {

    public void insertar(Entrada e);

    public Entrada buscar(String key);

    public void initTabla();

    public void initTablaSimbolos();
}
