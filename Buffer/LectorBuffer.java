/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buffer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alberto Loera
 */
public class LectorBuffer 
{
    String cadena = "";
    
    FileReader lectura;
    File archivoEntrada,archivoSalida;
    JFileChooser buscador ;
     

      public LectorBuffer(JTextPane areaTexto) throws FileNotFoundException, IOException 
    {
        String lineas = "";
        buscador = new JFileChooser();
        buscador.setCurrentDirectory(new File("C:\\Users\\Alberto Loera\\Desktop\\Loker"));
        buscador.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        buscador.showOpenDialog(buscador);
        archivoEntrada = (buscador.getSelectedFile());
        try
        {
        lectura = new FileReader(archivoEntrada);
        BufferedReader bufer = new BufferedReader(lectura);
       
        if(archivoEntrada.canRead())
        {
            areaTexto.setText("");
            while((lineas = bufer.readLine()) != null)
            {
                areaTexto.setText(areaTexto.getText()+lineas+"\n");
            }     
        }
        else
        {
            JOptionPane.showConfirmDialog(null,"Este archivo no se puede abrir elija un .txt");
        }
        }
        catch(IOException e)
        {
            System.out.println("Hubo un error en el archivo al quererlo leer");
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null,"No se eligio ningun arhcivo");
        }
        
    }
     
    
    
}    

