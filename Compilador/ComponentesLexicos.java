/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author Alberto Loera
 */
public class ComponentesLexicos 
{
    private String expresion ;
    private String descripcion;
    private int tipo ;
    private String [] significado = {"Palabra reservada","Simbolo especial","Operador","Tipo","Modificador","Digito","Valor","Cadena","Identificador"};
    public ComponentesLexicos(String expresion, String descripcion) 
    {
        this.expresion = expresion;
        this.descripcion = descripcion;
        darTipo(descripcion);
        
    }
    public int obtenerTipo()
    {
        return tipo;
    }
    public String geteExpresion()
    {
        return expresion;
    }
    public String getDescripcion()
    {
        return descripcion;
    }
    public void darTipo(String sigmificado)
    {
        int cont = 0;
        for(String sigm : significado)
        {
            if(sigm.equals(sigmificado))
            {
                tipo = cont;
                break;
            }
         
            cont++;
        }
    }
    
    
    
}
