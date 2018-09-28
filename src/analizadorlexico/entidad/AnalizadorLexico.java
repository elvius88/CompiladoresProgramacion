package analizadorlexico.entidad;

/**
 *
 * @author elvioc
 */
public class AnalizadorLexico {
     private int numeroLinea;
     private int numeroColumna;

    public AnalizadorLexico() {
        numeroLinea = 0;
        numeroColumna = 0;
    }
    
    public void error(String mensajeError){
        System.out.printf("Linea %d: Error Léxico. %s.\n", getNumeroLinea(), mensajeError);
    }
    
    public void siguienteLexema(){
        int i = 0;
        char c = 0;
        int acepto = 0;
        int estado = 0;
        String mensaje = "";
        Entrada entrada;
        
        while (true) {            
            switch (c) {
                case ' ':
                case '\t':
                    System.out.print(c);//Si es espacio en blanco o tabulador lo imprime y sigue continua con el siguiente caracter
                    break;
                case '\n':
                    System.out.println();
                    numeroLinea++;//Si es el fin de la línea incrementa el número de línea, lo imprime y sigue continua con el siguiente caracter
                    break;
                case '"':
                    while (true) {                        
                        break;
                    }
                    break;
                case '{':
                    break;
                case '}':
                    break;
                case '[':
                    break;
                case ']':
                    break;
                case ',':
                    break;
                case ':':
                    break;
                default:
                    break;
            }
        }
    }
    
    public boolean isNumerico(String s){
        return s.matches("[0-9]");
    }
    
    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public int getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(int numeroColumna) {
        this.numeroColumna = numeroColumna;
    }
    
    public static void main(String[] args) {
        AnalizadorLexico al = new AnalizadorLexico();
        System.out.println(al.isNumerico("1"));
        System.out.println(al.isNumerico("2"));
        System.out.println(al.isNumerico("3"));
        System.out.println(al.isNumerico("4"));
        System.out.println(al.isNumerico("5"));
        System.out.println(al.isNumerico("6"));
        System.out.println(al.isNumerico("7"));
        System.out.println(al.isNumerico("8"));
        System.out.println(al.isNumerico("@"));
        System.out.println(al.isNumerico("9"));
        
    }
}
