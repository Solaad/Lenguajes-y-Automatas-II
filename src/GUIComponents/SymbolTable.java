package GUIComponents;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import entities.Identifier;
import format.TableFormat;

public class SymbolTable extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane jsp;
	private ArrayList<Identifier> id;
	private SymbolTableModel mo;
	private boolean visible = false;
	private Listener O = new Listener(); 
	
	public SymbolTable( JFrame fr, ArrayList<Identifier> dis){
		setTitle("Symbol Token");
		id = dis;
		setSize(650, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(fr);
		init();
	}
	
	private void init(){
		createTable();
		jsp = new JScrollPane( table );
		getContentPane().add(jsp);
		
		try{
			setDefaultLookAndFeelDecorated(true);
			setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI( this );
		}catch ( Exception excepcion ) {	excepcion.printStackTrace();	}
		addWindowListener(O);
	}
	public void isVisible(boolean r){
		visible = true;
	}
	private void createTable(){
		mo = new SymbolTableModel(id);
		table = new JTable( mo );
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		//tabla.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.setFillsViewportHeight(true);
		table.setRowHeight(22);
		table.setDefaultRenderer(Object.class, new TableFormat());
	}
	public void actCat( ArrayList<Identifier> cd ){
		id = cd;
		createTable();
		jsp.setViewportView(table);
		this.setVisible(visible);
	}
	private class Listener extends WindowAdapter{

		@Override
		public void windowClosed(WindowEvent e) { visible = false; }
		
	}
}
	