package analizadorlexico.entidad;

import analizadorlexico.enums.TokenEnum;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author elvioc
 */
public class AnalizadorLexico {

    private int numeroLinea;
    private int numeroColumna;
    private String lexema;
    private String fuente;
    private Token token;
    FileReader fr;

    public AnalizadorLexico() {
        numeroLinea = 1;
        numeroColumna = 0;
        token = new Token();
    }

    public void error(String mensajeError) {
        System.err.printf("Linea %d columna %d: Error Léxico. %s.\n", getNumeroLinea(), getNumeroColumna(), mensajeError);
    }

    public void leerFuente() {
        //Declarar una variable FileReader
        fr = null;
        try {
            if (getFuente() != null) {
                //Abrir el fichero indicado en la variable nombreFichero
                fr = new FileReader(getFuente());
                //Leer el primer carácter
                //Se debe almacenar en una variable de tipo int
//                int caract = fr.read();
                //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
                while (token.getComponenteLexico() != -1) {
                    //Mostrar en pantalla el carácter leído convertido a char
                    siguienteLexema();
//                    System.out.print((char) caract);
                    //Leer el siguiente carácter
//                    caract = fr.read();
                }
            } else {
                System.err.println("Error. No se le pasó el fuente.");
            }
        } catch (FileNotFoundException e) {
            //En caso de no encontrar el fichero lanza la excepción
            //Mostrar el error producido por la excepción
            System.err.println("Error: Fichero no encontrado.");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            //Operaciones en caso de error general
            System.err.println("Error de lectura del fichero.");
            System.err.println(e.getMessage());
        } finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero.");
                System.err.println(e.getMessage());
            }
        }
    }

    public void siguienteLexema() throws IOException {
        int i = 0;
        char c = 0;
        int cInt = 0;
        int acepto = 0;
        int estado = 0;
        String mensaje = "";
        Entrada entrada;

        while ((cInt = fr.read()) != -1) {
            numeroColumna++;
            switch (c = (char) cInt) {
                case ' ':
                case '\t':
                    System.out.print(c);//Si es espacio en blanco o tabulador lo imprime y sigue continua con el siguiente caracter
                    break;
                case '\n':
                    System.out.println();
                    numeroColumna = 0;
                    numeroLinea++;//Si es el fin de la línea incrementa el número de línea, lo imprime y sigue continua con el siguiente caracter
                    break;
                case '"':
                    System.out.print(TokenEnum.STRING.getNombreToken());
                    while (true) {
                        break;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    System.out.print(TokenEnum.NUM.getNombreToken());
                    while (true) {
                        break;
                    }
                    break;
                case '{':
                    System.out.print(TokenEnum.LLAVE_IZQ.getNombreToken());
                    break;
                case '}':
                    System.out.print(TokenEnum.LLAVE_DER.getNombreToken());
                    break;
                case '[':
                    System.out.print(TokenEnum.CORCHETE_IZQ.getNombreToken());
                    break;
                case ']':
                    System.out.print(TokenEnum.CORCHETE_DER.getNombreToken());
                    break;
                case ',':
                    System.out.print(TokenEnum.COMA.getNombreToken());
                    break;
                case ':':
                    System.out.print(TokenEnum.DOS_PUNTOS.getNombreToken());
                    break;
                default:
                    error(c + " no esperado");
                    break;
            }
        }
    }

    private boolean isNumerico(String s) {
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

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
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
