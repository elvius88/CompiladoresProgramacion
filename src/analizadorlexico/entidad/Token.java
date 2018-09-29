package analizadorlexico.entidad;

/**
 *
 * @author elvioc
 */
public class Token {

    private int componenteLexico;
    private Entrada punteroEntrada;

    public Token() {
    
    }

    public int getComponenteLexico() {
        return componenteLexico;
    }

    public void setComponenteLexico(int componenteLexico) {
        this.componenteLexico = componenteLexico;
    }

    public Entrada getPunteroEntrada() {
        return punteroEntrada;
    }

    public void setPunteroEntrada(Entrada punteroEntrada) {
        this.punteroEntrada = punteroEntrada;
    }
    
}
