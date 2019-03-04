/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Print;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author toni
 */
public class clsPrintElemento implements Serializable {
    private int tipo; //0-vacio, 1-imagen, 2-Titulo, 3-Campo texto, 4-linea, 5-Rectangulo, 6-circulo    
    private Point punto; //posicion
    private Point areaTileable; //area de repeticion
    private int ancho;
    private int alto;
    private String texto;
    private String origenDatos; //origen del contenido de la base de datos
    private String Columna; //columna a mostrar de la tabla
    private int tipoOrigen; //0-string, 1-int, 2-Double, 3-Date, 4-Boolean, 5-ID
    private Font fuente;
    private int espesor;
    private Color color;
    private int rotacion;
    private boolean borde;
    private Color bordeColor;
    private int bordeEspesor;
    private boolean relleno;
    private Color rellenoColor;
    private int seccion; //0-encabezado , 1-pie, 2-contenido
    private int alineacionVertical; //0-central , 1-izquierda, 2-derecha
    private int alineacionHorizontal; //0-central , 1-arriba, 2-abajo
    private int repeticiones;
    private boolean tileable;
    private int posicion;
    
    //CONSTRUCTOR
    public clsPrintElemento(){
        inicializar();
    }
    
    //FUNCIONES DE INICIALIZACION
        private void inicializar(){
            setTipo(0);
            setPunto(new Point());
            setAreaTileable(new Point());
            setAncho(0);
            setAlto(0);
            setTexto("");
            setOrigenDatos("");
            setFuente(new Font("Aria",Font.BOLD,8));
            setEspesor(1);
            setColor(Color.black);
            setRotacion(0);
            setBorde(false);
            setBordeColor(Color.black);
            setBordeEspesor(1);
            setRelleno(false);
            setRellenoColor(Color.black);
            setSeccion(2);
            alineacionVertical=1;
            alineacionHorizontal=1;
            repeticiones=0;
            posicion=0;
        }

    
    //FUNCIONES GET & SET
        public Point getPunto() {
            return punto;
        }
        public int getPuntoX() {
            return punto.x;
        }
        public int getPuntoY() {
            return punto.y;
        }    
        public int getAncho() {
            return ancho;
        }
        public int getAlto() {
            return alto;
        }
        public String getTexto() {
            return texto;
        }
        public String getOrigenDatos() {
            return origenDatos;
        }
        public Font getFuente() {
            return fuente;
        }
        public int getTipo() {
            return tipo;
        }
        public String getTipoString(){
            //0-vacio, 1-imagen, 2-Titulo, 3-Campo texto, 4-linea, 5-Rectangulo, 6-circulo 
            String nombre="";
            switch (tipo){
                case 0:
                    nombre="Vacio";
                    break;
                case 1:
                    nombre="Imagen";
                    break;
                case 2:
                    nombre="Titulo";
                    break;
                case 3:
                    nombre="Campo Texto";
                    break;
                case 4:
                    nombre="Linea";
                    break;
                case 5:
                    nombre="Rectangulo";
                    break;
                case 6:
                    nombre="Circulo";
                    break;
            }

            return nombre;
        }
        public int getEspesor() {
            return espesor;
        }
        public float getEspesorF() {
            float a = (float)espesor;
            return a;
        }    
        public Color getColor() {
            return color;
        }
        public int getTipoOrigen() {
            return tipoOrigen;
        }
        public int getRotacion() {
            return rotacion;
        }
        public String getColumna() {
            return Columna;
        }
        public boolean getBorde() {
            return borde;
        }
        public Color getBordeColor() {
            return bordeColor;
        }
        public int getBordeEspesor() {
            return bordeEspesor;
        }
        public float getBordeEspesorF() {
            float a = (float)bordeEspesor;
            return a;
        }
        public boolean getRelleno() {
            return relleno;
        }
        public Color getRellenoColor() {
            return rellenoColor;
        }
        public int getSeccion() {
            return seccion;
        }
        public Point getAreaTileable() {
            return areaTileable;
        }
        public int getAreaTileableX() {
            return areaTileable.x;
        }
        public int getAreaTileableY() {
            return areaTileable.y;
        }
        public int getAlineacionVertical() {
            return alineacionVertical;
        }
        public int getAlineacionHorizontal() {
            return alineacionHorizontal;
        }
        public int getRepeticiones() {
            return repeticiones;
        }
        public int getPosicion() {
            return posicion;
        }
        public boolean getTileable(){
            return tileable;
        }

        public void setPunto(Point punto) {
            this.punto = punto;
        }
        public void setPunto(int x, int y) {
            this.punto.x=x;
            this.punto.y=y;
        }
        public void setAncho(int ancho) {
            this.ancho = ancho;
            this.areaTileable.x=ancho;
        }
        public void setAlto(int alto) {
            this.alto = alto;
            if (getAreaTileableY()>0){
                setRepeticiones((int)(this.getAreaTileableY()/this.getAlto()));
            }
        }
        public void setTexto(String texto) {
            this.texto = texto;
        }
        public void setOrigenDatos(String origenDatos) {
            this.origenDatos = origenDatos;
        }
        public void setFuente(Font fuente) {
            this.fuente = fuente;
        }
        public void setFuente(String Nombre, int Tipo, int Tamaño) {
            this.fuente = new Font(Nombre,Tipo, Tamaño);
        }
        public void setTipo(int tipo) {
            this.tipo = tipo;
        }
        public void setEspesor(int espesor) {
            this.espesor = espesor;
        }
        public void setColor(Color color) {
            this.color = color;
        }
        public void setTipoOrigen(int tipoOrigen) {
            //0-string, 1-int, 2-Double, 3-Date, 4 Boolean
            this.tipoOrigen = tipoOrigen;
        }
        public void setRotacion(int rotacion) {
            this.rotacion = rotacion;
        }
        public void setBorde(boolean borde) {
            this.borde = borde;
        }
        public void setBordeColor(Color bordeColor) {
            this.bordeColor = bordeColor;
        }
        public void setBordeEspesor(int bordeEspesor) {
            this.bordeEspesor = bordeEspesor;
        }
        public void setRellenoColor(Color rellenoColor) {
            this.rellenoColor = rellenoColor;
        }
        public void setSeccion(int seccion) {
            this.seccion = seccion;
        }
        public void setColumna(String Columna) {
            this.Columna = Columna;
        }
        public void setRelleno(boolean relleno) {
            this.relleno = relleno;
        }
        public void setRepeticiones(int repeticiones) {
            this.repeticiones = repeticiones;
        }
        public void setPosicion(int posicion) {
            this.posicion = posicion;
        }
        public void setAlineacionHorizontal(int alineacionHorizontal) {
            this.alineacionHorizontal = alineacionHorizontal;
        }
        public void setAlineacionVertical(int alineacionVertical) {
            this.alineacionVertical = alineacionVertical;
        }
        public void setAreaTileable(Point p) {
            areaTileable=p;

            //calcula las repeticiones disponibles de este objeto
            if (areaTileable.y>0 && alto>0){
                setRepeticiones((int)alto/areaTileable.y);
            }
        }
        public void setAreaTileable(int x,int y) {
            areaTileable.x=x;
            areaTileable.y=y;

            //calcula las repeticiones disponibles de este objeto
            if (areaTileable.y>0 && alto>0){
                setRepeticiones((int)alto/y);
            }
        }
        public void setTileable(boolean b){
            tileable= b;
        }
    
    //FUNCIONES DE CREACION
        public void crearImagen(int X, int Y, int Ancho, int Alto, String Direccion){
            setTipo(1);
            setPunto(X,Y);
            setAncho(Ancho);
            setAlto(Alto);
            setOrigenDatos(Direccion);
        }
        public void crearTitulo(int X, int Y, int Ancho, int Alto, String Texto, Font Fuente, Color col){
            setTipo(2);
            setPunto(X,Y);
            setAncho(Ancho);
            setAlto(Alto);
            setTexto(Texto);
            setFuente(Fuente);
            setColor(col);
        }
        public void crearTexto(int X, int Y, int Ancho, int Alto,int Tipo, String Origen, Font Fuente, Color col){
            setTipo(3);
            setPunto(X,Y);
            setAncho(Ancho);
            setAlto(Alto);
            setTipoOrigen(Tipo);
            setOrigenDatos(Origen);
            setFuente(Fuente);
            setColor(col);
        }
        public void crearLinea(int X, int Y, int X2, int Y2, int Espesor, Color col){
            setTipo(4);
            setPunto(X,Y);
            setAncho(X2);
            setAlto(Y2);
            setEspesor(Espesor);
            setColor(col);
        }
        public void crearRectangulo(int X, int Y, int Ancho, int Alto, int Espesor, Color col){
            setTipo(5);
            setPunto(X,Y);
            setAncho(Ancho);
            setAlto(Alto);
            setEspesor(Espesor);
            setColor(col);
        }
        public void crearCirculo(int X, int Y, int Radio, int Espesor, Color col){
            setTipo(6);
            setPunto(X,Y);
            setAncho(Radio);
            setEspesor(Espesor);
            setColor(col);
        }
        
    //FUNCIONES VARIAS
        public boolean isDown(Point p){
            boolean valor=false;
            if (tipo==4){
                //TIPO LINEA
                int juego=2;
                if ((p.x >=(punto.x-juego) && p.x<=(ancho+juego))&& (p.y >=(punto.y-juego) && p.y<=(alto+juego))){
                    valor=true;
                }
            }else if(tipo==6){
                //TIPO CIRCULO
                if ((p.x >=punto.x && p.x<=punto.x+ancho)&& (p.y >=punto.y && p.y<=punto.y+ancho)){
                    valor=true;
                }
            }else{
                //RESTO
                if ((p.x >=punto.x && p.x<=punto.x+ancho)&& (p.y >=punto.y && p.y<=punto.y+alto)){
                    valor=true;
                }
            }
            
            return valor;
        }
}
