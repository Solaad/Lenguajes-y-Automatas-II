/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelosTabla;

import Compilador.Simbolos;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import jdk.nashorn.internal.parser.Token;

/**
 *
 * @author Alberto Loera
 */
public class ModeloTabla extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private String[] titulo ={"Tipo","Token","Valor","tamaño","Renglon"};
	private ArrayList<Simbolos> simbolos ;

    public ModeloTabla(ArrayList<Simbolos> array) 
    {
        simbolos = array;
    }
       

    @Override
    public int getRowCount() 
    {
        return simbolos.size();
    }

    @Override
    public int getColumnCount() {
        return titulo.length;
        
    }

    @Override
    public String getColumnName(int column) {
       return titulo[column];
    }
   
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0: return simbolos.get(rowIndex).getType();
            case 1: return simbolos.get(rowIndex).getIdentificador();
            case 2: return simbolos.get(rowIndex).getValor();
            case 3: return simbolos.get(rowIndex).getTamaño();
            case 4: return simbolos.get(rowIndex).getFila();
            default: return null;
        }
    }
        
        
	
}
   