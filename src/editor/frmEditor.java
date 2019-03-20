/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import clasesBase.clsPaperFactory;
import clasesBase.clsPrint;
import clasesBase.clsPrintElemento;
import Print.clsVisor;
import miColorChooser.miColorChooser;
import miFontChooser.miFontChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import miButton.miButton;
import miComboBox.miComboBox;
import miTextField.miTextField;

/**
 *
 * @author toni
 */
public class frmEditor extends JDialog {
    private clsPrint printer;
    private clsVisor visor;
    private String direccion="";
    private Color colorBorder=new Color(21, 164, 250);
    private Color colorBackgroundSelect=new Color(243, 244, 249);
    private Color colorBackgroundUnselect=new Color(240, 240, 240);
    private int layerActual;
    private clsPaperFactory formatos = new clsPaperFactory();
    
    JList lista = new JList();
    JScrollPane sLista = new JScrollPane();
    DefaultListModel modelo = new DefaultListModel();
    
    JPanel pnlEditor= new JPanel();
    JPanel pnlPrinter= new JPanel();
    JPanel pnlGestion= new JPanel();
    JPanel pnlElementos= new JPanel();
    
    JLabel lblLayer= new JLabel("Capa :");
    
    JLabel lbl1= new JLabel("Tipo :");
    JLabel lbl2= new JLabel("Posicion :");
    JLabel lbl3= new JLabel("X");
    JLabel lbl4= new JLabel("Y");
    JLabel lbl5= new JLabel("Tamaño");
    JLabel lbl6= new JLabel("Ancho");
    JLabel lbl7= new JLabel("Alto");
    JLabel lbl8= new JLabel("Texto :");
    JLabel lbl9= new JLabel("Origen de Datos :");
    JLabel lbl10= new JLabel("Columna :");
    JLabel lbl11= new JLabel("Area Tileable :");
    JLabel lbl12= new JLabel("Tipo Origen :");
    JLabel lbl13= new JLabel("Fuente :");
    JLabel lbl14= new JLabel("Espesor :");
    JLabel lbl15= new JLabel("Color :");
    JLabel lbl16= new JLabel("Rotacion :");
    JLabel lbl17= new JLabel("Borde :");
    JLabel lbl18= new JLabel("Borde Espesor :");
    JLabel lbl19= new JLabel("Borde Color :");
    JLabel lbl20= new JLabel("Relleno :");
    JLabel lbl21= new JLabel("Relleno Color :");
    JLabel lbl22= new JLabel("Alineacion :");
    JLabel lbl23= new JLabel("Repeticiones :");
    JLabel lblTileable= new JLabel("Tileable :");
    
    JLabel lblFormato= new JLabel("Formato :");
    JLabel lblOrientacion= new JLabel("Orientacion :");
    JLabel lblMargenX= new JLabel("Margen X :");
    JLabel lblMargenY= new JLabel("Margen Y :");
    JLabel lblZoom= new JLabel("Zoom :");
    JLabel lblFileName= new JLabel("Archivo nombre :");
    JLabel lblFileDir= new JLabel("Archivo Directorio :");
    JLabel lblConexion= new JLabel("Conexion Tipo :");
    
    
    miComboBox cmbLayer;
    miComboBox cmbToLayer;
    miComboBox cmbTipo;
    miComboBox cmbTipoOrigen;
    miComboBox cmbAlineacionH;
    miComboBox cmbAlineacionV;
    
    miComboBox cmbFormato;
    miComboBox cmbOrientacion;
    miComboBox cmbConexion;
    
    miTextField txtMargenX;
    miTextField txtMargenY;
    miTextField txtZoom;
    miTextField txtFileName;
    miTextField txtFileDir;
    
    
    miTextField txtX;
    miTextField txtXT;
    miTextField txtY;
    miTextField txtYT;
    miTextField txtAncho;
    miTextField txtAlto;
    miTextField txtTexto;
    miTextField txtOrigen;
    miTextField txtColumna;
    miTextField txtFuente;
    miTextField txtEspesor;
    miTextField txtColor;
    miTextField txtRotacion;
    miTextField txtBordeEspesor;
    miTextField txtBordeColor;
    miTextField txtRellenoColor;
    miTextField txtRepeticiones;
    
    miButton btnNuevo;
    miButton btnDuplicar;
    miButton btnEliminar;
    miButton btnSubir;
    miButton btnBajar;
    miButton btnMover;
    miButton btnLayer;
    
    miButton btn1;
    miButton btn2;
    miButton btn3;
    miButton btn4;
    miButton btn5;
    
    JCheckBox chkBorde= new JCheckBox();
    JCheckBox chkTileable= new JCheckBox();
    JCheckBox chkRelleno= new JCheckBox();
    
    public frmEditor(clsPrint p, clsVisor v){
        super();
        printer = p;
        visor=v;
        
        inicializar();
    }
    public frmEditor(JDialog d,clsPrint p, clsVisor v){
        super(d);
        printer = p;
        visor=v;
        
        inicializar();
    }
    
    private void inicializar(){
        this.setSize(510, 950);
        this.setTitle("Editor de Objetos");
        //this.setResizable(false);
        
        inicializarTexto();
        inicializarCombo();
        inicializarBotones();
        inicializarColorBorder();
        
        cargarLayers(cmbLayer);
        cargarLayers(cmbToLayer);
        cargarAlineacionH(cmbAlineacionH);
        cargarAlineacionV(cmbAlineacionV);
        cargarFormatos(cmbFormato);
        cargarOrientacion(cmbOrientacion);
        cargarConexion(cmbConexion);
        
        sLista.setViewportView(lista);
        
        inicializarUI();
        this.add(pnlEditor);
        
        layerActual=0;
        cmbLayer.setSelectedIndex(layerActual);
        cmbTipo.setEnabled(false);
        
        mostrarLista();
        mostrarPrinter();
        mostrarElemento();
        
        inicializarListener();
    }
    private void inicializarTexto(){
        txtX= new miTextField(colorBorder,colorBackgroundSelect);
        txtXT= new miTextField(colorBorder,colorBackgroundSelect);
        txtY= new miTextField(colorBorder,colorBackgroundSelect);
        txtYT= new miTextField(colorBorder,colorBackgroundSelect);
        txtAncho= new miTextField(colorBorder,colorBackgroundSelect);
        txtAlto= new miTextField(colorBorder,colorBackgroundSelect);
        txtTexto= new miTextField(colorBorder,colorBackgroundSelect);
        txtOrigen= new miTextField(colorBorder,colorBackgroundSelect);
        txtColumna= new miTextField(colorBorder,colorBackgroundSelect);
        txtFuente= new miTextField(colorBorder,colorBackgroundSelect);
        txtEspesor= new miTextField(colorBorder,colorBackgroundSelect);
        txtColor= new miTextField(colorBorder,colorBackgroundSelect);
        txtRotacion= new miTextField(colorBorder,colorBackgroundSelect);
        txtBordeEspesor= new miTextField(colorBorder,colorBackgroundSelect);
        txtBordeColor= new miTextField(colorBorder,colorBackgroundSelect);
        txtRellenoColor= new miTextField(colorBorder,colorBackgroundSelect);
        txtRepeticiones= new miTextField(colorBorder,colorBackgroundSelect);
        
        txtMargenX= new miTextField(colorBorder,colorBackgroundSelect);
        txtMargenY= new miTextField(colorBorder,colorBackgroundSelect);
        txtZoom= new miTextField(colorBorder,colorBackgroundSelect);
        txtFileName= new miTextField(colorBorder,colorBackgroundSelect);
        txtFileDir= new miTextField(colorBorder,colorBackgroundSelect);
    }
    private void inicializarCombo(){
        cmbLayer=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbToLayer=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbTipo=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbTipoOrigen=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbAlineacionH=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbAlineacionV=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        
        cmbFormato=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbOrientacion=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
        cmbConexion=new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnselect);
    }
    private void inicializarColorBorder(){
        btnNuevo.setColorBorder(colorBorder);
        btnDuplicar.setColorBorder(colorBorder);
        btnEliminar.setColorBorder(colorBorder);
        btnSubir.setColorBorder(colorBorder);
        btnBajar.setColorBorder(colorBorder);
        btnMover.setColorBorder(colorBorder);
        txtX.setColorBorder(colorBorder);
        txtXT.setColorBorder(colorBorder);
        txtY.setColorBorder(colorBorder);
        txtYT.setColorBorder(colorBorder);
        txtAncho.setColorBorder(colorBorder);
        txtAlto.setColorBorder(colorBorder);
        txtTexto.setColorBorder(colorBorder);
        txtOrigen.setColorBorder(colorBorder);
        txtColumna.setColorBorder(colorBorder);
        txtFuente.setColorBorder(colorBorder);
        txtEspesor.setColorBorder(colorBorder);
        txtColor.setColorBorder(colorBorder);
        txtRotacion.setColorBorder(colorBorder);
        txtBordeEspesor.setColorBorder(colorBorder);
        txtBordeColor.setColorBorder(colorBorder);
        txtRellenoColor.setColorBorder(colorBorder);
        txtRepeticiones.setColorBorder(colorBorder);
        
        txtMargenX.setColorBorder(colorBorder);
        txtMargenY.setColorBorder(colorBorder);
        txtZoom.setColorBorder(colorBorder);
        txtFileName.setColorBorder(colorBorder);
        txtFileDir.setColorBorder(colorBorder);
        
        cmbLayer.setColorBorder(colorBorder);
        cmbToLayer.setColorBorder(colorBorder);
        cmbTipo.setColorBorder(colorBorder);
        cmbTipoOrigen.setColorBorder(colorBorder);
        cmbAlineacionH.setColorBorder(colorBorder);
        cmbAlineacionV.setColorBorder(colorBorder);
        
        cmbFormato.setColorBorder(colorBorder);
        cmbOrientacion.setColorBorder(colorBorder);
        cmbConexion.setColorBorder(colorBorder);
        
    }
    private void inicializarBotones(){
         btnNuevo= new miButton("Nuevo");
            btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnNuevo.setIcon(new ImageIcon(getClass().getResource("/Images/circle-plus.png")));
         btnDuplicar= new miButton("Duplicar");
            btnDuplicar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnDuplicar.setIcon(new ImageIcon(getClass().getResource("/Images/copy.png")));
         btnEliminar= new miButton("Eliminar");
            btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/Images/circle-minus.png")));
         btnSubir= new miButton("Subir");
            btnSubir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnSubir.setIcon(new ImageIcon(getClass().getResource("/Images/arrow-u.png")));
         btnBajar= new miButton("Bajar");
            btnBajar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnBajar.setIcon(new ImageIcon(getClass().getResource("/Images/arrow-d.png")));
         btnMover= new miButton("Mover A:");
            btnMover.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnMover.setIcon(new ImageIcon(getClass().getResource("/Images/insert.png")));
         btnLayer= new miButton("");
            btnLayer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnLayer.setIcon(new ImageIcon(getClass().getResource("/Images/list2.png")));
        
         btn1= new miButton("...");
         btn2= new miButton("...");
         btn3= new miButton("...");
         btn4= new miButton("...");
         btn5= new miButton("...");
    }
    
    private void inicializarUI(){
        inicializarUIPrinter();
        inicializarUIGestion();
        inicilializarUIElementos();
        
        //pnlGestion.setBorder(BorderFactory.createEtchedBorder());
        //pnlElementos.setBorder(BorderFactory.createEtchedBorder());
        
        GroupLayout layout = new GroupLayout(pnlEditor);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(pnlPrinter)
                .addComponent(pnlGestion)
                .addComponent(pnlElementos)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(pnlPrinter)
                .addComponent(pnlGestion)
                .addComponent(pnlElementos)
        );
        pnlEditor.setLayout(layout);
        
    }
    private void inicializarUIPrinter(){
        JSeparator sep= new JSeparator();

        GroupLayout layout = new GroupLayout(pnlPrinter);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(true);
        
        int at = 140;
        int ap = 90;
        int al = 330;
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblFormato,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbFormato,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblOrientacion,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbOrientacion,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblMargenX,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtMargenX,javax.swing.GroupLayout.PREFERRED_SIZE, ap, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(lblMargenY,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtMargenY,javax.swing.GroupLayout.PREFERRED_SIZE, ap, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblZoom,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtZoom,javax.swing.GroupLayout.PREFERRED_SIZE, ap, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblFileName,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtFileName,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblFileDir,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtFileDir,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblConexion,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbConexion,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addComponent(sep)
        );
        
        int ht=20;
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblFormato,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbFormato,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblOrientacion,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbOrientacion,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblMargenX,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtMargenX,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblMargenY,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtMargenY,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblZoom,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtZoom,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblFileName,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtFileName,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )    
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblFileDir,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtFileDir,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblConexion,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbConexion,javax.swing.GroupLayout.PREFERRED_SIZE, ht, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGap(3)
            .addComponent(sep,javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlPrinter.setLayout(layout);
        
        txtFileName.setEditable(false);
        txtFileDir.setEditable(false);
        cmbConexion.setEnabled(false);
    }
    private void inicializarUIGestion(){
        JSeparator sep1 = new JSeparator();
        JSeparator sep2 = new JSeparator();
        JSeparator sep3 = new JSeparator();
        GroupLayout layout = new GroupLayout(pnlGestion);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(true);
        
        int at = 140;
        int al = 330;
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLayer,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbLayer,javax.swing.GroupLayout.PREFERRED_SIZE, al-45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLayer,javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnNuevo,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDuplicar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep1,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep2,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubir,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBajar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep3,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMover,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbToLayer,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addComponent(sLista,javax.swing.GroupLayout.PREFERRED_SIZE, al, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
        );
        
        int ht=25;
        int hs=5;
        int h=32;
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblLayer,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbLayer,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLayer,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGap(10)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDuplicar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep1,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep2,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubir,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBajar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep3,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMover,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbToLayer,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addComponent(sLista,javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
        );
        pnlGestion.setLayout(layout);
        
    }
    private void inicilializarUIElementos(){
        txtRepeticiones.setHorizontalAlignment(0);
        txtRepeticiones.setEditable(false);
        
        //cargar listas 
        cargarTipoOrigen(cmbTipoOrigen);
        cargarTipo(cmbTipo);
        
        //inicializar los botones del editor
        btn1.setName("1");
        btn2.setName("2");
        btn3.setName("3");
        btn4.setName("4");
        btn5.setName("5");
        
        //posicionar elementos
        int a =140;
        int b=60;
        GroupLayout layout = new GroupLayout(pnlElementos);
        
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl1,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl2,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl3,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtX,javax.swing.GroupLayout.PREFERRED_SIZE, a-20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtY)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl5,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl6,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAncho,javax.swing.GroupLayout.PREFERRED_SIZE, a-20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl7,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlto)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl22,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAlineacionH)
                    .addComponent(cmbAlineacionV)
            )    
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl8,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTexto)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl9,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrigen)
                    .addComponent(btn1,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl10,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColumna)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl11,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtXT)
                    .addComponent(txtYT)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lblTileable,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkTileable)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl23,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRepeticiones)
            )              
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl12,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoOrigen)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl13,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFuente)
                    .addComponent(btn2,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl14,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspesor,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl15,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColor)
                    .addComponent(btn3,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl16,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRotacion,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl17,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBorde)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl18,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBordeEspesor,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl19,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBordeColor)
                    .addComponent(btn4,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl20,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkRelleno)
            )
            .addGroup(layout.createSequentialGroup()
                    .addComponent(lbl21,javax.swing.GroupLayout.PREFERRED_SIZE, a, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRellenoColor)
                    .addComponent(btn5,javax.swing.GroupLayout.PREFERRED_SIZE, b, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
        );
        
        int c=20;
        int s=10;
        layout.setVerticalGroup(layout.createSequentialGroup()
            
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl1,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl2,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl3,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtX,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl4,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtY,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl5,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl6,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAncho,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl7,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlto,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl22,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAlineacionH,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAlineacionV,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
                .addGap(0, 0, s)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl8,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTexto,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl9,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrigen,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl10,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColumna,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl11,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtXT,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYT,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTileable,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkTileable,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl23,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRepeticiones,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )                    
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl12,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoOrigen,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
                
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl13,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFuente,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn2,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
                .addGap(0, 0, s)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl14,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspesor,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl15,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColor,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl16,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRotacion,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
                .addGap(0, 0, s)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl17,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBorde,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl18,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBordeEspesor,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl19,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBordeColor,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
                .addGap(0, 0, s)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl20,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkRelleno,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl21,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRellenoColor,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5,javax.swing.GroupLayout.PREFERRED_SIZE, c, javax.swing.GroupLayout.PREFERRED_SIZE)
            )
        );
        
        pnlElementos.setLayout(layout);
    }
    
    //FUNCIONES DE CARGA
        private void cargarLayers(JComboBox j){
            j.removeAllItems();
            for(int i =0;i<printer.getCantidadLayers();i++){
                j.addItem(printer.getLayer(i).getNombre());
            }
        }
        private void cargarAlineacionH(JComboBox j){
            j.addItem("Centrado");
            j.addItem("Izquierda");
            j.addItem("Derecha");
        }
        private void cargarAlineacionV(JComboBox j){
            j.addItem("Centrado");
            j.addItem("Arriba");
            j.addItem("Abajo");
        }
        private void cargarTipo(JComboBox j){
            //0-vacio, 1-imagen, 2-Titulo, 3-Campo texto, 4-linea, 5-Rectangulo, 6-circulo
            j.addItem("Vacio");
            j.addItem("Imagen");
            j.addItem("Titulo");
            j.addItem("Campo Texto");
            j.addItem("Linea");
            j.addItem("Rectangulo");
            j.addItem("Circulo");
        }
        private void cargarTipoOrigen(JComboBox j){
            //0-string, 1-int, 2-Double, 3-Date, 4 Boolean
            j.addItem("String");
            j.addItem("Int");
            j.addItem("Double");
            j.addItem("Date");
            j.addItem("Boolean");
            j.addItem("ID");
        }
        private void cargarFormatos(JComboBox j){
            //0-string, 1-int, 2-Double, 3-Date, 4 Boolean
            for (int i=0; i<formatos.getSize();i++){
                j.addItem(formatos.getName(i));
            }
        }
        private void cargarOrientacion(JComboBox j){
            j.addItem("Horizontal");
            j.addItem("Vertical");
        }
        private void cargarConexion(JComboBox j){
            j.addItem("H2");
            
        }
    
    //FUNCIONES DE MUESTRA
        public void mostrarLista(){
            clsPrintElemento aux;
            modelo.clear();
            
            for (int i=0;i<printer.getSelectedLayer().getCantidadElementos();i++){
                aux=printer.getSelectedLayer().getObjeto(i);
                modelo.addElement(i + "-" + aux.getTipoString() + "-" + aux.getTexto());
            }
            
            lista.setModel(modelo);
        }
        public void mostrarPrinter(){
            if (printer!=null){
                //MUESTRA DATOS DEL PRINTER
                cargarLayers(cmbLayer);
                cargarLayers(cmbToLayer);
                
                cmbFormato.setSelectedIndex(formatos.getID(printer.getFormatoPapel()));
                cmbOrientacion.setSelectedIndex(printer.getOrientacion());
                txtMargenX.setText(String.valueOf(printer.getMargenX()));
                txtMargenY.setText(String.valueOf(printer.getMargenY()));
                txtZoom.setText(String.valueOf((int)(printer.getZoom()*100)));
                txtFileName.setText(printer.getFileName());
                txtFileDir.setText(printer.getFileDir());
                cmbConexion.setSelectedItem(printer.getConexion());
            }
        }
        public void mostrarElemento(){
            if (printer.getSelected()!=null){
                //MUESTRA DATOS ELEMENTO SELECCIONADO
                
                if (printer.getSelected()!=null){
                    cmbTipo.setSelectedIndex(printer.getSelected().getTipo());
                    cmbAlineacionH.setSelectedIndex(printer.getSelected().getAlineacionHorizontal());
                    cmbAlineacionV.setSelectedIndex(printer.getSelected().getAlineacionVertical());
                    txtX.setText(String.valueOf(printer.getSelected().getPuntoX()));
                    txtXT.setText(String.valueOf(printer.getSelected().getAreaTileableX()));
                    txtY.setText(String.valueOf(printer.getSelected().getPuntoY()));
                    txtYT.setText(String.valueOf(printer.getSelected().getAreaTileableY()));
                    txtRepeticiones.setText(String.valueOf(printer.getSelected().getRepeticiones()));
                    chkTileable.setSelected(printer.getSelected().getTileable());
                    txtAncho.setText(String.valueOf(printer.getSelected().getAncho()));
                    txtAlto.setText(String.valueOf(printer.getSelected().getAlto()));
                    txtTexto.setText(printer.getSelected().getTexto());
                    txtOrigen.setText(printer.getSelected().getOrigenDatos());
                    txtColumna.setText(printer.getSelected().getColumna());
                    cmbTipoOrigen.setSelectedIndex(printer.getSelected().getTipoOrigen());
                    Font f=printer.getSelected().getFuente();
                    txtFuente.setText(String.valueOf(f.getFontName()+ "-" + f.getSize()));
                    txtEspesor.setText(String.valueOf(printer.getSelected().getEspesor()));
                    txtColor.setBackground(printer.getSelected().getColor());
                    txtRotacion.setText(String.valueOf(printer.getSelected().getRotacion()));
                    chkBorde.setSelected(printer.getSelected().getBorde());
                    txtBordeEspesor.setText(String.valueOf(printer.getSelected().getBordeEspesor()));
                    txtBordeColor.setBackground(printer.getSelected().getBordeColor());
                    chkRelleno.setSelected(printer.getSelected().getRelleno());
                    txtRellenoColor.setBackground(printer.getSelected().getRellenoColor());
                }
            }else{
                cargarTipoVacio();
            }
        }
        public void limpiarLista(){
            modelo.clear();
        }
        public void limpiarElemento(){
                cmbTipo.setSelectedIndex(-1);
                cmbAlineacionH.setSelectedIndex(-1);
                cmbAlineacionV.setSelectedIndex(-1);
                txtX.setText("0");
                txtXT.setText("0");
                txtY.setText("0");
                txtYT.setText("0");
                txtRepeticiones.setText("0");
                chkTileable.setSelected(false);
                txtAncho.setText("0");
                txtAlto.setText("0");
                txtTexto.setText("");
                txtOrigen.setText("");
                txtColumna.setText("");
                cmbTipoOrigen.setSelectedIndex(-1);
                txtFuente.setText("");
                txtEspesor.setText("1");
                txtColor.setBackground(Color.WHITE);
                txtRotacion.setText("0");
                chkBorde.setSelected(false);
                txtBordeEspesor.setText("1");
                txtBordeColor.setBackground(Color.WHITE);
                chkRelleno.setSelected(false);
                txtRellenoColor.setBackground(Color.WHITE);
        }
    
    //FUNCIONES DE MOVIMIENTO DE OBJETOS
        private void moverA(){
            int numero=lista.getSelectedIndex();
            if (numero>-1){
                printer.moverElemento(cmbLayer.getSelectedIndex(), numero, cmbToLayer.getSelectedIndex());
                actualizar();
            }


        }
        private void subirObjeto(){
            int numero=lista.getSelectedIndex();
            if (numero>-1){
                printer.moveUP(cmbLayer.getSelectedIndex(), numero);
                numero--;
            }

            actualizar();
            lista.setSelectedIndex(numero);
        }
        private void bajarObjeto(){
            int numero=lista.getSelectedIndex();
                if (numero>-1){
                    printer.moveDown(cmbLayer.getSelectedIndex(), numero);
                    numero++;
                }
                actualizar();
                lista.setSelectedIndex(numero);
        }
    
    //FUNCIONES ABRIR VENTANAS
        private void abrirColores(int n,Color c){
            //setEnabled(false);
            miColorChooser colores=new miColorChooser(this);
            colores.setTag(n);
            //colores.setListenerAceptar(colores.inicializarListenerAceptar());
            //colores.setListenerCancelar(colores.inicializarListenerCancelar());
            colores.setLocationRelativeTo(this);
            colores.setActual(c);
            colores.setSeleccionado(c);
            colores.setModal(true);

            colores.setVisible(true);

            if (colores.getDialogResultValue()==miColorChooser.OK_OPTION){
                switch (n){
                    case 1:
                        printer.getSelected().setColor(colores.getSeleccionado());
                        break;
                    case 2:
                        printer.getSelected().setBordeColor(colores.getSeleccionado());
                        break;
                    case 3:
                        printer.getSelected().setRellenoColor(colores.getSeleccionado());
                        break;
                }
            }

            mostrarElemento();
            visor.repaint();
        }
        private void abrirFuentes(){
            miFontChooser f = new miFontChooser();
            f.setSelectedFont(printer.getSelected().getFuente());
            f.setColorBorder(colorBorder);
            f.showDialog(this);

            if (f.getDialogResultValue()==miFontChooser.OK_OPTION){
                printer.getSelected().setFuente(f.getSelectedFont());
            }
            mostrarElemento();
            visor.repaint();
        }
        private void abrirCapas(){
            frmLayers f = new frmLayers(printer);
            f.setLocationRelativeTo(this);
            f.setModal(true);
            
            f.setVisible(true);
            
            cargarLayers(cmbLayer);
            cargarLayers(cmbToLayer);
            cmbLayer.setSelectedIndex(printer.getSelectedLayerID());
            
            visor.repaint();
        }
    
    //FUNCIONES GET & SET
        public void setDireccion(String s){
            direccion=s;
        }
        public void setVisor(clsVisor v){
            visor=v;
        }
        public void setColorBorder(Color c){
            colorBorder=c;

            inicializarColorBorder();
        }
        public void setPrinter(clsPrint p){
            printer=p;
            
            actualizar();
        }
    
    //FUNCIONES DE ACTUALIZACION
        public void actualizar(){
            mostrarLista();
            mostrarPrinter();
            
            visor.repaint();
        }
        public void actualizarColor(int n, Color c){
            switch (n){
                case 3:
                    printer.getSelected().setColor(c);
                    break;
                case 4:
                    printer.getSelected().setBordeColor(c);
                    break;
                case 5:
                    printer.getSelected().setRellenoColor(c);
                    break;
            }
            mostrarElemento();
            visor.repaint();
        }
        public void actualizarElemeto(int layer, int pos){
            cmbLayer.setSelectedIndex(layer);
            
            lista.setSelectedIndex(pos);
            lista.ensureIndexIsVisible(pos);
            
            if (!printer.getSelectedLayer().isVisible()){
                printer.setSelected(null);
            }
            mostrarElemento();
        }
    
    //FUNCIONES CONFIGURACION TIPO OBJETO
    /*** configurar editor***/
        private void configurarTipo(){
            switch (cmbTipo.getSelectedIndex()){
                case 0:
                    //vacio
                    cargarTipoVacio();
                    break;
                case 1:
                    //1-imagen
                    cargarTipoImagen();
                    break;
                case 2:
                    //2-Titulo
                    cargarTipoTitulo();
                    break;
                case 3:
                    //3-Campo texto
                    cargarTipoTexto();
                    break;    
                case 4:
                    //4-linea
                    cargarTipoLinea();
                    break;
                case 5:
                    //5-Rectangulo
                    cargarTipoRectangulo();
                    break;
                case 6:
                    //6-circulo
                    cargarTipoCirculo();
                    break;
            }        
        }
        
        private void cargarTipoVacio(){
            //cmbTipo.setEnabled(false);
            limpiarElemento();
            
            cmbTipo.setSelectedIndex(0);
            if (printer.getSelected()!=null) cmbTipo.setEnabled(true);
            else cmbTipo.setEnabled(false);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(false);
            cmbAlineacionH.setVisible(false);
            cmbAlineacionV.setVisible(false);

            lbl2.setVisible(false);
            lbl3.setVisible(false);
            lbl4.setVisible(false);
            txtX.setVisible(false);
            txtY.setVisible(false);
            
            lbl5.setVisible(false);
            lbl6.setVisible(false);
            lbl7.setVisible(false);
            txtAncho.setVisible(false);
            txtAlto.setVisible(false);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(false);
            txtTexto.setVisible(false);
            
            lbl9.setVisible(false);
            txtOrigen.setVisible(false);
            btn1.setVisible(false);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(false);
            txtFuente.setVisible(false);
            btn2.setVisible(false);
            
            lbl14.setVisible(false);
            txtEspesor.setVisible(false);
            
            lbl16.setVisible(false);
            txtRotacion.setVisible(false);
            
            lbl18.setVisible(false);
            txtBordeEspesor.setVisible(false);
            
            lbl15.setVisible(false);
            txtColor.setVisible(false);
            btn3.setVisible(false);
            
            lbl19.setVisible(false);
            txtBordeColor.setVisible(false);
            btn4.setVisible(false);
            
            lbl21.setVisible(false);
            txtRellenoColor.setVisible(false);
            btn5.setVisible(false);

            lbl17.setVisible(false);
            chkBorde.setVisible(false);
            
            lbl20.setVisible(false);
            chkRelleno.setVisible(false);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);
        }
        private void cargarTipoImagen(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(false);
            cmbAlineacionH.setVisible(false);
            cmbAlineacionV.setVisible(false);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(true);
            txtAncho.setVisible(true);
            txtAlto.setVisible(true);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(false);
            txtTexto.setVisible(false);
            
            lbl9.setVisible(true);
            txtOrigen.setVisible(true);
            btn1.setVisible(true);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(false);
            txtFuente.setVisible(false);
            btn2.setVisible(false);
            
            lbl14.setVisible(false);
            txtEspesor.setVisible(false);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(true);
            txtBordeEspesor.setVisible(true);
            
            lbl15.setVisible(false);
            txtColor.setVisible(false);
            btn3.setVisible(false);
            
            lbl19.setVisible(true);
            txtBordeColor.setVisible(true);
            btn4.setVisible(true);
            
            lbl21.setVisible(false);
            txtRellenoColor.setVisible(false);
            btn5.setVisible(false);

            lbl17.setVisible(true);
            chkBorde.setVisible(true);
            
            lbl20.setVisible(false);
            chkRelleno.setVisible(false);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);
        }
        private void cargarTipoTitulo(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(true);
            cmbAlineacionH.setVisible(true);
            cmbAlineacionV.setVisible(true);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(true);
            txtAncho.setVisible(true);
            txtAlto.setVisible(true);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(true);
            txtTexto.setVisible(true);
            
            lbl9.setVisible(false);
            txtOrigen.setVisible(false);
            btn1.setVisible(false);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(true);
            txtFuente.setVisible(true);
            btn2.setVisible(true);
            
            lbl14.setVisible(false);
            txtEspesor.setVisible(false);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(true);
            txtBordeEspesor.setVisible(true);
            
            lbl15.setVisible(true);
            txtColor.setVisible(true);
            btn3.setVisible(true);
            
            lbl19.setVisible(true);
            txtBordeColor.setVisible(true);
            btn4.setVisible(true);
            
            lbl21.setVisible(true);
            txtRellenoColor.setVisible(true);
            btn5.setVisible(true);

            lbl17.setVisible(true);
            chkBorde.setVisible(true);
            
            lbl20.setVisible(true);
            chkRelleno.setVisible(true);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);
        }
        private void cargarTipoTexto(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(true);
            cmbTipoOrigen.setVisible(true);
            
            lbl22.setVisible(true);
            cmbAlineacionH.setVisible(true);
            cmbAlineacionV.setVisible(true);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(true);
            txtAncho.setVisible(true);
            txtAlto.setVisible(true);
            
            lbl11.setVisible(true);
            txtXT.setVisible(true);
            txtYT.setVisible(true);
            
            lbl8.setVisible(true);
            txtTexto.setVisible(true);
            
            lbl9.setVisible(true);
            txtOrigen.setVisible(true);
            btn1.setVisible(true);
            
            lbl10.setVisible(true);
            txtColumna.setVisible(true);
            
            lbl13.setVisible(true);
            txtFuente.setVisible(true);
            btn2.setVisible(true);
            
            lbl14.setVisible(false);
            txtEspesor.setVisible(false);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(true);
            txtBordeEspesor.setVisible(true);
            
            lbl15.setVisible(true);
            txtColor.setVisible(true);
            btn3.setVisible(true);
            
            lbl19.setVisible(true);
            txtBordeColor.setVisible(true);
            btn4.setVisible(true);
            
            lbl21.setVisible(true);
            txtRellenoColor.setVisible(true);
            btn5.setVisible(true);

            lbl17.setVisible(true);
            chkBorde.setVisible(true);
            
            lbl20.setVisible(true);
            chkRelleno.setVisible(true);
            
            lbl23.setVisible(true);
            txtRepeticiones.setVisible(true);
            lblTileable.setVisible(true);
            chkTileable.setVisible(true);
        }
        private void cargarTipoLinea(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(false);
            cmbAlineacionH.setVisible(false);
            cmbAlineacionV.setVisible(false);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(true);
            txtAncho.setVisible(true);
            txtAlto.setVisible(true);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(false);
            txtTexto.setVisible(false);
            
            lbl9.setVisible(false);
            txtOrigen.setVisible(false);
            btn1.setVisible(false);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(false);
            txtFuente.setVisible(false);
            btn2.setVisible(false);
            
            lbl14.setVisible(true);
            txtEspesor.setVisible(true);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(false);
            txtBordeEspesor.setVisible(false);
            
            lbl15.setVisible(true);
            txtColor.setVisible(true);
            btn3.setVisible(true);
            
            lbl19.setVisible(false);
            txtBordeColor.setVisible(false);
            btn4.setVisible(false);
            
            lbl21.setVisible(false);
            txtRellenoColor.setVisible(false);
            btn5.setVisible(false);

            lbl17.setVisible(false);
            chkBorde.setVisible(false);
            
            lbl20.setVisible(false);
            chkRelleno.setVisible(false);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);            
        }
        private void cargarTipoRectangulo(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(false);
            cmbAlineacionH.setVisible(false);
            cmbAlineacionV.setVisible(false);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(true);
            txtAncho.setVisible(true);
            txtAlto.setVisible(true);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(false);
            txtTexto.setVisible(false);
            
            lbl9.setVisible(false);
            txtOrigen.setVisible(false);
            btn1.setVisible(false);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(false);
            txtFuente.setVisible(false);
            btn2.setVisible(false);
            
            lbl14.setVisible(false);
            txtEspesor.setVisible(false);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(true);
            txtBordeEspesor.setVisible(true);
            
            lbl15.setVisible(false);
            txtColor.setVisible(false);
            btn3.setVisible(false);
            
            lbl19.setVisible(true);
            txtBordeColor.setVisible(true);
            btn4.setVisible(true);
            
            lbl21.setVisible(true);
            txtRellenoColor.setVisible(true);
            btn5.setVisible(true);

            lbl17.setVisible(true);
            chkBorde.setVisible(true);
            
            lbl20.setVisible(true);
            chkRelleno.setVisible(true);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);
        }
        private void cargarTipoCirculo(){
            cmbTipo.setEnabled(true);
            
            lbl12.setVisible(false);
            cmbTipoOrigen.setVisible(false);
            
            lbl22.setVisible(false);
            cmbAlineacionH.setVisible(false);
            cmbAlineacionV.setVisible(false);

            lbl2.setVisible(true);
            lbl3.setVisible(true);
            lbl4.setVisible(true);
            txtX.setVisible(true);
            txtY.setVisible(true);
            
            lbl5.setVisible(true);
            lbl6.setVisible(true);
            lbl7.setVisible(false);
            txtAncho.setVisible(true);
            txtAlto.setVisible(false);
            
            lbl11.setVisible(false);
            txtXT.setVisible(false);
            txtYT.setVisible(false);
            
            lbl8.setVisible(false);
            txtTexto.setVisible(false);
            
            lbl9.setVisible(false);
            txtOrigen.setVisible(false);
            btn1.setVisible(false);
            
            lbl10.setVisible(false);
            txtColumna.setVisible(false);
            
            lbl13.setVisible(false);
            txtFuente.setVisible(false);
            btn2.setVisible(false);
            
            lbl14.setVisible(true);
            txtEspesor.setVisible(true);
            
            lbl16.setVisible(true);
            txtRotacion.setVisible(true);
            
            lbl18.setVisible(false);
            txtBordeEspesor.setVisible(false);
            
            lbl15.setVisible(true);
            txtColor.setVisible(true);
            btn3.setVisible(true);
            
            lbl19.setVisible(false);
            txtBordeColor.setVisible(false);
            btn4.setVisible(false);
            
            lbl21.setVisible(true);
            txtRellenoColor.setVisible(true);
            btn5.setVisible(true);

            lbl17.setVisible(false);
            chkBorde.setVisible(false);
            
            lbl20.setVisible(true);
            chkRelleno.setVisible(true);
            
            lbl23.setVisible(false);
            txtRepeticiones.setVisible(false);
            lblTileable.setVisible(false);
            chkTileable.setVisible(false);
        }
    
    //FUNCIONES LISTENER
        private void inicializarListener(){
            cmbFormato.addActionListener(inicializarListenerFormato());
            cmbOrientacion.addActionListener(inicializarListenerOrientacion());
            
            txtMargenX.addKeyListener(inicializarListenerMargenX());
            txtMargenX.addFocusListener(inicializarFocusMargenX());
            txtMargenY.addKeyListener(inicializarListenerMargenY());
            txtMargenY.addFocusListener(inicializarFocusMargenY());
            txtZoom.addKeyListener(inicializarListenerZoom());
            txtZoom.addFocusListener(inicializarFocusZoom());
            
            lista.addMouseListener(inicializarListenerLista());
            cmbLayer.addActionListener(inicializarListenerLayer());
            btnLayer.addActionListener(inicializarListenerLayers());
            btnNuevo.addActionListener(inicializarListenerNuevo());
            btnDuplicar.addActionListener(inicializarListenerDuplicar());
            btnEliminar.addActionListener(inicializarListenerEliminar());
            btnSubir.addActionListener(inicializarListenerSubir());
            btnBajar.addActionListener(inicializarListenerBajar());
            btnMover.addActionListener(inicializarListenerMover());

            cmbTipo.addActionListener(inicializarListenerTipo());
            cmbAlineacionH.addActionListener(inicializarListenerAlineacionH());
            cmbAlineacionV.addActionListener(inicializarListenerAlineacionV());
            cmbTipoOrigen.addActionListener(inicializarListenerTipoOrigen());
            txtX.addKeyListener(inicializarListenerTextoX());
            txtX.addFocusListener(inicializarFocusTextoX());
            txtXT.addKeyListener(inicializarListenerTextoXT());
            txtXT.addFocusListener(inicializarFocusTextoXT());
            txtY.addKeyListener(inicializarListenerTextoY());
            txtY.addFocusListener(inicializarFocusTextoY());
            txtYT.addKeyListener(inicializarListenerTextoYT());
            txtYT.addFocusListener(inicializarFocusTextoYT());
            txtColumna.addKeyListener(inicializarListenerTextoColumna());
            txtColumna.addFocusListener(inicializarFocusTextoColumna());
            txtAncho.addKeyListener(inicializarListenerTextoAncho());
            txtAncho.addFocusListener(inicializarFocusTextoAncho());
            txtAlto.addKeyListener(inicializarListenerTextoAlto());
            txtAlto.addFocusListener(inicializarFocusTextoAlto());
            txtTexto.addKeyListener(inicializarListenerTextoTexto());
            txtTexto.addFocusListener(inicializarFocusTextoTexto());
            txtOrigen.addKeyListener(inicializarListenerTextoOrigen());
            txtOrigen.addFocusListener(inicializarFocusTextoOrigen());
            txtEspesor.addKeyListener(inicializarListenerTextoEspesor());
            txtEspesor.addFocusListener(inicializarFocusTextoEspesor());
            txtRotacion.addKeyListener(inicializarListenerTextoRotacion());
            txtRotacion.addFocusListener(inicializarFocusTextoRotacion());
            txtBordeEspesor.addKeyListener(inicializarListenerTextoBordeEspesor());
            txtBordeEspesor.addFocusListener(inicializarFocusTextoBordeEspesor());

            chkBorde.addItemListener(inicializarListenerCHKBorde());
            chkRelleno.addItemListener(inicializarListenerCHKRelleno());
            chkTileable.addItemListener(inicializarListenerCHKTileable());

            btn1.addActionListener(inicializarListenerOrigen());
            btn2.addActionListener(inicializarListenerFont());
            btn3.addActionListener(inicializarListenerColor(1));
            btn4.addActionListener(inicializarListenerColor(2));
            btn5.addActionListener(inicializarListenerColor(3));
        }

        private MouseListener inicializarListenerLista(){
            return new MouseListener(){
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                    // TODO Auto-generated method stub
                                if (e.getClickCount()==1){
                                    cmbTipo.setEnabled(true);
                                    if (cmbLayer.getSelectedIndex()>-1 && lista.getSelectedIndex()>-1){
                                        printer.setSelected(printer.getSelectedLayer().getObjeto(lista.getSelectedIndex()));
                                        
                                        mostrarElemento();
                                        visor.repaint();
                                    }
                                }
                            }
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                    // TODO Auto-generated method stub

                            }
                            @Override
                            public void mouseExited(MouseEvent e) {
                                    // TODO Auto-generated method stub

                            }
                            @Override
                            public void mousePressed(MouseEvent e) {
                                    //boton presionado

                            }
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                    //soltar boton
                            }
            };
        }
        private ActionListener inicializarListenerLayer(){
            return (ActionEvent evt) -> {
                layerActual=cmbLayer.getSelectedIndex();
                
                if (layerActual != printer.getSelectedLayerID()){
                    printer.setSelectedLayer(layerActual);
                    if (printer.getSelectedLayer().isVisible()){
                        lista.setEnabled(true);
                    }else{
                        lista.setEnabled(false);
                    }
                }
                
                
                mostrarLista();
            };
        }
        private ActionListener inicializarListenerNuevo(){
            return (ActionEvent evt) -> {
                printer.setSelected(printer.addElemento(cmbLayer.getSelectedIndex()));
                actualizar();
                
                lista.setSelectedIndex(modelo.getSize()-1);
                mostrarElemento();
            };
        }
        private ActionListener inicializarListenerDuplicar(){
            return (ActionEvent evt) -> {
                int numero=lista.getSelectedIndex();
                printer.duplicarElemento(cmbLayer.getSelectedIndex(),numero);
                actualizar();
                lista.setSelectedIndex(modelo.getSize()-1);
                mostrarElemento();
            };
        }
        private ActionListener inicializarListenerEliminar(){
            return (ActionEvent evt) -> {
                int numero=lista.getSelectedIndex();
                if (numero>-1){
                    if (JOptionPane.showConfirmDialog(null,"Esta seguro de Eliminar")==JOptionPane.OK_OPTION){
                        printer.removeElemento(cmbLayer.getSelectedIndex(), numero);
                    }
                }
                actualizar();
            };
        }

        private ActionListener inicializarListenerSubir(){
            return (ActionEvent evt) -> {
                subirObjeto();
            };
        }
        private ActionListener inicializarListenerBajar(){
            return (ActionEvent evt) -> {
                bajarObjeto();
            };
        }
        private ActionListener inicializarListenerMover(){
            return (ActionEvent evt) -> {
                moverA();
            };
        }
        
        private ActionListener inicializarListenerFormato(){
            return (ActionEvent evt) -> {
                if (cmbFormato!=null && printer!=null){
                    if (cmbFormato.getSelectedIndex()>-1){
                        if (!cmbFormato.getSelectedItem().toString().equals(printer.getFormatoPapel())){
                            printer.setFormatoPapel(cmbFormato.getSelectedItem().toString());

                            visor.actualizar();
                        }
                    }
                }
            };
        }
        private ActionListener inicializarListenerOrientacion(){
            return (ActionEvent evt) -> {
                if (cmbOrientacion!=null  && printer!=null){
                    if (cmbOrientacion.getSelectedIndex()!=printer.getOrientacion()){
                        printer.setOrientacion(cmbOrientacion.getSelectedIndex());

                        visor.actualizar();
                    }
                }
            };
        }

        private ActionListener inicializarListenerTipo(){
            return (ActionEvent evt) -> {
                if (cmbTipo!=null && printer.getSelected()!=null){
                    if (cmbTipo.getSelectedIndex()!=printer.getSelected().getTipo() && cmbTipo.getSelectedIndex()>-1){
                        printer.getSelected().setTipo(cmbTipo.getSelectedIndex());
                        String nombre=lista.getSelectedIndex()+ "-" + printer.getSelected().getTipoString();
                        modelo.set(lista.getSelectedIndex(),nombre);
                        
                    }
                    configurarTipo();
                }
            };
        }
        private ActionListener inicializarListenerAlineacionH(){
            return (ActionEvent evt) -> {
                if (cmbAlineacionH!=null  && printer.getSelected()!=null){
                    if (cmbAlineacionH.getSelectedIndex()!=printer.getSelected().getAlineacionHorizontal() && cmbAlineacionH.getSelectedIndex()>-1){
                        printer.getSelected().setAlineacionHorizontal(cmbAlineacionH.getSelectedIndex());
                        visor.repaint();
                    }
                }
            };
        }
        private ActionListener inicializarListenerAlineacionV(){
            return (ActionEvent evt) -> {
                if (cmbAlineacionV!=null  && printer.getSelected()!=null){
                    if (cmbAlineacionV.getSelectedIndex()!=printer.getSelected().getAlineacionVertical() && cmbAlineacionV.getSelectedIndex()>-1){
                        printer.getSelected().setAlineacionVertical(cmbAlineacionV.getSelectedIndex());
                        visor.repaint();
                    }
                }
            };
        }
        private ActionListener inicializarListenerTipoOrigen(){
            return (ActionEvent evt) -> {
                if (cmbTipoOrigen!=null && printer.getSelected()!=null){
                    if (cmbTipoOrigen.getSelectedIndex()!=printer.getSelected().getTipoOrigen() && cmbTipoOrigen.getSelectedIndex()>-1){
                        printer.getSelected().setTipoOrigen(cmbTipoOrigen.getSelectedIndex());
                        visor.repaint();
                    }
                }
            };
        }
        
        private KeyListener inicializarListenerMargenX(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        int a=Integer.valueOf(txtMargenX.getText());
                        printer.setMargenX(a);
                        
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusMargenX(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    int a=Integer.valueOf(txtMargenX.getText());
                    printer.setMargenX(a);
                        
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerMargenY(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        int a=Integer.valueOf(txtMargenY.getText());
                        printer.setMargenY(a);
                        
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusMargenY(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    int a=Integer.valueOf(txtMargenY.getText());
                    printer.setMargenY(a);
                        
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerZoom(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        double a=Integer.valueOf(txtZoom.getText());
                        a= a/100;
                        printer.setZoom(a);
                        
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusZoom(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    double a=Integer.valueOf(txtZoom.getText());
                    a= a/100;
                    printer.setZoom(a);
                        
                    visor.repaint();
                }
            };
        }
        
        private KeyListener inicializarListenerTextoX(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        int a=Integer.valueOf(txtX.getText());
                        printer.getSelected().setPunto(a,printer.getSelected().getPuntoY());
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private KeyListener inicializarListenerTextoXT(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        int a=Integer.valueOf(txtXT.getText());
                        printer.getSelected().setAreaTileable(a,printer.getSelected().getAreaTileableY());
                        txtRepeticiones.setText(String.valueOf(printer.getSelected().getRepeticiones()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private KeyListener inicializarListenerTextoColumna(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setColumna(txtColumna.getText());
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoX(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    int a=Integer.valueOf(txtX.getText());
                    printer.getSelected().setPunto(a,printer.getSelected().getPuntoY());
                    visor.repaint();
                }
            };
        }
        private FocusListener inicializarFocusTextoXT(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    int a=Integer.valueOf(txtXT.getText());
                    printer.getSelected().setAreaTileable(a,printer.getSelected().getAreaTileableY());
                    txtRepeticiones.setText(String.valueOf(printer.getSelected().getRepeticiones()));
                    visor.repaint();
                }
            };
        }
        private FocusListener inicializarFocusTextoColumna(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setColumna(txtColumna.getText());
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoY(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        Point a= printer.getSelected().getPunto();
                        a.y=Integer.valueOf(txtY.getText());
                        printer.getSelected().setPunto(a);
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private KeyListener inicializarListenerTextoYT(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        int a=Integer.valueOf(txtYT.getText());
                        printer.getSelected().setAreaTileable(printer.getSelected().getAreaTileableX(),a);
                        txtRepeticiones.setText(String.valueOf(printer.getSelected().getRepeticiones()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoY(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    Point a= printer.getSelected().getPunto();
                    a.y=Integer.valueOf(txtY.getText());
                    printer.getSelected().setPunto(a);
                    visor.repaint();
                }
            };
        }
        private FocusListener inicializarFocusTextoYT(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    int a=Integer.valueOf(txtYT.getText());
                    printer.getSelected().setAreaTileable(printer.getSelected().getAreaTileableX(),a);
                    txtRepeticiones.setText(String.valueOf(printer.getSelected().getRepeticiones()));
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoAncho(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setAncho(Integer.valueOf(txtAncho.getText()));
                        visor.repaint();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.   
                }
            };
        }
        private FocusListener inicializarFocusTextoAncho(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setAncho(Integer.valueOf(txtAncho.getText()));
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoAlto(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setAlto(Integer.valueOf(txtAlto.getText()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoAlto(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setAlto(Integer.valueOf(txtAlto.getText()));
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoTexto(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setTexto(txtTexto.getText());
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoTexto(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setTexto(txtTexto.getText());
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoOrigen(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setOrigenDatos(txtOrigen.getText());
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoOrigen(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setOrigenDatos(txtOrigen.getText());
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoEspesor(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setEspesor(Integer.valueOf(txtEspesor.getText()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoEspesor(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setEspesor(Integer.valueOf(txtEspesor.getText()));
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoRotacion(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setRotacion(Integer.valueOf(txtRotacion.getText()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoRotacion(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setRotacion(Integer.valueOf(txtRotacion.getText()));
                    visor.repaint();
                }
            };
        }
        private KeyListener inicializarListenerTextoBordeEspesor(){
            return new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        printer.getSelected().setBordeEspesor(Integer.valueOf(txtBordeEspesor.getText()));
                        visor.repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            };
        }
        private FocusListener inicializarFocusTextoBordeEspesor(){
            return new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public void focusLost(FocusEvent fe) {
                    printer.getSelected().setBordeEspesor(Integer.valueOf(txtBordeEspesor.getText()));
                    visor.repaint();
                }
            };
        }
        
        private ItemListener inicializarListenerCHKBorde(){
            return new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (printer.getSelected()!=null){
                        printer.getSelected().setBorde(chkBorde.isSelected());
                    
                        visor.repaint();
                    }
                }
            };
        }
        private ItemListener inicializarListenerCHKRelleno(){
            return (ItemEvent e) -> {
                if (printer.getSelected()!=null){
                    printer.getSelected().setRelleno(chkRelleno.isSelected());
                    visor.repaint();
                }
            };
        }
        private ItemListener inicializarListenerCHKTileable(){
            return (ItemEvent e) -> {
                if (printer.getSelected()!=null){
                    printer.getSelected().setTileable(chkTileable.isSelected());
                
                    visor.repaint();
                }
            };
        }

        private ActionListener inicializarListenerOrigen(){
            return (ActionEvent evt) -> {
                //FileNameExtensionFilter filtro=new FileNameExtensionFilter("ARCHIVOS .*","*");
                JFileChooser fc = new JFileChooser(direccion);
                //fc.setFileFilter(filtro);
                int respuesta = fc.showOpenDialog(null);
                //Comprobar si se ha pulsado Aceptar
                if (respuesta == JFileChooser.APPROVE_OPTION)
                {
                    File archivo = fc.getSelectedFile();
                    printer.getSelected().setOrigenDatos(archivo.getAbsolutePath());
                    txtOrigen.setText(printer.getSelected().getOrigenDatos());
                    visor.repaint();
                }
            };
        }
        private ActionListener inicializarListenerColor(int n){
            return (ActionEvent evt) -> {
                if (printer.getSelected()!=null){
                    switch (n){
                        case 1:
                            abrirColores(n,printer.getSelected().getColor());
                            break;
                        case 2:
                            abrirColores(n,printer.getSelected().getBordeColor());
                            break;
                        case 3:
                            abrirColores(n,printer.getSelected().getRellenoColor());
                            break;
                    }

                }
            };
        }
        private ActionListener inicializarListenerFont(){
            return (ActionEvent evt) -> {
                if (printer.getSelected()!=null){
                    abrirFuentes();
                }
            };
        }
        private ActionListener inicializarListenerLayers(){
            return (ActionEvent evt) -> {                
                    abrirCapas();
            };
        }
}
