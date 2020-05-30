/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoObjeto;

import Buffer.WriteBuffer;
import Cuadruplos.Cuadruplo;
import java.util.ArrayList;

/**
 *
 * @author Alberto Loera
 */
public class CodigoObjeto 
{
    private String ensamblador;
    ArrayList<Cuadruplo> cuadrupl = new ArrayList<Cuadruplo>();
    WriteBuffer wb;
    String ultimo = "";
    public CodigoObjeto(ArrayList<Cuadruplo> cuadruplos) 
    {
        cuadrupl = cuadruplos;
        wb = new WriteBuffer();
        generarCodigoObeto();
        
    }
    public void generarCodigoObeto()
    {
        generarDirectivas();
        for(int i = 0; i< cuadrupl.size(); i++)
        {
            Cuadruplo cuadruplo = cuadrupl.get(i);
            switch(cuadruplo.getOperador())
            {
                
                case "+":
                {
                   ensamblador += "\n        MOV     AL,"+cuadruplo.getOperando1()+"\n"
                                  + "        ADD     AL,"+cuadruplo.getOperando2()+"\n"
                                  + "        MOV     "+cuadruplo.getVariable()+",AL\n";   
                   break;
                }
                case "-":
                {
                   ensamblador += "\n        MOV     AL,"+cuadruplo.getOperando1()+"\n"
                                  + "        SUB     AL,"+cuadruplo.getOperando2()+"\n"
                                  + "        MOV     "+cuadruplo.getVariable()+",AL\n"; 
                   break;
                }
                case "*":
                {
                    ensamblador += "\n        MOV     AL,"+cuadruplo.getOperando1()+"\n"
                                  + "        MOV     BL,"+cuadruplo.getOperando2()+"\n"
                                  + "        MUL     BL\n"
                                  + "        MOV     "+cuadruplo.getVariable()+",AL\n";       
                    break;
                }    
                case "/":
                {
                    ensamblador += "\n       MOV     AL,"+cuadruplo.getOperando1()+"\n"
                                  + "        MOV     BL,"+cuadruplo.getOperando2()+"\n"
                                  + "        DIV     BL\n"
                                  + "        MOV     "+cuadruplo.getVariable()+",AL\n";        
                    break;
                }
            }
              ultimo = cuadruplo.getVariable();
        }
        ensamblador += "\n       MOV     AL,"+ultimo+
                       "\n       MOV     X,AL";    
        
        ensamblador +="\n      .exit \n" +
"main    ENDP \n" +
"        END";
        wb.writeBuffer(ensamblador);
        
    }
    public void generarDirectivas()
    {
        ensamblador = "         .model small\n" +
                      "         .stack \n" +
                      "         .data    \n"+
                      "         X       DB       0"  ;
        for(Cuadruplo c : cuadrupl)
        {
            ensamblador +="\n         "+c.getVariable()+"     DB"+"       0";
        }
        ensamblador+="\n        .code\n" +
                     "        .startup\n" +
                     "main    proc";
        
    }
    public static void main(String[] args) {
        ArrayList<Cuadruplo> listaCu = new ArrayList<Cuadruplo>();
        listaCu.add(new Cuadruplo("T1","-", "3","1"));
        listaCu.add(new Cuadruplo("T2","+", "T1","2"));
      
        CodigoObjeto codi = new CodigoObjeto(listaCu);
        
        
        codi.generarDirectivas();
        codi.generarCodigoObeto();
    }
    public String getCodigoObjeto()
    {
        return ensamblador;
    }
}
