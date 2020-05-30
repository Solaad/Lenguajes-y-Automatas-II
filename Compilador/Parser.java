/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Alberto Loera
 */
public class Parser {

    ArrayList<Componente> componentes;
    
    Componente componente, auxDer, auxIzq;
    
    String salida = "";
    public static String salida2 = "";
    private int idx = 0,fila = 0;
    
    public Stack<Integer> pila;
    boolean error;

    public Parser() {
        componentes = new ArrayList<Componente>();
        
        
    }

    private void Acomodar(int tipo, String s) {
        if (componente.getTipo() == tipo && componente.getToken().equals(s)) {
            Avanza();
        } else {
            error(tipo, s);
        }
    }

    private void Avanza() {
        salida2 += "Token obtenido:" + componente.getToken() + "\n" + "Token Esperado: " + componente.getToken() + "\n-------------------------------------------\n";
        if (idx < componentes.size() - 1) {
            idx++;
        }
        try {

            componente = componentes.get(idx);

        } catch (IndexOutOfBoundsException e) {
            idx--;

            Componente caux = componentes.get(idx);
            componente = new Componente(19, "", caux.getColumna(), caux.getFila());
            //error(tipo,s);
        }
    }

    private void aritmetic_expression() {

        Componente c;
        c = componente;
      
        identificador();

        Acomodar(Componente.SIMBOLO_ESPECIAL, "=");
        if(componente.getTipo() == Componente.DIGITO)
        {
         integer_literal();
        }
        else if(componente.getTipo() == Componente.IDENTIFICADOR)
        {
            identificador();
        }
       
       
        c = componente;
       while(!c.getToken().equals(";"))
       {
        if (c.getToken().matches("(\\+|-|/|\\*)")) {
            Avanza();
        } else {
            error(Componente.OPERADOR, "arit");
            System.out.println("Se esperaba un operador o un operando");
            break;
        }
       
       if(componente.getTipo() == Componente.DIGITO)
        {
         integer_literal();
        }
        else if(componente.getTipo() == Componente.IDENTIFICADOR)
        {
         identificador();
        }
          c = componente;
       }
        
        Acomodar(Componente.SIMBOLO_ESPECIAL, ";");
    }

    private void boolean_literal() {
        Componente c;
        Avanza();
    }

    private void declaracion_Clase() {
        Componente c = componente;
        if (!c.getToken().equals("class")) {
            modificador();
        }
        c = componente;
        
        Acomodar(Componente.PALABRA_RESERVADA, "class");
        
        c = componente;
        
        
        identificador();
        c = componente;
        Acomodar(Componente.SIMBOLO_ESPECIAL, "{");
        

        //-----------------field_Declaration
        c = componente;
        //if(c.getTipo() == Componente.MOD || c.getTipo() == Componente.TIPO )
        
        field_Declaration();
        //-----------------statement
        statement();
        Acomodar(Componente.SIMBOLO_ESPECIAL, "}");
    }

    private void expression() {
        testing_expression();
    }

    private void error(int t, String to) {
       switch (t) {
            case Componente.PALABRA_RESERVADA:
                switch (to) {
                    case "class":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"class\"\t" + componente.getToken() + "\n";
                        break;
                    case "if":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"if\"\t" + componente.getToken() + "\n";
                        break;
                    case "while":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"while\"\t" + componente.getToken() + "\n";
                        break;
                }
                break;
            case Componente.SIMBOLO_ESPECIAL:
                switch (to) {
                    case "{":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"" + to + "\"\t" + componente.getToken() + "\n";
                        break;
                    case "}":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"" + to + "\"\t" + componente.getToken() + "\n";
                        break;
                    case "(":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"" + to + "\"\t" + componente.getToken() + "\n";
                        break;
                    case ")":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"" + to + "\"\t" + componente.getToken() + "\n";
                        break;
                    case ";":
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"" + to + "\"\t" + componente.getToken() + "\n";
                        break;
                    case "blanck": salida +="";
                    break;
                    default:
                        salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un Simbolo especial\t" + componente.getToken() + "\n";
                        break;
                }
                break;
            case Componente.OPERADOR:
                if (to.equals("arit")) {
                    salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un operador aritmetico\t" + componente.getToken() + "\n";
                } else {
                    break;
                }
            case Componente.TIPO:
                salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"int\" o \"boolean\"\t" + componente.getToken() + "\n";
                break;
            case Componente.MODIFICADOR:
                salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un \"public\" o \"private\"\t" + componente.getToken() + "\n";
                break;
            case Componente.DIGITO:
                salida += "Error Sintactico, Fila: " + componente.getFila() + " se espeba un digito\t" + componente.getToken() + "\n";
                break;
            case Componente.VALOR:
                salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba \"true\" o \"false\"\t" + componente.getToken() + "\n";
                break;
            case Componente.IDENTIFICADOR:
                salida += "Error Sintactico, Fila: " + componente.getFila() + " se esperaba un identificador\t" + componente.getToken() + "\n";
                break;
        }
        salida2 += "Token obtenido:" + componente.getToken() + "\n" + "Token Esperado: " + to + "\n-------------------------------------------\n";

    }

    private void field_Declaration() {
        Componente c = componente;
        if (c.getTipo() == Componente.MODIFICADOR || c.getTipo() == Componente.TIPO)
        {
            
            variable_declaration();
            
            c = componente;
            Acomodar(Componente.SIMBOLO_ESPECIAL, ";");
            field_Declaration();
        }
    }

    public String getSalida()
    {
        return salida;
    }
    private void if_Statement() {
        Componente c;
        c = componente;
        Acomodar(Componente.SIMBOLO_ESPECIAL, "(");
        expression();
        Acomodar(Componente.SIMBOLO_ESPECIAL, ")");
        Acomodar(Componente.SIMBOLO_ESPECIAL, "{");
        aritmetic_expression();
        
        Acomodar(Componente.SIMBOLO_ESPECIAL, "}");
        
        statement();
    }

    private void integer_literal() {
        Acomodar(Componente.DIGITO, componente.getToken());
    }

    private void identificador() {
        Componente c, caux, cauxa;
        String men = "";
        c = componente;
        
        men = componente.getToken();
        Acomodar(Componente.IDENTIFICADOR, c.getToken());
        men = componente.getToken();
        
    }

    private void modificador() {
        Componente c = null, caux = null;
        c = componente;
        if (c.getToken().equals("public")) {
            Avanza();
        } else if (c.getToken().equals("private")) {
            Avanza();
        } else {
            error(Componente.MODIFICADOR, "");
        }
    }

    public String motorSintactico(ArrayList<Componente> listaTokens) {
       
        
        salida = "";
        idx = 0;
        componentes = listaTokens;
        try {
            componente = componentes.get(0);
        } catch (IndexOutOfBoundsException e) {
                        
        }
        declaracion_Clase();
        if (salida.equals("")) {
            salida = "No hay errores sintacticos";
            return salida;
        } else {
            return salida;
        }

    }
    private void statement() {
        Componente c = null, caux = null;
        c = componente;
        if (c.getToken().equals("if")) {
            Avanza();
            if_Statement();
        } else if (c.getToken().equals("while")) {
            Avanza();
            while_Statement();
        } else if (c.getTipo() == Componente.MODIFICADOR || c.getTipo() == Componente.TIPO) {
            variable_declaration();
        }
    }
    private void string_literal()
    {
        Acomodar(Componente.CADENA, componente.getToken());
    }
    private void testing_expression() {
        Componente c = null, caux = null;
        c = componente;
        if (c.getTipo() == Componente.DIGITO) {
            integer_literal();
        } else {
            identificador();
        }
        c = componente;
        if (c.getToken().matches("(>|<|>=|<=|==|!=|)")) {
            Avanza();
        } else {
            error(Componente.OPERADOR, "log");
        }
        c = componente;
        if (c.getTipo() == Componente.DIGITO) {
            integer_literal();
        } else {
            identificador();
        }
    }

    private void type() {
        type_specifier();
    }

    private void type_specifier() {
        Componente c = null, caux = null;
        c = componente;
        //if(c.getToken().matches("(int|boolean)"))
        if (c.getToken().equals("int")) {
            Avanza();
        } else if (c.getToken().equals("boolean")) {
            Avanza();
        }else if(c.getToken().equals("String"))
        {
            Avanza();
        }
        else {
            error(Componente.TIPO, "");
        }
    }

    private void variable_declaration() {

        Componente c = null, caux = null;
        c = componente;
        
        if (c.getTipo() != Componente.TIPO) {
            modificador();
        }
        type();
        if (c.getToken().equals("boolean")) {
            
        }
        
        fila = componente.getFila();
        identificador();
        
        c = componente;
        if (c.getToken().equals("=")) {
            Avanza();
            variable_declarator();
        }
        
    }

    private void variable_declarator() {
        Componente c, cauxa;
        c = componente;
        
        if (c.getTipo() == Componente.DIGITO) {
            integer_literal();
        } else if (c.getTipo() == Componente.VALOR) {
            boolean_literal();
        }
        else if(c.getTipo() == componente.CADENA)
        {
            string_literal();
        }
        else
        {
            error(componente.TIPO,"");
        }
    }

    private void while_Statement() {
        Componente c = null, caux = null, cauxa = null;
        c = componente;
        Acomodar(Componente.SIMBOLO_ESPECIAL, "(");
        expression();
        Acomodar(Componente.SIMBOLO_ESPECIAL, ")");
        Acomodar(Componente.SIMBOLO_ESPECIAL, "{");
        statement();
        Acomodar(Componente.SIMBOLO_ESPECIAL, "}");
    }
}
