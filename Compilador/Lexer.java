/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import java.util.ArrayList;

/**
 *
 * @author Alberto Loera
 */
public class Lexer {
    public static final ComponentesLexicos COMLEX [] = {new ComponentesLexicos("(public|private)$","Modificador"),
                                                        new ComponentesLexicos("(if|while|for|class)","Palabra reservada"),
                                                        new ComponentesLexicos("(int|boolean|String)","Tipo"),
                                                        new ComponentesLexicos("(>|<|>=|<=|==|!=|\\+|-|/|\\*)","Operador"),
                                                        new ComponentesLexicos("[{|}|\\\\;|=|(|)]","Simbolo especial"),
                                                        new ComponentesLexicos("^\\d+$","Digito"),
                                                        new ComponentesLexicos("(true|false)","Valor"),
                                                        new ComponentesLexicos("(\".*\")","Cadena"),
                                                        new ComponentesLexicos("^[A-Za-z]+$","Identificador")};
                                                        
    
    Componente componente;
    ArrayList<Componente> listaTokens;
    Parser parser;
    String salida;
    
    boolean error= false,marcad=false;
    public Lexer() {
        listaTokens= new ArrayList<Componente>();
       
    }

    public boolean motorLexicoM(String cadena) 
    {      
        salida = "";
        error=true;
        listaTokens.clear();
        String tokens = "";
        char caracter = '.';
        int columna = 0, lineas = 1;        
        for (int i = 0; i < cadena.length(); i++) 
        {
            caracter = cadena.charAt(i);
            if (Character.isLetterOrDigit(caracter)) 
            {
                tokens = tokens + caracter;
                int j = i + 1;
                columna++;	
                try 
                {
                    while (Character.isLetterOrDigit(cadena.charAt(j))) 
                    {
                        tokens = tokens + cadena.charAt(j);
                        j++;
                        if (j == cadena.length()) {
                            break;
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) 
                {
                  
                }
                i = j - 1;

            }
            else if(caracter == '\"')
            {
                tokens += caracter;
                int j = i+1 ;
                columna++;
                
                try {
                    while (cadena.charAt(j) != '\"') 
                    {
                        tokens = tokens + cadena.charAt(j);
                        j++;
                        if (j == cadena.length()) {
                            break;
                        }
                    }
                    tokens += cadena.charAt(j);
                } catch (StringIndexOutOfBoundsException e) 
                {
                   tokens = ""; 
                }
                
                i = j ;
            }
            else if (isOperatorOrCharacterEspecial(caracter))
            {
                tokens += caracter;
                int j = i + 1;

                try 
                {
                    while (isOperatorOrCharacterEspecial(cadena.charAt(j))) 
                    {
                        tokens += cadena.charAt(j);
                        j++;

                        if (j == cadena.length()) {
                            break;
                        }
                    }
                } 
                catch (StringIndexOutOfBoundsException e) 
                {
                    
                }
                i = j - 1;
            }
            else if(evaluarSeparador(caracter))
                    {
                      tokens += caracter;  
                      int j = i + 1;
                        
                        
                      i=j-1;
                    }   
            else if(String.valueOf(caracter).matches("\\S")){	//el caracter de cualquier caracter que no sea un espacion en blanco,un tabulador, un retorno de carro o un salto de linea
				 tokens += caracter;		//concatenamos el caracter
            }
            else if( caracter == '\t' || caracter == '\r' || caracter == ' ')
            {
                error = false;
            }
            else if(cadena.charAt(i)==10)
            {
                lineas ++;
                error = false;
            }
          
              for (int y = 0; y < COMLEX.length; y++) 
              {
                if (tokens.matches(COMLEX[y].geteExpresion())) 
                {
                    error = false;
                    //salida += COMLEX[y].getDescripcion()+"->"+tokens+"\n";
                    componente = new Componente(COMLEX[y].obtenerTipo(), tokens,columna,lineas);
                    listaTokens.add(componente);
                    break;
                }
            }
            if (error == true) 
            { //en caso de que el token sea uno no valido
                if (caracter != '\n' || caracter != '\r' || caracter != '\t' || caracter != ' ') {// se descarta la opcion de que sea un salto de liena o un retorno de carro o un tabulador o un espacion en blanco 
                    //muestra la salida de los errores por consola
                    salida += "\tError en el token \"" + tokens + "\" en la posicion "+lineas+" \n";
                    System.out.println("\tError en el token \"" + tokens + "\" en la posicion \n");
                }
            }            
              
            error = true;
            tokens = "";
        }
        if(salida.equals(""))
        {
            return true;
        }
        
        return false;
    }
   
    private  boolean isOperatorOrCharacterEspecial(char o) 
    {
        switch (o) 
        {
            case '+':
                return true;
            case '-':
                return true;
            case '/':
                return true;
            case '*':
                return true;
            case '<':
                return true;
            case '>':
                return true;
            case '=':
                return true;
            case ';':
                return true;
            case '!':
                return true;
                
        }
        return false;
    }
    public boolean evaluarSeparador(char c)
    {
       switch(c)
       {
        case '(':
            return true;
        case ')':
            return true;
        case '{':
            return true;
        case '}':
            return true;
       }
       return false;
    }
    public String getSalida()
    {
        return salida;
    }
    public ArrayList<Componente> getListTokens()
    {
        return listaTokens;
    }
}
