/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cuadruplos;

import Compilador.Componente;
import jdk.nashorn.internal.parser.Token;



/**
 *
 * @author Alberto Loera
 */
public class Nodo<Componente> {
    
        Componente dato;
        Nodo<Componente> padre, der, izq;

        public Nodo(Componente val) {
            dato = val;
        }
}
