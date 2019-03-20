/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import clasesBase.clsPrint;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.table.TableColumnModel;
import miButton.miButton;
import miTable.miTable;
import miTable.myDefaultTableModel;



/**
 *
 * @author anhor
 */
public class frmLayers extends JDialog{
    private clsPrint printer;
    private Color colorBorder=new Color(21, 164, 250);
    private Color colorBackgroundSelect=new Color(243, 244, 249);
    private Color colorBackgroundUnselect=new Color(240, 240, 240);
    
    //MENU
    JPanel pnlLayer= new JPanel();
    JPanel pnlMenu= new JPanel();
    
    miButton btnNuevo;
    miButton btnRename;
    miButton btnEliminar;
    miButton btnVisible;
    miButton btnImprimible;
    miButton btnSubir;
    miButton btnBajar;
    miButton btnCerrar;
    
    //TABLA
    JScrollPane pnlTabla;
    myDefaultTableModel model= new myDefaultTableModel();
    miTable tabla;
    
    private String titulos[]= {" Id"," Nombre"," Visible"," Imprimible"," Cant. Elem."};
    private int titulosMax[]= {100,350,150,150,150};
    private int titulosTamaño[]= {40,150,100,100,100};
    
    public frmLayers(clsPrint p){
        super();
        printer=p;
        
        inicializar();
    }
    
    private void inicializar(){
        this.setSize(600, 500);
        this.setTitle("Gestor Capas");
        
        inicializarTabla();
        inicializarBotones();
        
        inicializarUI();
        
        this.add(pnlLayer);
        inicializarListener();
        
        actualizarTabla();
    }
    
    private void inicializarBotones(){
         btnNuevo= new miButton("Nueva");
            btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnNuevo.setIcon(new ImageIcon(getClass().getResource("/Images/circle-plus.png")));
         btnRename= new miButton("Renombrar");
            btnRename.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnRename.setIcon(new ImageIcon(getClass().getResource("/Images/edit.png")));
         btnEliminar= new miButton("Eliminar");
            btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/Images/circle-minus.png")));
         btnVisible= new miButton("Visible");
            btnVisible.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnVisible.setIcon(new ImageIcon(getClass().getResource("/Images/change.png")));
         btnImprimible= new miButton("Imprimible");
            btnImprimible.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnImprimible.setIcon(new ImageIcon(getClass().getResource("/Images/change.png")));
         btnSubir= new miButton("Subir");
            btnSubir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnSubir.setIcon(new ImageIcon(getClass().getResource("/Images/arrow-u.png")));
         btnBajar= new miButton("Bajar");
            btnBajar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnBajar.setIcon(new ImageIcon(getClass().getResource("/Images/arrow-d.png")));
         btnCerrar= new miButton("Cerrar");
            btnCerrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnCerrar.setIcon(new ImageIcon(getClass().getResource("/Images/exit.png")));
         
    }
    private void inicializarTabla(){
        tabla=new miTable();
        tabla.setModel(model);
        
        //establer titulos y tamaños de las columnas
        TableColumnModel col=tabla.getColumnModel();
        
        for (int i=0;i<titulos.length;i++){
            model.addColumn(titulos[i]);
        }
        for (int i=0;i<titulos.length-1;i++){
            col.getColumn(i).setMaxWidth(titulosMax[i]);
            col.getColumn(i).setPreferredWidth(titulosTamaño[i]);
        }
        
        pnlTabla=new JScrollPane(tabla);
    }
    
    private void inicializarUI(){
        inicializarUIMenu();
        
        GroupLayout layout = new GroupLayout(pnlLayer);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlMenu)
                    .addComponent(pnlTabla)
                )
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(pnlMenu)
                    .addComponent(pnlTabla)
                )
                
        );
        pnlLayer.setLayout(layout);
    }
    private void inicializarUIMenu(){
        JSeparator sep1 = new JSeparator();
        JSeparator sep2 = new JSeparator();
        JSeparator sep3 = new JSeparator();
        JSeparator sep4 = new JSeparator();
        GroupLayout layout = new GroupLayout(pnlMenu);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(true);
        
        int at = 140;
        int al = 330;
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnNuevo,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRename,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep1,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep2,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVisible,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimible,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep3,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubir,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBajar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep4,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCerrar,javax.swing.GroupLayout.PREFERRED_SIZE, at, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
            )
        );
        
        int ht=25;
        int hs=5;
        int h=32;
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRename,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep1,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep2,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVisible,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimible,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(hs)
                        .addComponent(sep3,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubir,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBajar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sep4,javax.swing.GroupLayout.PREFERRED_SIZE, hs, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCerrar,javax.swing.GroupLayout.PREFERRED_SIZE, h, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
            )
        );
        pnlMenu.setLayout(layout);
        
    }
    
    //FUNCIONES DE TABLA
        public void actualizarTabla(){
        limpiarTabla(model);
        
        int cantidad = printer.getCantidadLayers();
        
        if (cantidad>0){
            for (int i =0;i<cantidad;i++){
                model.addRow(insertarFila(i));
            }
            
        }else{
            model.addRow(new Object[]{"*"});
        }
    }
        private Object[] insertarFila(int n){
        Object[] o= new Object[]{
            n,
            printer.getLayer(n).getNombre(),
            printer.getLayer(n).isVisible(),
            printer.getLayer(n).isPrintable(),
            printer.getLayer(n).getCantidadElementos()
            };
        
        return o;
    }
        private void limpiarTabla(myDefaultTableModel m){
        int a =m.getRowCount();
        for (int i=0;i<a;i++){
            m.removeRow(0);
        }
    }
    
    //FUNCIONES DE ACCION
        public void accionNuevo(){
            String nombre =JOptionPane.showInputDialog(this, "Nombre Capa :","Layer " + printer.getCantidadLayers());
            if (nombre!=null){
                printer.newLayer(nombre);
                
                actualizarTabla();
            }
        }
        public void accionRenombrar(){
            int i = tabla.getSelectedRow();
            
            String nombre =JOptionPane.showInputDialog(this, "Nuevo nombre Capa '" + printer.getLayer(i).getNombre() + "' :","Layer " + printer.getCantidadLayers());
            if (nombre!=null && i>-1){
                printer.getLayer(i).setNombre(nombre);
                
                actualizarTabla();
            }
        }
        public void accionVisible(){
            int i = tabla.getSelectedRow();
            
            if (i>-1){
                printer.getLayer(i).setVisible(!printer.getLayer(i).isVisible());

                actualizarTabla();
                tabla.setRowSelectionInterval(i, i);
            }
        }
        public void accionImprimible(){
            int i = tabla.getSelectedRow();
            
            if (i>-1){
                printer.getLayer(i).setPrintable(!printer.getLayer(i).isPrintable());

                actualizarTabla();
                tabla.setRowSelectionInterval(i, i);
            }
            
        }
        public void accionEliminar(){
            int i = tabla.getSelectedRow();
            
            if (i>-1){
                int respuesta=JOptionPane.YES_NO_OPTION;
                respuesta =JOptionPane.showConfirmDialog(this, "Esta seguro de querer ELIMINAR la Capa" ,"ELIMINAR CAPA ",respuesta);

                if (respuesta==JOptionPane.YES_OPTION){
                    printer.removeLayer(i);
                    
                    for (int a =0;a<printer.getCantidadLayers();a++){
                        printer.getLayer(a).setLayerID(a);
                    }

                    actualizarTabla();
                }
            }
            
        }
        
        
    //FUNCIONES DE MOVIMIENTO
        private void moveUP(){
            int numero=tabla.getSelectedRow();
            if (numero>0){
                printer.moveUPLayer(numero);
                numero--;
                
                actualizarTabla();
                tabla.setRowSelectionInterval(numero, numero);
            }
        }
        private void moveDown(){
            int numero=tabla.getSelectedRow();
            if (numero<printer.getCantidadLayers()-1){
                printer.moveDownLayer(numero);
                numero++;
                
                actualizarTabla();
                tabla.setRowSelectionInterval(numero, numero);
            }
        }
    
    //FUNCIONES GET & SET
        public void setColorBorder(Color c){
            colorBorder=c;
        }
        public void setPrinter(clsPrint p){
            printer=p;            
        }
        
    //FUNCIONES LISTENER
        private void inicializarListener(){
            btnNuevo.addActionListener(inicializarListenerNuevo());
            btnRename.addActionListener(inicializarListenerRenombrar());
            
            btnEliminar.addActionListener(inicializarListenerEliminar());
            
            btnVisible.addActionListener(inicializarListenerVisible());
            btnImprimible.addActionListener(inicializarListenerImprimible());
            
            btnSubir.addActionListener(inicializarListenerSubir());
            btnBajar.addActionListener(inicializarListenerBajar());
            
            btnCerrar.addActionListener(inicializarListenerCerrar());
        }
        private ActionListener inicializarListenerNuevo(){
            return (ActionEvent evt) -> {
                accionNuevo();
            };
        }
        private ActionListener inicializarListenerRenombrar(){
            return (ActionEvent evt) -> {
                accionRenombrar();
            };
        }
        private ActionListener inicializarListenerEliminar(){
            return (ActionEvent evt) -> {
                accionEliminar();
            };
        }
        private ActionListener inicializarListenerVisible(){
            return (ActionEvent evt) -> {
                accionVisible();
            };
        }
        private ActionListener inicializarListenerImprimible(){
            return (ActionEvent evt) -> {
                accionImprimible();
            };
        }
        private ActionListener inicializarListenerSubir(){
            return (ActionEvent evt) -> {
                moveUP();
            };
        }
        private ActionListener inicializarListenerBajar(){
            return (ActionEvent evt) -> {
                moveDown();
            };
        }
        private ActionListener inicializarListenerCerrar(){
            return (ActionEvent evt) -> {
                this.dispose();
            };
        }
}
