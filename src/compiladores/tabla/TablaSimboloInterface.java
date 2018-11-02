package compiladores.tabla;

import compiladores.analizadorlexico.lexer.Entrada;

/**
 *
 * @author elvioc
 */
public interface TablaSimboloInterface {

    public void insertar(Entrada e);

    public Entrada buscar(String key);

    public void insertarTablaSimbolos(String s, int compLex);

    public void initTablaSimbolos();
}
