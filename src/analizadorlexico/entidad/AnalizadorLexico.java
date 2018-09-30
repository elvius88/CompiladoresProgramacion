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
        char c;
        lexema = "";
        Entrada entrada = new Entrada();
        int cInt = fr.read();
        boolean acepto = false;
        int estado = 0;
        int index;

        while (cInt != -1) {
            numeroColumna++;
            switch (c = (char) cInt) {
                case ' ':
                    numeroColumna++;
                    System.out.print(c);//Si es espacio en blanco o tabulador lo imprime y sigue continua con el siguiente caracter
                    break;
                case '\t':
                    numeroColumna++;
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
                            error("Se llegó al fin del archivo sin finalizar un literal");
                        } else {
                            lexema += c;
                        }
                    } while (isLiteralCadena(cInt));
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
                    boolean err = false;
                    estado = 0;
                    acepto = false;
                    lexema = "";
                    while (!acepto) {
                        switch (estado) {
                            case 0://Una secuencia netamente de dígitos, puede ocurrir . o e
                                cInt = fr.read();
                                c = (char) cInt;
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 0;
                                } else if (c == '.') {
                                    lexema += c;
                                    estado = 1;
                                } else if ("e".equals(String.valueOf(c))) {
                                    lexema += c;
                                    estado = 3;
                                } else {
                                    estado = 6;
                                }
                                break;
                            case 1://Un punto, debe seguir un dígito
                                cInt = fr.read();
                                c = (char) cInt;
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 2;
                                } else {
                                    error("No se esperaba \'" + c + "\'");
                                    estado = -1;
                                }
                                break;
                            case 2://La fraccion decimal, pueden seguir los digitos o e
                                cInt = fr.read();
                                c = (char) cInt;
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 2;
                                } else if ("e".equals(String.valueOf(c).toLowerCase())) {
                                    lexema += c;
                                    estado = 3;
                                } else {
                                    estado = 6;
                                }
                                break;
                            case 3: //Una e, puede seguir +, - o una secuencia de digitos
                                cInt = fr.read();
                                c = (char) cInt;
                                if (c == '+' || c == '-') {
                                    lexema += c;
                                    estado = 4;
                                }
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 5;
                                } else {
                                    error("No se esperaba '" + c + "'");
                                    estado = -1;
                                }
                                break;
                            case 4: //Necesariamente debe venir por lo menos un digito
                                cInt = fr.read();
                                c = (char) cInt;
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 5;
                                } else {
                                    error("No se esperaba '" + c + "'");
                                    estado = -1;
                                }
                                break;
                            case 5: //Secuencia de digitos correspondiente al exponente
                                cInt = fr.read();
                                c = (char) cInt;
                                if (isNumerico(c)) {
                                    lexema += c;
                                    estado = 5;
                                } else {
                                    estado = 6;
                                }
                                break;
                            case 6:
                                if (cInt != 1) {
                                    c = 0;
                                }
                                lexema += c;
                                acepto = true;
                                token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                                if (token.getPunteroEntrada() == null) {
                                    entrada.setLexema(lexema);
                                    entrada.setComponenteLexico(TokenEnum.NUM.getId());
                                    tablaSimbolo.insertar(entrada);
                                    token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                                }
                                token.setComponenteLexico(TokenEnum.NUM.getId());
                                break;
                            case -1:
                                if (cInt == -1) {
                                    error("No se esperaba el fin del archivo");
                                }
                                acepto = true;
                                err = true;
                                break;
                        }
                    }

                    if (cInt != -1 && !isNumerico(cInt)) {
                        c = 0;
                    }
                    lexema += '\0';
                    if (!err) {
                        token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                        if (token.getPunteroEntrada() == null) {
                            entrada.setLexema(lexema);
                            entrada.setComponenteLexico(TokenEnum.NUM.getId());

                            tablaSimbolo.insertar(entrada);
                            token.setPunteroEntrada(tablaSimbolo.buscar(lexema));
                        }
                        token.setComponenteLexico(TokenEnum.NUM.getId());
                        System.out.print(TokenEnum.NUM.getNombreToken() + " ");
                    }

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
                case 'T':
                    lexema = "";
                    index = 0;
                    while (index < 4) {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                        index++;
                    }
                    if (cInt != -1) {
                        c = 0;
                    }
                    if ("true".equals(lexema) || "TRUE".equals(lexema)) {
                        token.setComponenteLexico(TokenEnum.PR_BOOLEANO_TRUE.getId());
                        token.setPunteroEntrada(tablaSimbolo.buscar("true"));
                        System.out.print(TokenEnum.PR_BOOLEANO_TRUE.getNombreToken() + " ");

                    } else {
                        error("no es valor booleano válido");
                    }
                    break;
                case 'f':
                case 'F':
                    lexema = "";
                    index = 0;
                    while (index < 5) {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                        index++;
                    }
                    if (cInt != -1) {
                        c = 0;
                    }
                    if ("false".equals(lexema) || "FALSE".equals(lexema)) {
                        token.setComponenteLexico(TokenEnum.PR_BOOLEANO_FALSE.getId());
                        token.setPunteroEntrada(tablaSimbolo.buscar("false"));
                        System.out.print(TokenEnum.PR_BOOLEANO_FALSE.getNombreToken() + " ");

                    } else {
                        error("no es valor booleano válido");
                    }
                    break;
                case 'n':
                case 'N':
                    lexema = "";
                    index = 0;
                    while (index < 4) {
                        lexema += c;
                        cInt = fr.read();
                        c = (char) cInt;
                        index++;
                    }
                    if (cInt != -1) {
                        c = 0;
                    }
                    if ("null".equals(lexema) || "NULL".equals(lexema)) {
                        token.setComponenteLexico(TokenEnum.PR_BOOLEANO_FALSE.getId());
                        token.setPunteroEntrada(tablaSimbolo.buscar("null"));
                        System.out.print(TokenEnum.PR_NULL.getNombreToken() + " ");

                    } else {
                        error("no es valor nulo válido");
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
            token.setComponenteLexico(TokenEnum.EOF.getId());
            token.setPunteroEntrada(entrada);
        }
    }

    private boolean isNumerico(int cInt) {
        String s = "";
        char c = (char) cInt;
        s += c;
        return s.matches("[0-9]");
    }

    private boolean isLiteralCadena(int cInt) {
        String s = "";
        char c = (char) cInt;
        s += c;
        return s.matches(".*");
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
