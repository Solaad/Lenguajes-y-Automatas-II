/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafico;

import Buffer.LectorBuffer;
import CodigoObjeto.CodigoObjeto;
import Compilador.Componente;
import Compilador.Lexer;
import Compilador.Parser;
import Compilador.SemanticAnalizer;
import Compilador.Simbolos;
import ModelosTabla.ModeloTabla2;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;

/**
 *
 * @author Alberto Loera
 */
public class VentanaPrincipal extends JFrame {
    FrameTablaSimbolos tabla;
  
    public JToolBar toolBar;
    private LectorBuffer lector;
    private EspacioTexto panelTexto;
    private EspacioConsola panelConsola;
    private FrameTablaTokens frm_tablaTokens;
    private JPanel panel_Tokens, panelJTree;
    private WorkSpace workSpaceTexto, workspaceConsola;
    private JButton btn_Correr, btn_clsConsola;
    public Oyente oyente;
    ArrayList<Simbolos> simbolos;
    ArrayList<Componente> tokens;
    // ArrayList <PanelTexto> componentesPestañas =new ArrayList<PanelTexto>();
    JPopupMenu menu;
    JMenuBar barraMenu;
    JMenu menuB, ver;
    JMenuItem abrir, botonAñadirPestaña, copiar, cerrar, TablaSimbolos, TablaTokens;
    private boolean errorLex;
    private Lexer motorLexico = new Lexer();
    private Parser parser;

    public VentanaPrincipal() throws HeadlessException, IOException {

        simbolos = new ArrayList<Simbolos>();
        tokens = new ArrayList<Componente>();
        setVisible(true);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(pantalla.width - 200, pantalla.height - 200);
        Dimension ventana = getSize();
        setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        setSize(pantalla.width - 201, pantalla.height - 201);

    }

    public void init() throws IOException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        frm_tablaTokens = new FrameTablaTokens(this);
        tabla = new FrameTablaSimbolos(this);
        repaint();

        //creando el toolbar
        toolBar = new JToolBar("Barra herramientas");
        toolBar.addSeparator();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        //BOTONES
        botones();
        btn_Correr.setIcon(redimencionarImagen("/Imagenes/playOji.png"));

        toolBar.add(btn_Correr);
        toolBar.add(btn_clsConsola);
        toolBar.setPreferredSize(new Dimension(0, 35));
        add(toolBar, BorderLayout.NORTH);

        //Crear Menu
        barraMenu = new JMenuBar();
        menuB = new JMenu("File");
        ver = new JMenu("Ver");

        //menu items
        botonAñadirPestaña = new JMenuItem("Nuevo pestaña");
        TablaSimbolos = new JMenuItem("Tabla Simbolos");
        TablaTokens = new JMenuItem("Tabla Tokens");
        copiar = new JMenuItem("Copiar Todo");
        abrir = new JMenuItem("Abirr");

        menuB.add(abrir);
        menuB.add(copiar);
        menuB.add(botonAñadirPestaña);
        ver.add(TablaSimbolos);
        ver.add(TablaTokens);

        //se añaden los nemonicos
        menuB.setMnemonic(KeyEvent.VK_F);
        abrir.setMnemonic(KeyEvent.VK_A);
        copiar.setMnemonic(KeyEvent.VK_C);

        //se añade el menu al frame
        barraMenu.add(menuB);
        barraMenu.add(ver);
        setJMenuBar(barraMenu);

        //creacion del panel de JTree
        panelJTree = new JPanel();
        
        //creacion del palnel textoIDE
        workSpaceTexto = new WorkSpace();
        panelTexto = new EspacioTexto();

        //creacion del panel consola
        panelConsola = new EspacioConsola();

        //creacion del panel tokens
        panel_Tokens = new JPanel();
        
        panel_Tokens.setLayout(new BorderLayout());
        panel_Tokens.repaint();
        panel_Tokens.updateUI();

        workspaceConsola = new WorkSpace();
        workspaceConsola.addTab("Consola", panelConsola);

        //se crea las pestañas del documento
        workSpaceTexto.addTab("Analizador", panelTexto);
        //ceacion de los splitpane
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, workSpaceTexto, workspaceConsola);
        JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp, panel_Tokens);
        JSplitPane sp3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelJTree, sp2);
        sp.setResizeWeight(0.8);
        sp2.setResizeWeight(0.8);
        sp3.setResizeWeight(0.2);
        this.add(sp3);
        menu = new JPopupMenu();
        cerrar = new JMenuItem("Cerar");
        menu.add(cerrar);
        workSpaceTexto.setComponentPopupMenu(menu);
        añadirOyentes();
    }

    public void disposeArrays() {
        simbolos = null;
        tokens = null;
    }

    public void disposeEngines() {
        motorLexico = null;
        parser = null;
    }

    public void botones() {
        btn_Correr = new JButton();
        btn_clsConsola = new JButton();
        btn_Correr.setSize(30, 30);
        btn_clsConsola.setSize(30, 30);
        btn_clsConsola.setText("cls");
    }

    public void añadirOyentes() {
        oyente = new Oyente();
        btn_Correr.setToolTipText("Correr");
        btn_Correr.addActionListener(oyente);
        btn_clsConsola.addActionListener(oyente);
        TablaSimbolos.addActionListener(oyente);
        TablaTokens.addActionListener(oyente);
        abrir.addActionListener(oyente);
        botonAñadirPestaña.addActionListener(oyente);
    }

    public Icon redimencionarImagen(String ruta) {
        ImageIcon imagenIconoCorrer = new ImageIcon(getClass().getResource("/Imagenes/playOji.png"));
        Image imagenEscaladaCorrer = imagenIconoCorrer.getImage().getScaledInstance(btn_Correr.getWidth(), btn_Correr.getHeight(), Image.SCALE_SMOOTH);
        Icon icono = new ImageIcon(imagenEscaladaCorrer);
        return icono;
    }

    class Oyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cadenaConsola = "";
            System.out.println(3+6/8*4+9);
            if (e.getSource() == btn_Correr) {
                parser = new Parser();
                motorLexico = new Lexer();
                int x = workSpaceTexto.getSelectedIndex();
                EspacioTexto espacioAux = (EspacioTexto) workSpaceTexto.getSelectedComponent();
                String texto = espacioAux.getTextPane().getText();
                errorLex = motorLexico.motorLexicoM(texto);
                if (errorLex == false) {
                    cadenaConsola = motorLexico.getSalida();
                } else if (errorLex) {
                    cadenaConsola = "Lexico Completo\n";
                    ArrayList<Componente> array = motorLexico.getListTokens();
                    cadenaConsola += parser.motorSintactico(array) + "\n";
                    tokens = motorLexico.getListTokens();
                    if (parser.getSalida().equals("No hay errores sintacticos")) {
                        SemanticAnalizer ans = new SemanticAnalizer(tokens);
                        ans.semanticEngine();
                        if (ans.getSalida().equals("")) {
                            cadenaConsola += "Semantico Correcto\n";
                            simbolos = ans.getSymbolTable();
                            cadenaConsola+=ans.getCua();
                            CodigoObjeto codiOb = new CodigoObjeto(ans.getListCua());
                        } else {
                            cadenaConsola += ans.getSalida();
                        }
                    } else {
                        simbolos.clear();
                    }
                }
                panel_Tokens.removeAll();
                panelConsola.setTextoConola(cadenaConsola);
                JTable tabla = new JTable(new ModeloTabla2(tokens));
                JScrollPane sp = new JScrollPane(tabla);
                panel_Tokens.add(sp);
                panel_Tokens.updateUI();
                disposeEngines();
                cadenaConsola = null;
            } else if (e.getSource() == btn_clsConsola) {
                panelConsola.setTextoConola("");
            } else if (e.getSource() == botonAñadirPestaña) {
                workSpaceTexto.addTab("Nueva Pestaña code", new EspacioTexto());
            }
            if (e.getSource() == abrir) {
                EspacioTexto espacioAux = (EspacioTexto) workSpaceTexto.getSelectedComponent();
                try {
                    lector = new LectorBuffer(espacioAux.getTextPane());
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getSource() == TablaSimbolos) {
                tabla.crearTabla(simbolos);
                tabla.setVisible(true);
            }
            if (e.getSource() == TablaTokens) {
                frm_tablaTokens.crearTabla(tokens);
                frm_tablaTokens.repaint();
                frm_tablaTokens.setVisible(true);
            }
        }
    }
}
