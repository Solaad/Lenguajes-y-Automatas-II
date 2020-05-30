/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import Cuadruplos.Arbol;
import java.util.ArrayList;
import java.util.Stack;
import javafx.beans.binding.Bindings;

/**
 *
 * @author Alberto Loera
 */
public class SemanticAnalizer {

    Arbol arbolito = new Arbol();
    ArrayList<Componente> tokens, expresion;
    ArrayList<Simbolos> simbolos;
    Simbolos aux;
    String tipo, identificador, valor, salida, operador, cuadruplo, postOrden;
    int posicion, indice, tamaño;
    Componente componente;

    boolean error = true;

    public SemanticAnalizer(ArrayList<Componente> tokens) {
        this.tokens = tokens;

        componente = tokens.get(indice);
        simbolos = new ArrayList<Simbolos>();
        expresion = new ArrayList<Componente>();
        salida = "";
        simbolos.clear();
    }

    public void semanticEngine() {
        do {

            if (componente.getTipo() == Componente.PALABRA_RESERVADA && componente.getToken().equals("class")) {
                tipo = "clase";
                nexToken();
                valor = "-";
                posicion = componente.getFila();
                identificador = componente.getToken();
                error = false;
                tamaño = 1;
                addSymbol();
            } else if (componente.getTipo() == Componente.TIPO) {
                valor = "0";
                tipo = componente.getToken();
                nexToken();
                posicion = componente.getFila();
                identificador = componente.getToken();
                if (!matchSearch(identificador)) {
                    error = false;
                    nexToken();
                    if (componente.getToken().equals("=")) {
                        nexToken();
                        if (tipo.equals("int")) {
                            intCheck();
                            
                        } else if (tipo.equals("boolean")) {
                            booleanCheck();
                        } else if (tipo.equals("String")) {
                            stringCheck();
                        }
                    }
                    addSymbol();
                    valor = "";
                } else {
                    error = true;
                    salida += ("\tError semantico en la linea " + posicion + " en el token \""
                            + componente.getToken() + "\" la variable ya esta declarada anteriormente en la linea " + aux.getFila() + "\n");
                }
            } else if (componente.getTipo() == Componente.IDENTIFICADOR) {
                posicion = componente.getFila();
                if (matchSearch(componente.getToken())) {
                    if (aux.getType().equals("int")) {
                        identificador = componente.getToken();
                        nexToken();
                        if (componente.getToken().equals("=")) {
                            nexToken();
                            while (!componente.getToken().equals(";")) 
                            {
                                if(componente.getTipo() == Componente.IDENTIFICADOR)
                                {     
                                    if(matchSearch(componente.getToken()))
                                        arbolito.añadirNodo(new Componente(componente.getTipo(),aux.getValor(),componente.getColumna(), componente.getFila()));
                                    else
                                    {
                                        error = true;
                                    salida = "Ew No puedes hacer esto";
                                            }
                                }
                                else
                                {
                                     arbolito.añadirNodo(componente);
                                }
                                
                               
                                nexToken();
                            }
                            arbolito.recorrePosOrden(arbolito.raiz);
                            System.out.println("Recorrido en POST-ORDEN :" + arbolito.getPost());
                            arbolito.generaCuadruplo(arbolito.raiz);
                            cuadruplo = (arbolito.getCuadruplo());
                            arbolito.resuelve(arbolito.raiz);
                            String cuad = arbolito.getResultado();
                            matchSearch(identificador, arbolito.getResultado());
                        }
                    } else {;
                        salida += "\tError semantico, linea " + posicion + " Solo se pueden hacer operaciones y/0 condiciones con enteros\n";
                    }
                } else {
                    error = true;
                    salida += ("\tError semantico en la linea " + posicion + " en el token \""
                            + componente.getToken() + "\" la variable no esta declarada anteriormente\n");
                }
            }
            error = true;
            valor = "";
            nexToken();
        } while (componente != null);
    }

    public void nexToken() {
        try {
            indice++;
            componente = tokens.get(indice);
        } catch (IndexOutOfBoundsException e) {
            componente = null;
        }
    }

    public String getSalida() {
        return salida;
    }

    public boolean matchSearch(String identificador) {
        return matchSearch(identificador, null);
    }

    public boolean matchSearch(String identificador, String valorNuevo) {
        boolean bandera = false;
        for (Simbolos c : simbolos) {
            if (c.getIdentificador().equals(identificador)) {
                bandera = true;
                aux = c;
                if (valorNuevo != null) {
                    c.setValor(valorNuevo);
                }
                break;
            }
        }
        return bandera;
    }

    public void addSymbol() {
        if (error == false) {
            simbolos.add(new Simbolos(tipo, identificador, tamaño, valor, posicion));
        }
    }

    public ArrayList getSymbolTable() {
        return simbolos;
    }

    public void intCheck() {
        try {
            int val = Integer.parseInt(componente.getToken());
            valor = String.valueOf(val);
            
            tamaño = 1;
            matchSearch(identificador, valor);
            error = false;

        } catch (NumberFormatException e) {
            salida += ("\tError Semantico identificador " + identificador + " en la linea " + posicion + " en el token " + componente.getToken()
                    + " Se esperaba un valor entero\n");
        }
    }

    public void booleanCheck() {
        if (componente.getToken().matches("(true|false)")) {
            valor = componente.getToken();
            tamaño = 1;
        } else {
            salida
                    += "\tError Semantico en la linea, linea: " + posicion
                    + " en el token:" + componente.getToken() + " Se esperba un dato boolean\n";
        }
    }

    public void stringCheck() {
        if (componente.getToken().charAt(0) == '\"' && componente.getToken().charAt(componente.getToken().length() - 1) == '\"') {
            valor = componente.getToken();
            tamaño = componente.getToken().length() - 2;
            error = false;
        } else {
            salida
                    += "\tError Semantico en la linea, linea: " + posicion
                    + " en el token: " + componente.getToken() + " Se esperaba el token \"\n";
        }
    }

    public String identChek() throws NumberFormatException {
        String val = "";
        if (matchSearch(componente.getToken())) {
            posicion = componente.getFila();
            if (aux.getType().equals("int")) {
                val = aux.getValor();
            } else {
                salida += "\tError Semantico en la posicion " + posicion + " No se puede hacer operaciones con valores diferente de entero\n";
            }
        } else {
            salida += ("\tError semantico en la linea " + posicion + " en el token \""
                    + componente.getToken() + "\" la variable no esta declarada anteriormente\n");
        }
        return val;
    }

    public ArrayList<Componente> getExpresion() {
        return expresion;
    }
    public String getCua()
    {
        return cuadruplo;
    }
    public ArrayList getListCua()
    {
        return arbolito.getArreCuad();
    }
            
}
