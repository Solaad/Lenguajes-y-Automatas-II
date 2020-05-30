/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cuadruplos;

/**
 *
 * @author Alberto Loera
 */
public class Cuadruplo 
{
   private String variable;
   
   private String operador;
   private String operando1;
   private String operando2;

    public Cuadruplo(String var,String op,String oper1,String oper2) 
    {
        this.variable = var;
        
        this.operador = op;
        this.operando1 = oper1;
        this.operando2 = oper2;
    }

    public String getVariable() {
        return variable;
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }
   
   
}
