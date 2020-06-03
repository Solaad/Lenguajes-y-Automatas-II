package interfaz;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import compilador.Escuchadores;
import estructuraDeDatos.ListaDoble;

public class Vista extends JFrame {
	public JMenuBar barraDelMenu;
	public JMenu menuArchivo, menuCompilar, menuOpciones, menuArchivosRecientes;
	public JMenuItem nuevoArchivo, cargarArchivo, guardarArchivo, guardarComo, salir, compilarCodigo, cambiarTema;
	public JTabbedPane codigo;
	public JTabbedPane consola;
	public ListaDoble<JTextPane> txtCodigo;
	public ListaDoble<File> codigoTemporal;
	public ListaDoble<String> rutaDeArchivoActual, nombreDelArchivo, tituloVentana;
	public ListaDoble<Integer> tama�oTextoDelEditor;
	public ListaDoble<Boolean> cambiosGuardados;
	public JList<String> tokens;
	public final String [] tituloVentanas ={"Tipo","Nombre","Valor"};
	public DefaultTableModel modelo = new DefaultTableModel(new Object[0][0],tituloVentanas);
	public JTable tablaDatos = new JTable(modelo);
	private JLabel infoEtiqueta;
	private Escuchadores escuchadores;
	public int tab = 0;
	public boolean teclaControl = false, teclaShift = false; // esto es para la combinaci�n de teclas
	public boolean teclaAlt = false, hayError = false;
	public int tema = Theme.CLARO;
	public Vista(int tema) {
		this.tema = tema;
		constructor();
	}
	public Vista() {
		super("CRACK'S Code");
		constructor();
	}
	public void constructor() {
		hazInterfaz();
		agregaEscuchadores();
		codigo.setSelectedIndex(1);
		txtCodigo.getByIndex(1).dato.requestFocus();
	}
	private void hazInterfaz() {
		setSize(1200, 950);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(700, 600));
		barraDelMenu = new JMenuBar();
		setJMenuBar(barraDelMenu);
		creaMenu();
		crearPesta�as();
		codigoTemporal = new ListaDoble<File>();
		rutaDeArchivoActual = new ListaDoble<String>();
		nombreDelArchivo = new ListaDoble<String>();
		tituloVentana = new ListaDoble<String>();
		tama�oTextoDelEditor = new ListaDoble<Integer>();
		cambiosGuardados = new ListaDoble<Boolean>();
		rutaDeArchivoActual.insertar("", 0);
		nombreDelArchivo.insertar("C�digo", 0);
		tituloVentana.insertar("CRACK'S Code", 0);
		tama�oTextoDelEditor.insertar(0, 0);
		cambiosGuardados.insertar(true, 0);
		creaFicheroTemporal();
		setVisible(true);
	}
	private void creaMenu() {
		menuArchivo = new JMenu("Archivo");
		menuCompilar = new JMenu("Compilar");
		menuOpciones = new JMenu("Opciones");
		barraDelMenu.add(menuArchivo);
		barraDelMenu.add(menuCompilar);
		barraDelMenu.add(menuOpciones);
		
		nuevoArchivo = new JMenuItem("Nuevo archivo (ctrl+N)");
		cargarArchivo = new JMenuItem("Cargar archivo (ctrl+O)");
		guardarArchivo = new JMenuItem("Guardar archivo (ctrl+S)");
		guardarComo = new JMenuItem("Guardar archivo como (ctrl+shift+S)");
		menuArchivosRecientes = new JMenu("Archivos Recientes");
		salir = new JMenuItem("Salir (alt+F4)");
		compilarCodigo = new JMenuItem("Compilar c�digo (F5)");
		cambiarTema = new JMenuItem("Cambiar de tema");
		
		menuArchivo.add(nuevoArchivo);
		menuArchivo.add(cargarArchivo);
		menuArchivo.add(guardarArchivo);
		menuArchivo.add(guardarComo);
		menuArchivo.add(menuArchivosRecientes);
			menuArchivosRecientes.add(new JMenuItem("Aqui iria una direcci�n"));
			menuArchivosRecientes.add(new JMenuItem("Aqui otra"));
			menuArchivosRecientes.add(new JMenuItem("Asi es"));
		menuArchivo.add(salir);
		menuCompilar.add(compilarCodigo);
		menuOpciones.add(cambiarTema);
	}
	private void crearPesta�as() {
		codigo = new JTabbedPane();
		ImageIcon iconoNuevoTab = new ImageIcon("src/images/pesta�aNueva.png");
		codigo.insertTab("", iconoNuevoTab, null, "Nueva pesta�a", 0);
		txtCodigo = new ListaDoble<JTextPane>();
		txtCodigo.insertar(new JTextPane(), 0);
		txtCodigo.getInicio().dato.setFont(new Font("Consolas", Font.PLAIN, 16));
		codigo.addTab("Eo", new JScrollPane(txtCodigo.getInicio().dato));
		codigo.setTabComponentAt(1, new Cross("C�digo"));
		
		consola = new JTabbedPane();
		final String HTML = "<HTML>\n" // HTML no es divertido xd
					+ "  <head>\n"
					+ "    <style type=\"text/css\">\n" // CSS tampoco xd
					+ "      body {\n"
					+ "        text-align: center;\n"
					+ "      }\n"
					+ "      .fuenteGris {\n"
					+ "        font-size: 15px;\n"
					+ "        font-weight: normal;\n"
					+ "        color: #888888;\n"
					+ "      }\n"
					+ "      .fuenteTitulo {\n"
					+ "        font-size: 20px;\n"
					+ "        font-weight: bold;\n"
					+ "        text-decoration: underline;\n"
					+ "      }\n"
					+ "      .importante {\n"
					+ "        color: #454545;\n"
					+ "      }\n"
					+ "      .salir {\n"
					+ "        color: #DD8888;\n"
					+ "      }\n"
					+ "    </style>\n"
					+ "  </head>\n"
					+ "  <body>\n"
					+ "    <p class=\"fuenteTitulo\">\n"
					+ "      Acortadores\n"
					+ "    </p>\n"
					+ "    <p class=\"fuenteGris\">\n"
					+ "      <span class=\"importante\">\n"
					+ "        <strong>\n"
					+ "          Control + O:\n"
					+ "        </strong>\n"
					+ "          Abrir archivo<br />\n"
					+ "        <strong>\n"
					+ "        Control + S:\n"
					+ "        </strong>\n"
					+ "        Guardar archivo<br />\n"
					+ "        <strong>\n"
					+ "          Control + Shift + S:\n"
					+ "        </strong>\n"
					+ "        Guardar archivo como<br />\n"
					+ "        <strong>\n"
					+ "          F5:\n"
					+ "        </strong>\n"
					+ "        Compilar c�digo<br />\n"
					+ "      </span>\n"
					+ "      <strong>\n"
					+ "        Control + Shift + entrar:\n"
					+ "      </strong>\n"								
					+ "      Ir a la siguiente pesta�a<br />\n"
					+ "      <strong>\n"
					+ "        F1:\n"
					+ "      </strong>\n"
					+ "      Mostrar esta pesta�a<br />\n"
					+ "      <strong>\n"
					+ "        F2:\n"
					+ "      </strong>\n"
					+ "      Mostrar pesta�a consola<br />\n"
					+ "      <strong>\n"
					+ "        F3:\n"
					+ "      </strong>\n"								
					+ "      Mostrar pesta�a datos<br />\n"
					+ "      <strong>\n"
					+ "        Control + coma:\n"
					+ "      </strong>\n"								
					+ "      Ir al c�digo anterior<br />\n"
					+ "      <strong>\n"
					+ "        Control + punto:\n"
					+ "      </strong>\n"								
					+ "      Ir al c�digo siguiente<br />\n"
					+ "      <strong>\n"
					+ "        Control + N:\n"
					+ "      </strong>\n"								
					+ "      Nuevo c�digo<br />\n"
					+ "      <strong>\n"
					+ "        Control + W:\n"
					+ "      </strong>\n"								
					+ "      Cerrar c�digo actual<br />\n"
					+ "      <strong>\n"
					+ "        ESC:\n"
					+ "      </strong>\n"								
					+ "      Pantalla completa/Modo ventana<br />\n"
					+ "      <span class=\"salir\">\n"
					+ "        <strong>\n"
					+ "          Alt + F4:\n"
					+ "        </strong>\n"
					+ "        Salir\n"
					+ "      </span>\n"
					+ "    </p>\n"
					+ "  </body>\n"
					+ "</HTML>";
		//System.out.println("HTML de la pesta�a info\n" + HTML);
		tokens=new JList<String>();
		tokens.setFont(new Font("Consolas", Font.PLAIN, 16));
		JScrollPane pesta�aConsola = new JScrollPane(tokens);
		JScrollPane pesta�aDatos = new JScrollPane(tablaDatos);
		JPanel info = new JPanel();
		infoEtiqueta = new JLabel(HTML);
		info.add(infoEtiqueta);
		consola.addTab("Info", new JScrollPane(info));
		consola.addTab("Consola", pesta�aConsola);
		consola.addTab("Datos", pesta�aDatos);
		
		
		JPanel fuckyou = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
        fuckyou.setLayout(layout);
        fuckyou.add(codigo);
		fuckyou.add(consola);

		add(fuckyou);
	}
	private void creaFicheroTemporal() {
		try {
			codigoTemporal.insertar(File.createTempFile("codigoTemporal",null), 0);
			codigoTemporal.getFin().dato.deleteOnExit();
		}
		catch (Exception e) {
			System.out.println("Error al guardar el archivo temporal para compilar su c�digo.");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error al guardar el archivo temporal para compilar su c�digo.","Alerta",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void agregaEscuchadores() {
		escuchadores = new Escuchadores(this);
		codigo.setFocusable(false);
		consola.setFocusable(false);
		tablaDatos.setFocusable(false);
		tokens.setFocusable(false);
		// Teclas
			codigo.addKeyListener(escuchadores);
			txtCodigo.getByIndex(1).dato.addKeyListener(escuchadores);
			addKeyListener(escuchadores);
			barraDelMenu.addKeyListener(escuchadores);
			tokens.addKeyListener(escuchadores);
			tablaDatos.addKeyListener(escuchadores);
		
		// Items del menu
			// Archivo
				nuevoArchivo.addActionListener(escuchadores);
				cargarArchivo.addActionListener(escuchadores);
				guardarArchivo.addActionListener(escuchadores);
				guardarComo.addActionListener(escuchadores);
				salir.addActionListener(escuchadores);
			// Compilar
				compilarCodigo.addActionListener(escuchadores);
			// Opciones
				cambiarTema.addActionListener(escuchadores);
			
		// Cambio entre pesta�as
			infoEtiqueta.addAncestorListener(escuchadores);
			tokens.addAncestorListener(escuchadores);
			tablaDatos.addAncestorListener(escuchadores);
			codigo.addMouseListener(escuchadores);
			txtCodigo.getByIndex(1).dato.addAncestorListener(escuchadores);
			
		// Ventana
			addWindowListener(escuchadores);
	}
	public void nuevaPesta�a(String texto, String ruta, String nombre, int tama�o) {
		int tab = codigo.getTabCount() - 1;
		txtCodigo.insertar(new JTextPane(), tab);
		txtCodigo.getByIndex(tab).dato.setText(texto);
		txtCodigo.getByIndex(tab).dato.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtCodigo.getByIndex(tab).dato.addKeyListener(escuchadores);
		txtCodigo.getByIndex(tab).dato.addAncestorListener(escuchadores);
		creaFicheroTemporal();
		rutaDeArchivoActual.insertar(ruta, tab);
		nombreDelArchivo.insertar(nombre, tab);
		if(ruta.length() == 0)
			tituloVentana.insertar("CRACK'S Code", tab);
		else
			tituloVentana.insertar(nombre + " - " + ruta + " - CRACK'S Code", tab);
		tama�oTextoDelEditor.insertar(tama�o, tab);
		cambiosGuardados.insertar(true, tab);
		codigo.addTab(nombre, new JScrollPane(txtCodigo.getByIndex(tab).dato));
		codigo.setSelectedIndex(tab+1);
		codigo.setTabComponentAt(codigo.getSelectedIndex(), new Cross(nombre));
	}
	public void nuevaPesta�a() {
		nuevaPesta�a("", "", "C�digo", 0);
	}
	public void cerrarPesta�a(boolean band) {
		int tab = codigo.getSelectedIndex() - 1;
		try {
			codigoTemporal.getByIndex(tab).dato.delete();
		} catch(Exception e) {
			System.err.println("Error al eliminar el archivo temporal.");
			System.out.println("Pero es temporal, se borrar� cuando el programa acabe. Igual ten el recorrido completo del error:");
			e.printStackTrace();
		}
		codigoTemporal.borrar(tab);
		txtCodigo.borrar(tab);
		rutaDeArchivoActual.borrar(tab);
		nombreDelArchivo.borrar(tab);
		tituloVentana.borrar(tab);
		tama�oTextoDelEditor.borrar(tab);
		cambiosGuardados.borrar(tab);
		codigo.remove(tab+1);
		if(codigo.getTabCount()==1 && !band) {
			nuevaPesta�a();
			txtCodigo.getByIndex(codigo.getSelectedIndex()).dato.requestFocus();
		}
	}
	public void cerrarPesta�a() {
		cerrarPesta�a(false);
	}
	public void cargarArchivo() {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de cracks (*.txt, *.java, *.crack)", "txt", "java", "crack");
        
        JFileChooser explorador = new JFileChooser();              //Muestra una ventana que permite navegar por los directorios
        explorador.setDialogTitle("Abrir");                        //Agrega t�tulo al cuadro de di�logo
        explorador.setFileFilter(filtro);                          //Se agrega el filtro de tipo de archivo al cuadro de di�logo
 
        try {
	        if (explorador.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	
			FileReader archivo;
			BufferedReader lector;
			File fichero = explorador.getSelectedFile();
			archivo = new FileReader(fichero);
			String texto = "";
			if (archivo.ready()) {
				lector = new BufferedReader(archivo);
				String linea = "";
				String str = "";
				while (linea != null) {
					linea = lector.readLine();
					if(linea == null)
						break;
					if(linea.length()>0)
						str+= linea + "\n";
				}
				str = str.substring(0, str.length()-1);
				texto +=str + "\n";
				//txtCodigo.getByIndex(codigo.getSelectedIndex()).dato.setText(str);
			}
			else
				System.out.print("El archivo no est� listo para su lectura.");
			final int tab = codigo.getSelectedIndex();
			nuevaPesta�a(texto, fichero.getPath(), fichero.getName(), codigo.getSelectedIndex());
			setTitle(nombreDelArchivo.getByIndex(tab).dato + " - " + rutaDeArchivoActual.getByIndex(tab).dato + " - CRACK'S Code");
			tituloVentana.getByIndex(tab).dato = getTitle();
			
			}
		}
        catch(FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "Archivo no encontrado", "", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	public void guardarArchivo(String ruta) {
        try {
			FileWriter archivo = new FileWriter(ruta);
			PrintWriter escritor = new PrintWriter(archivo);
			
			int index;
			if(codigo.getTabCount()>2)
				index = codigo.getSelectedIndex() -1;
			else
				index = codigo.getSelectedIndex();
			JTextPane codigoEnPesta�a = txtCodigo.getByIndex(index).dato;
			String str = codigoEnPesta�a.getDocument().getText(0, codigoEnPesta�a.getText().length());
			String salida = "", aux = "";
			while(str.length()>0) { // mientras haya elementos en el texto para a�adir al archivo
				aux = str.charAt(0) + ""; // obtienes primer el elemento del texto
				salida+= aux; // lo concatenas a salida
				str = str.substring(1, str.length()); // quitas el primer elemento del texto, para que en la pr�xima iteraci�n se tome el siguiente elemento
				if(aux.hashCode() == 10) { // si el dato que est�s a�adiendo es un salto de linea
					escritor.println(salida); // se escribe la linea de todo lo concatenado en ella
					salida = ""; // y se reinicia la variable para concatenar lo de la siguiente linea
				}
			}
			escritor.print(salida); // escribe la �ltima linea del texto
			archivo.close();
			final int tab = codigo.getSelectedIndex() - 1;
			setTitle(nombreDelArchivo.getByIndex(tab).dato + " - " + rutaDeArchivoActual.getByIndex(codigo.getSelectedIndex() - 1).dato + " - CRACK'S Code");
			tituloVentana.getByIndex(tab).dato = getTitle();
			
			String textoAux = txtCodigo.getByIndex(tab).dato.getText(), 
					rutaAux = rutaDeArchivoActual.getByIndex(tab).dato, 
					nombreAux = nombreDelArchivo.getByIndex(tab).dato;
			int tama�oAux = tama�oTextoDelEditor.getByIndex(tab).dato;
			cerrarPesta�a(true);
			nuevaPesta�a(textoAux, rutaAux, nombreAux, tama�oAux);
			
		}catch (Exception e) {
        	e.printStackTrace();
	    }
	}
	public void guardarArchivoComo() {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de cracks (*.txt, *.java, *.crack)", "txt", "java", "crack");
		JFileChooser explorador = new JFileChooser();              //Muestra una ventana que permite navegar por los directorios
        explorador.setApproveButtonText("Guardar");
		explorador.setDialogTitle("Guardar como");                 //Agrega t�tulo al cuadro de di�logo
        explorador.setFileFilter(filtro);                          //Se agrega el filtro de tipo de archivo al cuadro de di�logo
 
        try {
	        if (explorador.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File fichero = explorador.getSelectedFile();      
				
				int index;
				if(codigo.getTabCount()>2)
					index = codigo.getSelectedIndex() - 1;
				else
					index = codigo.getSelectedIndex();
				rutaDeArchivoActual.getByIndex(index).dato = fichero.getPath();
				nombreDelArchivo.getByIndex(index).dato = fichero.getName();
				guardarArchivo(fichero.getPath());
				setTitle(nombreDelArchivo.getByIndex(codigo.getSelectedIndex() - 1).dato + " - " + rutaDeArchivoActual.getByIndex(codigo.getSelectedIndex() - 1).dato + " - CRACK'S Code");
				JOptionPane.showMessageDialog(null, "El archivo ha sido guardado con �xito.", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch (Exception e) {
        	e.printStackTrace();
	    }
	}
	
	class Cross extends JPanel {
		  private JLabel etiqueta;
		  private JButton boton;

		  public Cross(String titulo) {
			  setTitle(titulo);
		  }
		  public void setTitle(String titulo) {
			    setOpaque(false);
			    setLayout(new java.awt.GridBagLayout());
			    GridBagConstraints gbc = new GridBagConstraints();
			    gbc.gridx=0;
			    gbc.gridy=0;
			    gbc.weightx=1;
			    etiqueta=new JLabel(titulo+" ");
			    boton=new JButton();
			    boton.setFocusable(false);
			    ImageIcon iconoNuevoTab = new ImageIcon("src/images/cerrarPesta�a.png");
			    boton.setIcon(iconoNuevoTab);
			    boton.setPreferredSize(new Dimension(iconoNuevoTab.getIconWidth(), iconoNuevoTab.getIconHeight()));
			    //Listener para cierre de tabs con acceso estatico al `JTabbedPane`
			    boton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						cerrarPesta�a();
					}
			    });
			    add(etiqueta,gbc);
			    gbc.gridx++;
			    gbc.weightx=0;
			    add(boton,gbc);
		  }
		}
}