/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelosTabla;

import Compilador.Componente;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alberto Loera
 */
public class ModeloTabla2 extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;
	private String[] titulo ={"Tipo","Token"};
	private ArrayList<Componente> tokens ;

    public ModeloTabla2(ArrayList<Componente> array) 
    {
        tokens = array;
    }
       

    @Override
    public int getRowCount() {
        return tokens.size();
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
            case 0: return tokens.get(rowIndex).getTipo();
            case 1: return tokens.get(rowIndex).getToken();
            default: return null;
        }
    }        
    
}
