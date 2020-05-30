/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafico;

import Compilador.Simbolos;
import Grafico.VentanaPrincipal;
import ModelosTabla.ModeloTabla;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Alberto Loera
 */
public class FrameTablaSimbolos extends JDialog{
JPanel panelSim;
    public FrameTablaSimbolos(VentanaPrincipal v) throws HeadlessException 
    {
        super(v,"Tabla de tokens",true);
         panelSim = new JPanel();
         panelSim.setLayout(new BorderLayout());
         
         this.add(panelSim);
        
        Dimension pantalla = v.getSize();
        setLocationRelativeTo(v);
        setSize(400,500);
        Dimension ventana = getSize();
        setLocation((pantalla.width - ventana.width)/2,(pantalla.height - ventana.height)+100);
        this.add(panelSim);
        setResizable(false);
        
        
    }
    public void crearTabla(ArrayList<Simbolos> arraySim)
    {
        panelSim.removeAll();
        JTable tabla = new JTable(new ModeloTabla(arraySim));
        JScrollPane sp = new JScrollPane(tabla);
        panelSim.add(sp);
        panelSim.updateUI();
    }
    
    
    
}
