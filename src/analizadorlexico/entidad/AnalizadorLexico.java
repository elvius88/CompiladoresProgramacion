package analizadorlexico.entidad;

import analizadorlexico.enums.TokenEnum;
import analizadorlexico.tabla.TablaSimbolo;
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
    private TablaSimbolo tablaSimbolo;
    FileReader fr;

    public AnalizadorLexico() {
        numeroLinea = 1;
        numeroColumna = 0;
        token = new Token();
        tablaSimbolo = new TablaSimbolo();
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
                //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
                while (token.getComponenteLexico() != -1) {
                    //Mostrar en pantalla el carácter leído convertido a char
                    siguienteLexema();
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
        char c;
        int cInt = 0;
        lexema = "";
        Entrada entrada = new Entrada();
        cInt = fr.read();
        while (cInt != -1) {
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
                    lexema = "";
                    lexema += c;
                    do {
                        cInt = fr.read();
                        c = (char) cInt;
                        if (c == '"') {
                            cInt = fr.read();
                            c = (char) cInt;
                            if (c == '"') {
                                lexema += c;
                                lexema += c;
                            } else {
                                lexema += '"';
                                break;
                            }
                        } else if (cInt == -1) {
                            error("Se llegó al fin del archivo sin finalizar un literal.");
                        } else {
                            lexema += c;
                        }
                    } while (isLetra(cInt) || isNumerico(cInt));
                    lexema += '\0';
                    if (cInt != -1) {
                        c = 0;
                    }
                    token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                    if (token.getPunteroEntrada() == null) {
                        entrada.setLexema(lexema);
                        if (lexema.length() == 3 || lexema.equals("\"\"\"")) {

                        } else {
                            entrada.setComponenteLexico(TokenEnum.STRING.getId());
                        }
                        tablaSimbolo.insertar(entrada);
                        token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                        token.setComponenteLexico(entrada.getComponenteLexico());
                    }
                    System.out.print(TokenEnum.STRING.getNombreToken() + " ");

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
                    lexema = "";
                    do {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                    } while (isNumerico(cInt));

                    if (cInt != -1 && !isNumerico(cInt)) {
                        c = 0;
                    }
                    lexema += '\0';

                    token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                    if (token.getPunteroEntrada() == null) {
                        entrada.setLexema(lexema);
                        entrada.setComponenteLexico(TokenEnum.NUM.getId());

                        tablaSimbolo.insertar(entrada);
                        token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                    }
                    token.setComponenteLexico(TokenEnum.NUM.getId());
                    System.out.print(TokenEnum.NUM.getNombreToken() + " ");
                    break;
                case '{':
                    token.setComponenteLexico(TokenEnum.LLAVE_IZQ.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar("{"));
                    System.out.print(TokenEnum.LLAVE_IZQ.getNombreToken());
                    break;
                case '}':
                    token.setComponenteLexico(TokenEnum.LLAVE_DER.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar("}"));
                    System.out.print(TokenEnum.LLAVE_DER.getNombreToken() + " ");
                    break;
                case '[':
                    token.setComponenteLexico(TokenEnum.CORCHETE_IZQ.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar("["));
                    System.out.print(TokenEnum.CORCHETE_IZQ.getNombreToken());
                    break;
                case ']':
                    token.setComponenteLexico(TokenEnum.CORCHETE_DER.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar("]"));
                    System.out.print(TokenEnum.CORCHETE_DER.getNombreToken() + " ");
                    break;
                case ',':
                    token.setComponenteLexico(TokenEnum.COMA.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar(","));
                    System.out.print(TokenEnum.COMA.getNombreToken() + " ");
                    break;
                case ':':
                    token.setComponenteLexico(TokenEnum.DOS_PUNTOS.getId());
                    token.setPunteroEntrada(tablaSimbolo.buscar(":"));
                    System.out.print(TokenEnum.DOS_PUNTOS.getNombreToken() + " ");
                    break;
                case 't':
                    lexema = "";
                    int id = 0;
                    while (id < 4) {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                        id++;
                    }
                    if (cInt != -1) {
                        c = 0;
                    }
                    if ("true".equals(lexema)) {
                        token.setComponenteLexico(TokenEnum.PR_BOOLEANO_TRUE.getId());
                        token.setPunteroEntrada(tablaSimbolo.buscar("true"));
                        System.out.print(TokenEnum.PR_BOOLEANO_TRUE.getNombreToken() + " ");

                    } else {
                        error("no es valor booleano.");
                    }
                    break;
                case 'f':
                    lexema = "";
                    int id_ = 0;
                    while (id_ < 5) {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                        id_++;
                    }
                    if (cInt != -1) {
                        c = 0;
                    }
                    if ("false".equals(lexema)) {
                        token.setComponenteLexico(TokenEnum.PR_BOOLEANO_FALSE.getId());
                        token.setPunteroEntrada(tablaSimbolo.buscar("false"));
                        System.out.print(TokenEnum.PR_BOOLEANO_FALSE.getNombreToken() + " ");

                    } else {
                        error("no es valor booleano.");
                    }
                    break;
                default:
                    error(c + " no esperado");
                    break;
            }
            if (c != 0) {
                cInt = fr.read();
            }
        }

        if (cInt == -1) {
            token.setComponenteLexico(-1);
            token.setPunteroEntrada(entrada);
        }
    }

    private boolean isNumerico(int cInt) {
        String s = "";
        char c = (char) cInt;
        s += c;
        return s.matches("[0-9]");
    }

    private boolean isLetra(int cInt) {
        String s = "";
        char c = (char) cInt;
        s += c;
        return s.matches("[a-zA-Z]|[áéíóúÁÉÍÓÚ ]");
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
}
