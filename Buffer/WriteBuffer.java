/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Loera
 */
public class WriteBuffer 
{
    private File archivoSalida;
     public void writeBuffer(String cadena)
      {
        try {
            archivoSalida = new File("C:\\Users\\Alberto Loera\\Desktop\\object.txt");
            FileWriter fw = new FileWriter(archivoSalida);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cadena);
            bw.close();
           } catch (IOException ex) {
            Logger.getLogger(LectorBuffer.class.getName()).log(Level.SEVERE, null, ex);
           }
      }
     public static void main(String[] args) {
        WriteBuffer wr = new WriteBuffer();
        wr.writeBuffer("asdwadaw");
    }
}
