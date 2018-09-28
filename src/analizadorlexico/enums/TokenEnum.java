package analizadorlexico.enums;

/**
 *
 * @author elvioc
 */
public enum TokenEnum {
    LLAVE_IZQ(256,"LLAVE_IZQUIERDA"),
    LLAVE_DER(257,"LLAVE_DERECHA"),
    CORCHETE_IZQ(258,"CORCHETE_IZQUIERDA"),
    CORCHETE_DER(259,"CORCHETE_DERECHA"),
    NUM(260,"NUMERO"),
    COMA(261,"COMA"),
    DOS_PUNTOS(262,"DOS_PUNTOS"),
    STRING(263,"STRING"),
    PR_BOOLEANO(264,"PR_BOOLEANO");
    
    private final int id;
    private final String nombreToken;
    
    private TokenEnum(int id, String nombreToken){
        this.id = id;
        this.nombreToken = nombreToken;
    }

    public int getId() {
        return id;
    }

    public String getNombreToken() {
        return nombreToken;
    }
    
    
}
