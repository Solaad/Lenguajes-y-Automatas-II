/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.text.Caret;
import sun.awt.AWTAccessor;

/**
 *
 * @author Alberto Loera
 */
public class EspacioTexto extends JScrollPane
{
    Caret barrita ;
    JTextPane textoIde;
    boolean swcontrol = false;
    
    public EspacioTexto() 
    {
        
        setLayout(new ScrollPaneLayout());
        textoIde = new JTextPane();
        textoIde.setBackground(new Color(0, 0 ,0));
        textoIde.setFont(new Font("Consolas", Font.PLAIN, 16));
        textoIde.setForeground(Color.white);
        textoIde.setCaretColor(Color.white);
        
        
        
        setViewportView(textoIde);
    }
    public JTextPane getTextPane ()
    {
        return textoIde;
    }
      
    /*  public class Oyenteteclado implements KeyListener
      {


        @Override
        public void keyTyped(KeyEvent e) {
          
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(e.getKeyCode() == KeyEvent.VK_CONTROL )
            { 
                System.out.println("SE PRESIONO CONTROL");
                swcontrol = true;
            }
            else 
                if(swcontrol && e.getKeyCode() == KeyEvent.VK_G)
                {         
                try {
                    lector = new LectorBuffer(textoIde);
                    System.out.println("SE PRESIONO CONTROL + G");
                    swcontrol = false;
                } catch (IOException ex) {
                    Logger.getLogger(PanelTexto.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else 
                    if(swcontrol && e.getKeyCode() == KeyEvent.VK_C)
                    {
                        System.out.println("SE PRESIONO CONTROL MAS C");
                        textoIde.copy();
                        swcontrol = false;
                    }
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
            if(e.getKeyCode() == KeyEvent.VK_CONTROL)
            {
                System.out.println("SE SOLTO CONTROL");
                swcontrol = false;
            }
           
        }

       
          
      }*/
}

