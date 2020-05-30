/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafico;

import Compilador.Componente;
import Grafico.VentanaPrincipal;
import ModelosTabla.ModeloTabla2;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Alberto Loera
 */
public class FrameTablaTokens extends JDialog
{

    public FrameTablaTokens(VentanaPrincipal v) 
    {
        super(v,"Tabla de tokens",true);
        setResizable(false);
        Dimension pantalla = v.getSize();
        setLocationRelativeTo(v);
        setSize(400,500);
        Dimension ventana = getSize();
        setLocation((pantalla.width - ventana.width)/2,(pantalla.height - ventana.height)+100);
        
    }
      public void crearTabla(ArrayList<Componente> arraySim)
    {
        JTable tabla = new JTable(new ModeloTabla2(arraySim));
        JScrollPane sp = new JScrollPane(tabla);
        this.add(sp);
    }
   
    
}

