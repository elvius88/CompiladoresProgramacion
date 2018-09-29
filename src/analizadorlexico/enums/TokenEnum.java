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
    COMA(260,"COMA"),
    DOS_PUNTOS(261,"DOS_PUNTOS"),
    PR_BOOLEANO_FALSE(262,"PR_BOOLEANO_FALSE"),
    PR_BOOLEANO_TRUE(263,"PR_BOOLEANO_TRUE"),
    STRING(264,"STRING"),
    NUM(265,"NUMERO");
    
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
