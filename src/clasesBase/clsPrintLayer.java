/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesBase;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author anhor
 */
public class clsPrintLayer implements Serializable{
    ArrayList<clsPrintElemento> elementos;
    private clsPrintElemento selected;
    private int selectedId;
    private String nombre;
    private int layerID;
    private boolean visible;
    private boolean printable;
    
    
    public clsPrintLayer(){
        layerID=-1;
        nombre ="";
        inicializar();
    }
    public clsPrintLayer(int ID){
        layerID=ID;
        nombre = "";
        inicializar();
    }
    public clsPrintLayer(int ID, String name){
        layerID=ID;
        nombre = name;
        inicializar();
    }
    
    private void inicializar(){
        elementos= new ArrayList();
        selected=null;
        selectedId=-1;
        visible = true;
        printable = true;
    }
    
    //FUNCIONES DE CREACION
        public clsPrintElemento addImagen(int X, int Y, int Ancho, int Alto, String Direccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearImagen(X, Y, Ancho, Alto, Direccion);
            ele.setLayer(layerID);
            
            elementos.add(ele);
            return ele;
        }
        public clsPrintElemento addTitulo(int X, int Y, int Ancho, int Alto, String Texto, Font Fuente, Color Col){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTitulo(X, Y, Ancho, Alto,Texto,Fuente,Col);
            ele.setLayer(layerID);
            
            elementos.add(ele);
            return ele;
        }
        public clsPrintElemento addTexto(int X, int Y, int Ancho, int Alto,int Tipo, String Origen, Font Fuente, Color Col){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTexto(X, Y, Ancho, Alto,Tipo,Origen,Fuente,Col);
            ele.setLayer(layerID);
            elementos.add(ele);
            
            return ele;
        }
        public clsPrintElemento addLinea(int X, int Y, int Ancho, int Alto, int Espesor, Color color){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearLinea(X, Y, Ancho, Alto, Espesor, color);
            ele.setLayer(layerID);
            
            elementos.add(ele);
            return ele;
        }
        public clsPrintElemento addRectangulo(int X, int Y, int Ancho, int Alto, int Espesor, Color color){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearRectangulo(X, Y, Ancho, Alto, Espesor, color);
            ele.setLayer(layerID);

            elementos.add(ele);
            return ele;
        }
        public clsPrintElemento addCirculo(int X, int Y, int Radio, int Espesor, Color color){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearCirculo(X, Y, Radio, Espesor, color);
            ele.setLayer(layerID);

            elementos.add(ele);            
            return ele;
        }
        
    //FUNCIONES DE MOVIMIENTO DE ELEMENTOS
        public void moveUp(int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=null;

            if (numero>0){
                aux1= elementos.get(numero-1);
                aux2= elementos.get(numero);
                elementos.set(numero-1,aux2);
                elementos.set(numero,aux1);
            }
        }
        public void moveDown(int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=null;
            if (numero<elementos.size()-1){
                aux1= elementos.get(numero+1);
                aux2= elementos.get(numero);
                
                elementos.set(numero+1,aux2);
                elementos.set(numero,aux1);
            }
        }
        
    //FUNCIONES DE GESTION DE ELEMENTOS
        public clsPrintElemento addElemento(){
            clsPrintElemento aux1=new clsPrintElemento();
                
            elementos.add(aux1);
            
            return aux1;
        }
        public void addElemento(clsPrintElemento elemento){    
            elementos.add(elemento);
        }
        public void removeElemento(int numero){
            if (numero>-1){
                elementos.remove(numero);
            }
        }
        public void duplicateElemento(int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=new clsPrintElemento();

            if (numero>=0){
                aux1=elementos.get(numero);
            }

                aux2.setAlto(clone(aux1.getAlto()));
                aux2.setAncho(clone(aux1.getAncho()));
                aux2.setAlineacionHorizontal(clone(aux1.getAlineacionHorizontal()));
                aux2.setAlineacionVertical(clone(aux1.getAlineacionVertical()));
                aux2.setAreaTileable(clone(aux1.getAreaTileable()));
                aux2.setTileable(aux1.getTileable());
                aux2.setBorde(aux1.getBorde());
                aux2.setBordeColor(aux1.getBordeColor());
                aux2.setBordeEspesor(clone(aux1.getBordeEspesor()));
                aux2.setColor(aux1.getColor());
                aux2.setColumna(aux1.getColumna());
                aux2.setEspesor(clone(aux1.getEspesor()));
                aux2.setFuente(aux1.getFuente());
                aux2.setOrigenDatos(aux1.getOrigenDatos());
                aux2.setPunto(clone(aux1.getPunto()));
                aux2.setRelleno(aux1.getRelleno());
                aux2.setRellenoColor(aux1.getRellenoColor());
                aux2.setRotacion(clone(clone(aux1.getRotacion())));
                aux2.setLayer(clone(aux1.getSeccion()));
                aux2.setTexto(clone(aux1.getTexto()));
                aux2.setTipo(clone(aux1.getTipo()));
                aux2.setTipoOrigen(clone(aux1.getTipoOrigen()));

            addElemento(aux2);
        }
        
    //FUNCIONES DE COPIA
        public clsPrintElemento makeCopy(clsPrintElemento elemento){
            clsPrintElemento ele=new clsPrintElemento();

            ele.setTipo(elemento.getTipo());
            ele.setPunto(elemento.getPunto());
            ele.setAlto(elemento.getAlto());
            ele.setAncho(elemento.getAncho());
            ele.setTexto(elemento.getTexto());
            ele.setOrigenDatos(elemento.getOrigenDatos());
            ele.setTipoOrigen(elemento.getTipoOrigen());
            ele.setFuente(elemento.getFuente());
            ele.setColor(elemento.getColor());
            ele.setRotacion(elemento.getRotacion());
            ele.setBorde(elemento.getBorde());
            ele.setBordeColor(elemento.getBordeColor());
            ele.setBordeEspesor(elemento.getBordeEspesor());
            ele.setRelleno(elemento.getRelleno());
            ele.setRellenoColor(elemento.getRellenoColor());
            ele.setLayer(elemento.getSeccion());


            return ele;
        }
        private int clone(int n){
            String t = String.valueOf(n);

            return Integer.valueOf(t);
        }
        private String clone(String n){
            String t = new String(n);

            return t;
        }
        private Point clone(Point n){
            Point p= new Point();
            p.x = clone(n.x);
            p.y = clone(n.y);
            return p;
        }
        
    //FUNCIONES DE BUSQUEDA
        public boolean searchElement(clsPrintElemento s){
            boolean valor=false;
            
            //BUSCAR EN CONTENIDO
            for (int i=0;i<elementos.size();i++){
                if (elementos.get(i)==s){
                    valor = true;
                    selectedId=i;
                    break;
                }
            }
            return valor;
        }
        
        public clsPrintElemento searchClick(Point p){
            clsPrintElemento valor=null;
            
            //BUSCAR EN CONTENIDO
            valor = searchInLayer(p,0);
            
            return valor;
        }
        public clsPrintElemento searchClickNext(Point p){
            clsPrintElemento valor=null;
            
            //BUSCAR EN CONTENIDO
            valor = searchInLayer(p,selectedId+1);
            
            return valor;
        }
        public clsPrintElemento searchInLayer( Point p, int inicio){
            clsPrintElemento valor=null;
            selectedId=-1;
            
            //BUSCAR EN CONTENIDO
            
            for (int i=inicio;i<elementos.size();i++){
                if (elementos.get(i).isDown(p)){
                    valor = elementos.get(i);
                    selectedId=i;
                    break;
                }
            }
            
            return valor;
        }
        
    //FUNCIONES GET & SET
        public void setSelected (clsPrintElemento s){
            selected = s;
            searchElement(s);
        }
        public void setSelectedId(int n){
            if (n>-1 && n<elementos.size()){
                selectedId=n;
                selected = elementos.get(n);
            }
        }
        public void setLayerID(int n){
            layerID = n;
            
            for (int i=0;i<elementos.size();i++){
                elementos.get(i).setLayer(n);
            }
        }
        public void setNombre (String name){
            nombre = name;
        }
        public void setVisible (boolean b){
            visible =b;
        }
        public void setPrintable (boolean b){
            printable =b;
        }
        
        public int getSelectedId(){
            return selectedId;
        }
        public clsPrintElemento getSelected(){
            return selected;
        }
        public clsPrintElemento getObjeto(int numero){
            clsPrintElemento aux=null;
            
            aux=elementos.get(numero);
            
            return aux;
        }
        public int getCantidadElementos(){
            return elementos.size();
        }
        public int getLayerID(){
            return layerID;
        }
        public String getNombre(){
            return nombre;
        }
        public boolean isVisible(){
            return visible;
        }
        public boolean isPrintable(){
            return printable;
        }
}
