/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cuadruplos;

import Compilador.Componente;
import java.util.ArrayList;

/**
 *
 * @author Alberto Loera
 */
public class Arbol 
         
{        int resultado = 0;
String t;
        public Nodo<Componente> raiz,actual;
        String post = "",cuadruplo ="";
        ArrayList <Cuadruplo> cuad = new ArrayList<Cuadruplo>();
	private boolean bandera_nose_que_hace = false;
	int cosa = 1;
        public Arbol() {
		raiz = null;
	}
        public void añadirNodo(Componente c)
        {
           actual = añade(actual,c);
           bandera_nose_que_hace = false;
           
        }
        public Nodo<Componente> añade(Nodo<Componente> act,Componente c)
        {
           Nodo<Componente> nuevo, aux;
		if( act == null)
                { //sirve para inicializar el arbol o insertar un nuevo nodo raiz / cambiar
			nuevo = new Nodo<Componente>(c);
			//System.out.println("\t\t\tRaiz -> "+nuevo.dato.getToken());
			if( raiz != null){
				nuevo.izq = raiz;
				raiz.padre = nuevo;
			}
			raiz = nuevo;
    
			return nuevo;
		}
                nuevo = new Nodo<Componente>(c);
                int prioridad_actual = priori(act),prioridad_nuevo = priori(nuevo);
                if(prioridad_actual >= prioridad_nuevo)
                {
                    aux = añade(act.padre,c);
                    if(bandera_nose_que_hace)
                    {
                        act.padre = aux;
                        aux.izq = act;   
                        
                    }
                    act = aux;
                    bandera_nose_que_hace = false;
                }
                else
                {
                    nuevo.padre = act;
                    act.der = nuevo;
                    act = nuevo;
                    bandera_nose_que_hace = true;
                    
                }
                return act;
        }
       
      private int priori(Nodo<Componente> n){
		int val = -1;
		Componente t = (Componente) n.dato;
		switch (t.getTipo()) {
		case Componente.DIGITO: case Componente.IDENTIFICADOR:
			val = 3;
			break;
		case Componente.OPERADOR:
			switch (t.getToken()) {
			case "*": case "/":
				val = 2;
				break;
			case "+": case "-":
				val = 1;
				break;
			}
			break;
		}
		return val;
	}
       public void recorrePosOrden(Nodo<Componente> node) {
	     if (node != null) 
             {
	    	 recorrePosOrden(node.izq);
	         recorrePosOrden(node.der);
	         post += (node.dato.getToken());   
	     }
	 }
        public String generaCuadruplo(Nodo<Componente> node) {
	     if (node != null)
              
	      {
	    	 String v1,v2;
	    	 v1 = generaCuadruplo(node.izq);
	               v2 = generaCuadruplo(node.der);
	               if( node.dato.getTipo() == Componente.OPERADOR)
                       {
	        	 //visit(node.dato.getToken());
	        	 //System.out.println("T"+cosa+":= "+v1+node.dato.getToken()+v2);
	        	 
                         cuad.add(new Cuadruplo("T"+cosa, node.dato.getToken(), v1, v2));
                         
                         
                         cuadruplo += String.format("%15s %s %5s %5s %5s %n%n","T"+cosa,"=",v1,node.dato.getToken(),v2);
                         
	        	 t = "T"+cosa;
                         cosa++;
	        	 return "T"+(cosa-1);
	         	}
                         return node.dato.getToken();   
	     }
	     return "";
	 }
            public String resuelve(Nodo<Componente> node) {
	      
              if (node != null)
	      {
	    	 String v1,v2;
                       v1 = resuelve(node.izq);
	               v2 = resuelve(node.der);
	               if( node.dato.getTipo() == Componente.OPERADOR)
                        {
                         
	        	 switch(node.dato.getToken())
                         {
                             case "+":
                             {
                                 resultado = Integer.parseInt(v1)+Integer.parseInt(v2);
                                 break;
                             }
                             case "-":
                             {
                                 resultado = Integer.parseInt(v1)-Integer.parseInt(v2);
                                 break;
                             }
                             case "*":
                             {
                                 resultado = Integer.parseInt(v1)*Integer.parseInt(v2);
                                 break;
                             }
                             case "/":
                             {
                                 resultado = Integer.parseInt(v1)/Integer.parseInt(v2);
                                 break;
                             }
                         } 
	        	 return String.valueOf(resultado);
	         	}
                       else
                       { 
                           //System.out.println(node.dato.getToken());
                           return node.dato.getToken();   
                       }  
	     }
	     return "0";
	 }
        public String getPost()
        {
            return post;
        }
        public String getCuadruplo()
        {
            cuadruplo += String.format("%15s %s %s %n","X"+"",":=",t);
            return cuadruplo;
        }
        public String getResultado()
        {
            return String.valueOf(resultado);
        }
        public ArrayList getArreCuad()
        {
            return cuad;
        }
       public static void main(String[] args)
       {
           
           
           String exp = "3 + 6 / 8 * 4 + 9";
           String [] arregloExpr = exp.split(" ");
           
           Arbol arbolito = new Arbol();
           int x = 0;
           for(int i = 0 ; i< arregloExpr.length;i++)
           {
               
               if((i+1)%2 == 0)
               {
                 x = Componente.OPERADOR;
               }
               else
               {
                 x = Componente.DIGITO;
               }
            
               Componente comp = new Componente(x,arregloExpr[i], 0, 0);
               arbolito.añadirNodo(comp);
           }
           arbolito.recorrePosOrden(arbolito.raiz);
           System.out.println(arbolito.generaCuadruplo(arbolito.raiz));
           ArrayList<Cuadruplo> cua = arbolito.getArreCuad();
           for(Cuadruplo s : cua)
           {
               System.out.println(s.getOperador());
           }
       }
}
