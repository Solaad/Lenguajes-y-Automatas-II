/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

public class Componente 
{
        public final static int PALABRA_RESERVADA=0;
	public final static int SIMBOLO_ESPECIAL = 1;
	public final static int OPERADOR = 2;
	public final static int TIPO  = 3;
	public final static int MODIFICADOR = 4;
	public final static int DIGITO = 5;
	public final static int VALOR = 6;
	public final static int IDENTIFICADOR = 8;
        public final static int CADENA = 7;	
	private String desc,token,tipoSt;
	private int tipo,columna,fila;
	private String [] significado = {"Palabra reservada","Simbolo especial","Operador","Tipo","Modificador","Digito","Valor","Cadena","Identificador"};
	public Componente(int tp,String t,int col,int fila){
		tipo = tp;
		token = t;
                columna = col;
		this.fila = fila;
		if(tipo < 9)
			desc = significado[tipo];
		else
			desc ="";
	}
	public String getDesc() {
		return desc;
	}

	public String getToken() {
		return token;
	}
	
	public int getColumna() {
		return columna;
	}

	public int getFila() {
		return fila;
	}
	public int getTipo(){
		return tipo;
	} 
        public void setToken(String tokenN)
        {
            token = tokenN;
        }
}
/**
 *
 * @author Alberto Loera
 */
