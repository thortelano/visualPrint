/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Print;

import clasesBase.clsPrint;
import editor.frmEditor;
import H2.clsH2;
import importExport.clsImportExportV1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import miButton.miButton;
import miComboBox.miComboBox;


/**
 *
 * @author toni
 */
public class frmPrint extends JDialog {
    //CONSTANTES
    public static final int PRINT_MODE = 0;
    public static final int EDIT_MODE = 1;
    
    //BASE DE DATOS
    private clsH2 DB;
    
    //VARIABLES GENERALES
    private String fileName;
    private String filePrinter= "";
    private String defaultDir="";
    private int mode=0;
    private int margenX=30;
    private int margenY=30;
    
    //OBJETOS PARA LA GESTION
    private clsPrint printer = new clsPrint();
    private clsVisor visor;
    private frmEditor editor;
    
    //COLORES DEL SISTEMA
    private Color colorBorder=new Color(21, 164, 250);
    private Color colorBackgroundSelect=new Color(243, 244, 249);
    private Color colorBackgroundUnSelect=new Color(240, 240, 240);
    
    //OBJETOS DEL MENU DESPLEGABLE
    JMenuBar menuB = new JMenuBar();
    JMenu menuArchivo = new JMenu("Archivo");
    JMenu menuControl = new JMenu("Control");
    JMenu menuZoom = new JMenu("Zoom");
    JMenu menuHerramientas = new JMenu("Herramientas");
    JMenu menuAyuda = new JMenu("Ayuda");
    
    JMenuItem menuNuevo = new JMenuItem("Nuevo");
    JMenuItem menuAbrir= new JMenuItem ("Abrir");
    JMenuItem menuGuardar= new JMenuItem ("Guardar");
    JMenuItem menuImprimir= new JMenuItem ("Imprimir");
    JMenu menuImport= new JMenu ("Import");
    JMenu menuExport= new JMenu ("Export");
    JMenuItem menuImportV1= new JMenuItem ("Import V1");
    JMenuItem menuExportV1= new JMenuItem ("Export V1");
    JMenuItem menuSalir= new JMenuItem ("Cerrar");
    
    JMenuItem menuPaginaPri= new JMenuItem ("<< - Pagina Primera");
    JMenuItem menuPaginaAnt= new JMenuItem ("< - Pagina Anterior");
    JMenuItem menuPaginaSig= new JMenuItem ("> - Pagina Siguiente");
    JMenuItem menuPaginaUlt= new JMenuItem (">> - Pagina Ultima");
    
    JMenuItem menuZoomMas= new JMenuItem ("+ Zoom");
    JMenuItem menuZoomMenos= new JMenuItem ("- Zoom");
    
    JMenuItem menuEditObjets= new JMenuItem ("Editor de Objetos");
    
    //OBJETOS DEL MENU ACCESOS RAPIDOS
    JPanel pnlBotones=new JPanel();
    
    miButton btnImprimir = new miButton();
    miButton btnSeparator2 = new miButton();
    miButton btnSeparator1 = new miButton();
    miButton btnPageDown = new miButton();
    miButton btnPageUp = new miButton();
    miButton btnZoomIn = new miButton();
    miButton btnZoomOut = new miButton();
    
    JComboBox cmbZoom;
    JComboBox cmbPagina;
    
    //CONTRUCTOR
        public frmPrint(){
            super();

            Inicializar();
        }
        public frmPrint(JFrame f){
            super(f);

            Inicializar();
        }
        public frmPrint(JDialog f){
            super(f);


            Inicializar();
        }
    
    //FUNCIONES DE INICIALIZACION
        private void Inicializar(){
            //Accion de cerrar ventana termina la aplicacion
            this.setModal(true);
            this.addWindowListener(new WindowAdapter(){@Override
            public void windowClosing(WindowEvent evt){
                editor.dispose();
                dispose();
            }});

            this.setSize(800, 1000);
            this.setTitle("Visor de Impresion");
            this.setLayout(new BorderLayout());
            this.setLocationRelativeTo(null);
            
            newFile();

            inicializarApariencia();
            inicializarMenuMode();
            inicializarCombo();
            inicializarVisor();
            inicializarEditor();
            inicializarBotones();
            inicializarListeners();

            inicializarUI();
            
            cargarPaginas(cmbPagina);
            cargarZoom(cmbZoom);
        }
        private void inicializarApariencia(){
            //apariencia de la aplicacion
            try {
                // TODO code application logic here
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(Asyar2014.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                //Logger.getLogger(Asyar2014.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                //Logger.getLogger(Asyar2014.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                //Logger.getLogger(Asyar2014.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private void inicializarCombo(){
            cmbZoom=addCombo();
            cmbPagina=addCombo();
        }
        private void inicializarBotones(){
            Dimension d = new Dimension(50,50);
            Dimension d1 = new Dimension(80,40);

            
            btnImprimir.setIcon(new ImageIcon(this.getClass().getResource("/images/print.png")));
            btnImprimir.setPressedIcon(new ImageIcon(this.getClass().getResource("/images/Lprint.png")));
            btnImprimir.setRolloverIcon(new ImageIcon( this.getClass().getResource("/images/Lprint.png")));
            btnImprimir.setPreferredSize(d);

            btnSeparator1.setIcon(new ImageIcon( this.getClass().getResource("/images/separator.png")));
            btnSeparator1.setEnabled(false);
            btnSeparator1.setPreferredSize(d);

            btnPageDown.setIcon(new ImageIcon( this.getClass().getResource("/images/pageDown.png")));
            btnPageDown.setPressedIcon(new ImageIcon(this.getClass().getResource("/images/LpageDown.png")));
            btnPageDown.setRolloverIcon(new ImageIcon(this.getClass().getResource("/images/LpageDown.png")));
            btnPageDown.setPreferredSize(d);

            btnPageUp.setIcon(new ImageIcon(this.getClass().getResource("/images/pageUp.png")));
            btnPageUp.setPressedIcon(new ImageIcon(this.getClass().getResource("/images/LpageUp.png")));
            btnPageUp.setRolloverIcon(new ImageIcon(this.getClass().getResource("/images/LpageUp.png")));
            btnPageUp.setPreferredSize(d);

            btnSeparator2.setIcon(new ImageIcon(this.getClass().getResource("/images/separator.png")));
            btnSeparator2.setEnabled(false);
            btnSeparator2.setPreferredSize(d);

            
            btnZoomIn.setIcon(new ImageIcon(this.getClass().getResource("/images/zoomIn.png")));
            btnZoomIn.setPressedIcon(new ImageIcon(this.getClass().getResource("/images/LzoomIn.png")));
            btnZoomIn.setRolloverIcon(new ImageIcon(this.getClass().getResource("/images/LzoomIn.png")));
            btnZoomIn.setPreferredSize(d);

            
            btnZoomOut.setIcon(new ImageIcon(this.getClass().getResource("/images/zoomOut.png")));
            btnZoomOut.setPressedIcon(new ImageIcon(this.getClass().getResource("/images/LzoomOut.png")));
            btnZoomOut.setRolloverIcon(new ImageIcon(this.getClass().getResource("/images/LzoomOut.png")));
            btnZoomOut.setPreferredSize(d);

            
            cmbPagina.setPreferredSize(d1);
            
            cmbZoom.setPreferredSize(d1);        
        }
        private void inicializarVisor(){
            visor = new clsVisor(printer);
            visor.setBackground(Color.LIGHT_GRAY);

            visor.setVisible(true);
        }
        private void inicializarEditor(){
            editor= new frmEditor(this,printer,visor);
        }
        private void inicializarMenuEdit(){
            menuB.removeAll();
            menuImport.removeAll();
            menuExport.removeAll();
            menuArchivo.removeAll();
            menuControl.removeAll();
            menuZoom.removeAll();

            menuImport.add(menuImportV1);
            menuExport.add(menuExportV1);

            menuArchivo.add(menuNuevo);
            menuArchivo.add(menuAbrir);
            menuArchivo.add(menuGuardar);
            menuArchivo.addSeparator();
            menuArchivo.add(menuImport);
            menuArchivo.add(menuExport);
            menuArchivo.addSeparator();
            menuArchivo.add(menuImprimir);
            menuArchivo.addSeparator();
            menuArchivo.add(menuSalir);

            menuControl.add(menuPaginaPri);
            menuControl.addSeparator();
            menuControl.add(menuPaginaAnt);
            menuControl.add(menuPaginaSig);
            menuControl.addSeparator();
            menuControl.add(menuPaginaUlt);

            menuZoom.add(menuZoomMas);
            menuZoom.add(menuZoomMenos);

            menuHerramientas.add(menuEditObjets);

            menuB.add(menuArchivo);
            menuB.add(menuControl);
            menuB.add(menuZoom);
            menuB.add(menuHerramientas);
            menuB.add(menuAyuda);
        }
        private void inicializarMenuPrint(){
            menuB.removeAll();
            menuImport.removeAll();
            menuExport.removeAll();
            menuArchivo.removeAll();
            menuControl.removeAll();
            menuZoom.removeAll();

            menuImport.add(menuImportV1);
            menuExport.add(menuExportV1);

            //menuArchivo.add(menuNuevo);
            //menuArchivo.add(menuAbrir);
            //menuArchivo.add(menuGuardar);
            //menuArchivo.addSeparator();
            //menuArchivo.add(menuImport);
            //menuArchivo.add(menuExport);
            //menuArchivo.addSeparator();
            menuArchivo.add(menuImprimir);
            menuArchivo.addSeparator();
            menuArchivo.add(menuSalir);

            menuControl.add(menuPaginaPri);
            menuControl.addSeparator();
            menuControl.add(menuPaginaAnt);
            menuControl.add(menuPaginaSig);
            menuControl.addSeparator();
            menuControl.add(menuPaginaUlt);

            menuZoom.add(menuZoomMas);
            menuZoom.add(menuZoomMenos);

            menuHerramientas.add(menuEditObjets);

            menuB.add(menuArchivo);
            menuB.add(menuControl);
            menuB.add(menuZoom);
            //menuB.add(menuHerramientas);
            menuB.add(menuAyuda);
        }
        private void inicializarMenuMode(){
            if (mode==PRINT_MODE){
                inicializarMenuPrint();
            }else if(mode==EDIT_MODE){
                inicializarMenuEdit();
            }
        }

        private void inicializarUI(){
            inicializarUI_MenuAR();

            this.setJMenuBar(menuB);
            this.add(pnlBotones,BorderLayout.NORTH);
            this.add(visor, BorderLayout.CENTER);
        }
        private void inicializarUI_MenuAR(){
            pnlBotones.add(btnImprimir);
            pnlBotones.add(btnSeparator1);
            pnlBotones.add(btnPageDown);
            pnlBotones.add(cmbPagina);
            pnlBotones.add(btnPageUp);
            pnlBotones.add(btnSeparator2);
            pnlBotones.add(btnZoomOut);
            pnlBotones.add(btnZoomIn);
            pnlBotones.add(cmbZoom);
        }
    
    //FUNCIONES VARIAS
        private miComboBox addCombo(){
            miComboBox t = new miComboBox(colorBorder,colorBackgroundSelect,colorBackgroundUnSelect);

            return t;
        }
        private void mostrarNombre(){
            this.setTitle("Visor de Impresion"  + " - " + fileName);
        }
        
    //FUNCIONES CON ARCHIVOS
        private void newFile(){
            fileName="default.txt";
            filePrinter="";
            printer= new clsPrint();
            printer.setMargenX(margenX);
            printer.setMargenY(margenY);

            if (visor!=null)visor.setPrint(printer);
            if (editor!=null){
                editor.setPrinter(printer);
                editor.limpiarLista();
                editor.limpiarElemento();
                editor.mostrarPrinter();
            }
            
            mostrarNombre();
        }
        private void openFile(){
            //FileNameExtensionFilter filtro=new FileNameExtensionFilter("ARCHIVOS .*","*");
                JFileChooser fc = new JFileChooser(defaultDir);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Impresiones .TXT", "txt");
                fc.setFileFilter(filter);
                fc.setSelectedFile(new File(fileName));

                int respuesta = fc.showOpenDialog(null);

                //Comprobar si se ha pulsado Aceptar
                if (respuesta == JFileChooser.APPROVE_OPTION)
                {
                    File archivo = fc.getSelectedFile();
                    filePrinter=archivo.getAbsolutePath();
                    fileName = archivo.getName();
                    
                    printer.setMode(mode);
                    printer.abrirArchivo(filePrinter);
                    vistaPreliminar();
                    
                    mostrarNombre();
                    if (editor.isEnabled()) {
                        editor.mostrarPrinter();
                        editor.mostrarLista();
                    }
                }
        }
        private void safeFile(){
                JFileChooser fc = new JFileChooser(defaultDir);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Impresiones .TXT", "txt");
                fc.setFileFilter(filter);
                fc.setSelectedFile(new File(fileName));
                
                int respuesta = fc.showSaveDialog(null);
                //Comprobar si se ha pulsado Aceptar
                if (respuesta == JFileChooser.APPROVE_OPTION){
                    File archivo = fc.getSelectedFile();
                    filePrinter=archivo.getAbsolutePath();
                    fileName = archivo.getName();
                    
                    printer.setFileDir(filePrinter);
                    printer.setFileName(fileName);
                    printer.guardarArchivo(filePrinter);
                
                    mostrarNombre();
                    editor.mostrarPrinter();
                }            
        }
        private void printFile(){
            PrinterJob pj= PrinterJob.getPrinterJob();
            
            if (pj.printDialog()){
                Book book = new Book();
                book.append(printer, printer.getPageFormat(),printer.getPaginas());

                try {
                    pj.setPrintable(printer);
                    pj.setPageable(book);
                    pj.print();
                } catch (PrinterException ex) {
                    //Logger.getLogger(frmPrint.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error");
                }
            }
        }
        
    //METODOS IMPORT & EXPORT
        private void importV1(){
            JFileChooser fc = new JFileChooser(defaultDir);
            int respuesta = fc.showOpenDialog(null);

            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                    newFile();

                    clsImportExportV1 importar = new clsImportExportV1();
                    File archivo = fc.getSelectedFile();
                    filePrinter=archivo.getAbsolutePath();
                    fileName = archivo.getName();

                    importar.setPrint(printer);
                    importar.setArchivo(archivo.getAbsolutePath());

                    importar.importV1();
                    
                    printer.setMode(mode);
                    visor.repaint();
                    
                    mostrarNombre();
                    if (editor.isVisible()) {
                        editor.mostrarPrinter();
                        editor.mostrarLista();
                    }
            }
        }
        private void exportV1(){
            if (printer.getCantidadElementos()>0 || printer.getCantidadElementosEncabezado()>0 || printer.getCantidadElementosPie()>0){
                JFileChooser fc = new JFileChooser(defaultDir);
                fc.setSelectedFile(new File(fileName));

                int respuesta = fc.showSaveDialog(null);

                //Comprobar si se ha pulsado Aceptar
                if (respuesta == JFileChooser.APPROVE_OPTION){
                    clsImportExportV1 export = new clsImportExportV1();
                    File archivo = fc.getSelectedFile();
                    filePrinter=archivo.getAbsolutePath();
                    fileName = archivo.getName();

                    export.setPrint(printer);
                    export.setArchivo(archivo.getAbsolutePath());

                    export.exportV1();
                }
            }
        }
    
    //FUNCIONES DE CARGA
        private void cargarZoom(JComboBox b){
            for (int i=10;i<=100;i=i+10){
                b.addItem(i);
            }
            b.addItem(200);
            b.addItem(300);
            b.addItem(500);

            b.setSelectedItem(100);
        }
        private void cargarPaginas(JComboBox b){
            b.removeAllItems();
            for (int i=1;i<=printer.getPaginas();i++){
                b.addItem(i);
            }
        }
    
    //METODOS GRAFICOS E IMPRESION
        private void mostrarVentanaEditor(){
            //edit.setLocationRelativeTo(this);
            if (mode==EDIT_MODE){
                Point p= this.getLocation();

                printer.setMode(EDIT_MODE);
                printer.setDB(DB);

                editor.setDireccion(defaultDir);            
                editor.setLocation(p.x+this.getWidth(), p.y);
                editor.setModal(false);
                editor.setPrinter(printer);
                editor.setVisible(true);

            }else if (mode==PRINT_MODE){
                printer.setMode(PRINT_MODE);
                editor.dispose();
            }
        }
        private void vistaPreliminar(){
            visor.actualizar();
        }
        private void limpiarVisor(){
            Graphics a = visor.getGraphics();
            a.clearRect(0, 0, visor.getWidth(),visor.getHeight());
        }
        private void moveToPage(int n){
            printer.setPaginaActual(n);

            cmbPagina.setSelectedItem(String.valueOf(printer.getPaginaActual()));
            vistaPreliminar();
        }
        
    //FUNCIONES DEL VISOR
        public void zoomIN(){
                double a =printer.getZoom() + 0.1;
                printer.setZoom(a);
                a=a*100;
                cmbZoom.setSelectedItem((int)a);
                
                visor.repaint();
                editor.mostrarPrinter();
        }
        public void zoomOUT(){
                double a =printer.getZoom() - 0.1;
                printer.setZoom(a);
                a=a*100;
                cmbZoom.setSelectedItem((int)a);
                
                visor.repaint();
                editor.mostrarPrinter();
        }
        public void setZoom(String z){
                double a =Double.valueOf(z);
                a=a/100;
                printer.setZoom(a);

                visor.repaint();
                editor.mostrarPrinter();
        }
        
    //FUNCIONES GESTION DE IMPRESIONES DESDE EXTERIOR
        public void abrirImpresion(String archivo,String Origen, String arg[]){
            //File arc = new File(archivo);
            filePrinter=archivo;

            printer.setARG(arg);
            printer.setOrigenDatos(Origen);
            printer.setMode(mode);
            printer.abrirArchivo(archivo);
            
            fileName = printer.getFileName();

            cargarPaginas(cmbPagina);
            mostrarNombre();
        }
        
    //GET Y SET
        public clsH2 getDB() {
            return DB;
        }
        public int getMode(){
            return mode;
        }
        public clsPrint getPrinter(){
            return printer;
        }
        public Color getColorBorder() {
            return colorBorder;
        }
        public Color getColorBackgroundSelect() {
            return colorBackgroundSelect;
        }
        public Color getColorBackgroundUnSelect() {
            return colorBackgroundUnSelect;
        }
        public String getDefaultDir(){
            return defaultDir;
        }
        public int getMargenX(){
            return margenX;
        }
        public int getMargenY(){
            return margenY;
        }

        public void setDB(clsH2 DB) {
            this.DB = DB;
            printer.setDB(DB);
        }
        public void setOrigenDatos(String origen) {
            printer.setOrigenDatos(origen);

            cargarPaginas(cmbPagina);
            editor.actualizar();
        }
        public void setMode(int m){
            mode=m;
            inicializarMenuMode();

            vistaPreliminar();
        }
        public void setARG(String s[]){
            printer.setARG(s);

            cargarPaginas(cmbPagina);
            editor.actualizar();
        }
        public void setColorBorder(Color colorBorder) {
            this.colorBorder = colorBorder;
        }
        public void setColorBackgroundSelect(Color colorBackgroundSelect) {
            this.colorBackgroundSelect = colorBackgroundSelect;
        }
        public void setColorBackgroundUnSelect(Color colorBackgroundUnSelect) {
            this.colorBackgroundUnSelect = colorBackgroundUnSelect;
        }
        public void setColors(Color border, Color bs, Color bu){
            colorBorder=border;
            colorBackgroundSelect=bs;
            colorBackgroundUnSelect=bu;
        }
        public void setDefaultDir(String d){
            defaultDir=d;
        }
        public void setMargenX(int m){
            margenX=m;
        }
        public void setMargenY(int m){
            margenY=m;
        }
    
    //LISTENERS
        private void inicializarListeners(){
            menuNuevo.addActionListener(inicializarListenerNuevo());
            menuAbrir.addActionListener(inicializarListenerAbrir());
            menuGuardar.addActionListener(inicializarListenerGuardar());
            menuImprimir.addActionListener(inicializarListenerImprimir());
            menuSalir.addActionListener(inicializarListenerSalir());

            menuImportV1.addActionListener(inicializarListenerImportV1());
            menuExportV1.addActionListener(inicializarListenerExportV1());

            menuZoomMas.addActionListener(inicializarListenerZoomMas());
            menuZoomMenos.addActionListener(inicializarListenerZoomMenos());

            menuEditObjets.addActionListener(inicializarListenerEditor());
            cmbZoom.addActionListener(inicializarListenerZoom());
            cmbPagina.addActionListener(null);
            
            btnZoomIn.addActionListener(inicializarListenerZoomMas());
            btnZoomOut.addActionListener(inicializarListenerZoomMenos());
            btnPageDown.addActionListener(inicializarListenerPaginaMenos());
            btnPageUp.addActionListener(inicializarListenerPaginaMas());
            btnImprimir.addActionListener(inicializarListenerImprimir());
            
            visor.addMouseListener(inicializarListenerVisor());
        }

        private ActionListener inicializarListenerNuevo(){
            return (ActionEvent evt) -> {
                newFile();
                visor.repaint();
            };
        }
        private ActionListener inicializarListenerAbrir(){
            return (ActionEvent evt) -> {
                openFile();
            };
        }
        private ActionListener inicializarListenerGuardar(){
            return (ActionEvent evt) -> {
                safeFile();
            };
        }
        private ActionListener inicializarListenerImprimir(){
            return (ActionEvent evt) -> {
                printFile();
            };
        }

        private ActionListener inicializarListenerImportV1(){
            return (ActionEvent evt) -> {
                importV1();
            };
        }
        private ActionListener inicializarListenerExportV1(){
            return (ActionEvent evt) -> {
                exportV1();
            };
        }

        private ActionListener inicializarListenerZoomMas(){
            return (ActionEvent evt) -> {
                zoomIN();
            };
        }
        private ActionListener inicializarListenerZoomMenos(){
            return (ActionEvent evt) -> {
                zoomOUT();
            };
        }
        private ActionListener inicializarListenerZoom(){
            return (ActionEvent evt) -> {
                setZoom(cmbZoom.getSelectedItem().toString());
            };
        }
        private ActionListener inicializarListenerSalir(){
            return (ActionEvent evt) -> {
                editor.dispose();
                dispose();
            };
        }
        private ActionListener inicializarListenerEditor(){
            return (ActionEvent evt) -> {
                mostrarVentanaEditor();
            };
        }
        private ActionListener inicializarListenerPaginaMenos(){
            return (ActionEvent evt) -> {
               moveToPage(printer.getPaginaActual()-1);
            };
        }
        private ActionListener inicializarListenerPaginaMas(){
            return (ActionEvent evt) -> {
               moveToPage(printer.getPaginaActual()+1); 
            };
        }
        
        private MouseListener inicializarListenerVisor(){
        return new MouseListener(){
                        @Override
			public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount()==1){
                                //PUNTO CLICK
                                if(editor.isVisible()) {
                                    editor.actualizarElemeto(printer.getSelectedLayer(),printer.getSelectedId());
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
    
    
}
