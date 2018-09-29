package analizadorlexico.enums;

/**
 *
 * @author elvioc
 */
public enum TokenEnum {
    LLAVE_IZQ('{', "LLAVE_IZQUIERDA"),
    LLAVE_DER('}', "LLAVE_DERECHA"),
    CORCHETE_IZQ('[', "CORCHETE_IZQUIERDA"),
    CORCHETE_DER(']', "CORCHETE_DERECHA"),
    COMA(',', "COMA"),
    DOS_PUNTOS(':', "DOS_PUNTOS"),
    PR_BOOLEANO_FALSE(262, "PR_BOOLEANO_FALSE"),
    PR_BOOLEANO_TRUE(263, "PR_BOOLEANO_TRUE"),
    STRING(264, "STRING"),
    NUM(265, "NUMERO");

    private final int id;
    private final String nombreToken;

    private TokenEnum(int id, String nombreToken) {
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
