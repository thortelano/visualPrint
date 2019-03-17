/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesBase;

import H2.clsH2;
import H2.clsH2R;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author toni
 */
public class clsPrint implements Printable, Serializable{
    //CONSTANTES
    public static final int PRINT_MODE = 0;
    public static final int EDIT_MODE = 1;
    
    //VARIABLES IMPRESION
    private Font fuente = new Font("Arial",Font.CENTER_BASELINE, 10);
    private PageFormat pf= new PageFormat();
    private Paper hoja;
    private clsPaperFactory formatos = new clsPaperFactory();
    
    private String formatoPapel;
    private int orientacion;
    private String conexion;
    
    //VARIABLES VISOR
    private int mode=0; //MODO EN QUE SE MUESTRA Y TRABAJA CON LOS OBJETOS
    private String arg[]=null; //VARIABLE DONDE SE GUARDAN LOS PARAMETROS ADJUNTOS
    
    private Point punto0= new Point(); // DONDO ESTA SITUADO EL PUNTO 0,0
    private Point tamaño= new Point(); // TAMAÑO DE LA HOJA EN PIXELES
    
    private int margenX =30;
    private int margenY =30;
    
    private double zoom;
    
    private int paginas;
    private int paginaActual;
    
    private clsPrintElemento selected;
    private clsPrintLayer selectedLayer;
    private int selectedId;
    
    //VARIABLES ARCHIVO
    private String fileName;
    private String fileDir;
    
    //VARIABLE CONEXION DB
    private String origenDatos; //sentencia SQL para acceder a la DB
    private clsH2 DB;
    
    //CAPAS DE IMPRESION
    ArrayList<clsPrintLayer> capas;
    
    
    //ESTILOS DE LINEA
    float dash[] = { 10.0f }; //tamaño de los trazos de linea a trazos
    BasicStroke stroke = new BasicStroke(1.0f); //linea continua
    BasicStroke dashed=new BasicStroke(.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.f,dash,0.0f); // linea a trazos
    
    //CONSTRUCTOR
    public clsPrint(){
        super();
        inicializar();
    }
    
    private void inicializar(){
        formatoPapel="A4";
        orientacion=PageFormat.PORTRAIT;
        conexion="H2";
        
        hoja= formatos.createPaper(formatoPapel);
        
        inicializarMargen();
        pf.setOrientation(orientacion);
        
        punto0.setLocation(50,50);
        
        capas= new ArrayList();
        

        paginas=1;
        paginaActual=1;
        origenDatos="";
        
        zoom=1;
        
        fileName="";
        fileDir="";
        
        selected=null;
        selectedLayer=newLayer("DEFAULT");
        
        selectedId=-1;
    }
    private void inicializarMargen(){
        hoja.setImageableArea(margenX, margenY, hoja.getWidth()-(margenX*2),hoja.getHeight()-(margenY*2));
        pf.setPaper(hoja);
    }
           
    /**
     * A continuación el método "imprimir(String)", el encargado de
     * colocar en el objeto gráfico la cadena que se le pasa como
     * parámetro y se imprime.
     * @param graphics
     * @param pageFormat
     * @param pageIndex
     * @return 
     * @throws java.awt.print.PrinterException
    */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex>=0) {
            //desplaza el punto 0,0 a la posicion punto0
            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate((int)pf.getImageableX(),(int)pf.getImageableY());
            
            for (int i=0;i<capas.size();i++){
                if (capas.get(i).isPrintable()){
                    mostrarLayer(g2,pageIndex,i);
                }
            }
            return (PAGE_EXISTS);
        } else {
            return (NO_SUCH_PAGE);
        }
    }
    public void VistaPreliminar(Graphics ga){
        //muestra la hoja + el contenido
        Graphics2D g2 = (Graphics2D) ga;
        g2.scale(zoom,zoom);
            if (mode==EDIT_MODE){
                mostrarFondoHoja(ga);
                for (int i=0;i<capas.size();i++){
                    if (capas.get(i).isVisible()){
                        mostrarLayerEdit(ga,paginaActual-1, i);
                    }
                }
                
                if (selected != null){
                    mostrarSeleccion(ga,selected);
                }
            }else if (mode==PRINT_MODE){
                mostrarFondoHoja(ga);
                for (int i=0;i<capas.size();i++){
                    if (capas.get(i).isVisible()){
                        mostrarLayer(ga,paginaActual-1,i);
                    }
                }
            }
        
    }
    
    //FUNCIONES GET & SET
        public void setPunto0(Point p){
            punto0=p;
        }
        public void setPunto0(int X, int Y){
            punto0.x=X;
            punto0.y= Y;
        }
        public void setDesplazamiento(Point p){
            punto0.x=punto0.x-p.x;
            punto0.y=punto0.y-p.y;
        }
        public void setDB(clsH2 DB) {
            this.DB = DB;
        }
        public void setOrigenDatos(String s){
            origenDatos=s;
            paginas = calcularPaginas(s);
            paginaActual=1;
        }
        public void setZoom(double d){
            zoom=d;
            if (zoom<0.05) zoom=0.05;
            
        }
        public void setMargenX(int x){
            margenX=x;

            inicializarMargen();
        }
        public void setMargenY(int y){
            margenY=y;

            inicializarMargen();
        }
        public void setMode(int b){
            mode=b;
        }
        public void setARG(String s[]){
            arg=s;
        }
        public void setPaginaActual(int n){
            if (n<=paginas && n>0){
                paginaActual=n;
            }
        }
        public void setSelected (clsPrintElemento s){
            selected = s;
            searchElement(s);
        }
        public void setFormatoPapel(String s){
            formatoPapel=s;
            hoja= formatos.createPaper(s);
            inicializarMargen();
        }
        public void setOrientacion(int h){
            if (h>-1) {
                orientacion=h;
                pf.setOrientation(h);
            }
        }
        public void setFileName(String s){
            fileName=s;
        }
        public void setFileDir(String s){
            fileDir=s;
        }
        public void setSelectedLayer(int n){
            selectedLayer=capas.get(n);
        }
        public void setSelectedLayer(clsPrintLayer l){
            selectedLayer=l;
        }
        public void setSelectedId(int n){
            selectedId=n;
        }
        public void setLayerName(int layer,String n){
            capas.get(layer).setNombre(n);
        }
        
        public Point getPunto0(){
            return punto0;
        }
        public int getCantidadLayers(){
            return capas.size();
        }
        public int getCantidadElementos(){
            return selectedLayer.getCantidadElementos();
        }
        public int getCantidadElementos(int layer){
            return capas.get(layer).getCantidadElementos();
        }
        public double getZoom(){
            return zoom;
        }
        public clsH2 getDB() {
            return DB;
        }
        public int getMargenX(){
            return margenX;
        }
        public int getMargenY(){
            return margenY;
        }
        public int getMode(){
            return mode;
        }
        public String getARG(int n){
            return arg[n];
        }
        public int getPaginas(){
            return paginas;
        }
        public int getPaginaActual(){
            return paginaActual;
        }
        public PageFormat getPageFormat(){
            return pf;
        }
        public String getOrigenDatos(){
            return origenDatos;
        }
        public clsPrintElemento getSelected(){
            return selected;
        }
        public Point getTamaño(){
            return tamaño;
        }
        public String getFileName(){
            return fileName;
        }
        public String getFileDir(){
            return fileDir;
        }
        public Font getFuente(){
            return fuente;
        }
        public int getSelectedLayerID(){
            return selectedLayer.getLayerID();
        }
        public clsPrintLayer getSelectedLayer(){
            return selectedLayer;
        }
        public int getSelectedId(){
            return selectedId;
        }
        public String getFormatoPapel(){
            return formatoPapel;
        }
        public String getConexion(){
            return conexion;
        }
        public int getOrientacion(){
            return orientacion;
        }
        public boolean isVacio(){
            boolean valor = true;
            for (int i =0;i<capas.size();i++){
                if (capas.get(i).getCantidadElementos()>0){
                    valor=false;
                    break;
                }
            }
            
            return valor;
        }
        public clsPrintLayer getLayer(int n){
            return capas.get(n);
        }
    
    // FUNCIONES PARA MOSTRAR LOS OBJETOS
        private void mostrarFondoHoja(Graphics ga){
            //desplaza el punto 0,0 a la posicion punto0
            Graphics2D g2 = (Graphics2D) ga;
            g2.translate((int)punto0.getX(),(int)punto0.getY());

            g2.setColor(Color.white);
            g2.fillRect(0, 0, (int)hoja.getWidth(),(int)hoja.getHeight());
            tamaño.x = (int)hoja.getWidth();
            tamaño.y= (int)hoja.getHeight();

            g2.setStroke(dashed);
            g2.translate((int)hoja.getImageableX(),(int)hoja.getImageableY());
            g2.setColor(Color.lightGray);
            g2.drawRect(0, 0, (int)hoja.getImageableWidth(),(int)hoja.getImageableHeight());
            g2.setStroke(stroke);

            g2.setFont(fuente);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("TAMAÑO HOJA: "+ formatoPapel + " - " + puntosToMM(hoja.getWidth()) + "x" + puntosToMM(hoja.getHeight()), 0, -10);
        }

        private void mostrarLayer(Graphics ga, int pag, int layer){
            clsPrintElemento ele;

            for (int i =0;i<capas.get(layer).getCantidadElementos();i++ ){
                ele=(clsPrintElemento)capas.get(layer).getObjeto(i);

                switch (ele.getTipo()){
                    case 1:
                        mostrarImagen(ga,ele);
                        break;
                    case 2:
                        mostrarTitulo(ga,ele);
                        break;
                    case 3:
                        mostrarTexto(ga,ele,pag);
                        break;
                    case 4:
                        mostrarLinea(ga,ele);
                        break;
                    case 5:
                        mostrarRectangulo(ga,ele);
                        break;
                    case 6:
                        mostrarCirculo(ga,ele);
                        break;
                    default:
                        break;
                }
            }

        }
        private void mostrarLayerEdit(Graphics ga, int pag, int layer){
        clsPrintElemento ele;
        
        for (int i =0;i<capas.get(layer).getCantidadElementos();i++ ){
            ele=(clsPrintElemento)capas.get(layer).getObjeto(i);
            
            switch (ele.getTipo()){
                case 1:
                    mostrarImagen(ga,ele);
                    break;
                case 2:
                    mostrarTitulo(ga,ele);
                    break;
                case 3:
                    mostrarTextoEdit(ga,ele,pag);
                    break;
                case 4:
                    mostrarLinea(ga,ele);
                    break;
                case 5:
                    mostrarRectangulo(ga,ele);
                    break;
                case 6:
                    mostrarCirculo(ga,ele);
                    break;
                default:
                    break;
            }
        }
        
    }
    
        //FUNCIONES EN MODO IMPRESION
        private void mostrarImagen(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            
            try {
                Point p= e.getPunto();
                String appPath = new java.io.File(".").getCanonicalPath();

                if (!e.getOrigenDatos().equals("")){
                    File f=null;
                    Image i=null;
                    
                    String archivo =e.getOrigenDatos();
                    
                    if (archivo.charAt(0)=='/' || archivo.charAt(0)=='\\') {
                        //ORIGEN DIRECCION APARTIR DE LA APLICACION
                        archivo=appPath+archivo;
                        f = new File(archivo);
                        i = ImageIO.read(f);
                    }else if (archivo.indexOf(":\\")==-1 && archivo.indexOf(":/")==-1) {
                        //ORIGEN INTERNO APLICACION
                        if (getClass().getResourceAsStream(archivo)!=null){
                            i =new ImageIcon(getClass().getResource(archivo)).getImage();
                        }
                    }else{
                        //ORIGEN DIRECCION COMPLETA
                        f = new File(archivo);
                        i = ImageIO.read(f);
                    }

                    if (i!=null){
                        //SE HA ENCONTRADO LA IMAGEN
                        ImageIcon ima= new ImageIcon(i);
                        if (e.getRotacion()>0){
                            g2.rotate(Math.toRadians(e.getRotacion()),p.x , p.y);
                        }

                        g2.drawImage(ima.getImage(),p.x ,p.y,e.getAncho(), e.getAlto(),null);
                        if (e.getBorde()==true){
                           mostrarBorde(g2,e);
                        }

                        int an=360-e.getRotacion();
                        g2.rotate(Math.toRadians(an),p.x , p.y);
                    }
                }

            } catch (IOException ex) {
                //Logger.getLogger(clsPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private void mostrarTitulo(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();
            String texto;
            FontMetrics fm= ga.getFontMetrics(e.getFuente());
            int ancho;
            int alto;

            texto=recortarTexto(e.getTexto(),e.getAncho(),ga,e.getFuente());
            ancho=fm.stringWidth(texto);
            alto=fm.getAscent();

                g2.setColor(e.getColor());
                g2.setFont(e.getFuente());
                if (e.getRotacion()>0){
                    g2.rotate(Math.toRadians(e.getRotacion()),p.x , p.y);
                }

                if (alto<e.getAlto()){
                    //si altura de texto es menor que el hueco escribimos
                    Point po=alinear(p,ancho,alto,e.getAncho(),e.getAlto(),e.getAlineacionHorizontal(),e.getAlineacionVertical());
                    g2.drawString(texto,po.x ,po.y);
                }

                //si hay borde
                if (e.getBorde()==true){
                   mostrarBorde(g2,e);
                }

                //si hemos rotado el texto lo dejamos en posicion inicial
                if (e.getRotacion()>0){
                    int an=360-e.getRotacion();
                    g2.rotate(Math.toRadians(an),p.x , p.y);
                }

        }
        private void mostrarTexto(Graphics ga, clsPrintElemento e, int pag){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= new Point();
            String texto="";
            FontMetrics fm= ga.getFontMetrics(e.getFuente());
            int modo=0;   //0 - NO HAY ORIGEN , 1 - ORGEN PARAMETRICO, 2 - ORIGEN DB
            int ancho;
            int alto;
            int posI; //posicion inicio
            int posF; //posicion final
            int can=0; //cantidad de registros de la BD
            String origen="";
            p.x = e.getPuntoX();
            p.y = e.getPuntoY();

            //CARGAMOS EL MODO EN QUE SE OBTIENEN LOS DATOS Y LA CANTIDAD
                if ("".equals(e.getColumna())){
                    //MODEO VALOR PARAMETRICO
                    modo=1;
                    can=1;
                }else{
                    //MODO VALOR DB
                    modo=2;
                    origen=buscarOrigen(e);
                    
                    if (!origen.equals("")){
                        //HAY ORIGEN DE DATOS
                        can=cantidadElementosDB(e, origen);
                    }else{
                        //NO HAY ORIGEN
                        modo=0;
                        can=1;
                    }
                }
            

            //NOS COLOCAMOS EN EL REGISTRO DONDE SE EMPIEZA IMPRIMIR DE LA DB
                posI =((pag)*e.getRepeticiones());
                posF=posI;
                if (can>posI){
                    posF=posI+e.getRepeticiones();
                    if (posF>can){
                        posF=can;
                    }
                }

            //IMPRIMIMOS LOS TEXTOS DE LA DB  O PARAMETROS
            for (int i=posI;i<posF;i++){
                //obtenemos el texto a mostrar segun el modo
                switch (modo){
                    case 0:
                        //NO HAY ORGEN DE DONDE SACAR INFORMACION
                        texto=e.getTexto();
                        break;
                    case 1:
                        //OBTENEMOS LOS DATOS DE UN PARAMETRO
                        texto=insertarValor(e.getOrigenDatos(),e.getTipoOrigen());
                        break;
                    case 2:
                        //OBTENEMOS LOS DATOS DE DB
                        texto=buscarTexto(e,i, origen);
                        break;
                }

                //DIMENSIONAMOS EL TEXTO VISIBLE EN EL ESPACIO DISPONIBLE
                    //RECORTAMOS EL ANCHO DE TEXTO SI NO ENTRA
                    texto=recortarTexto(texto,e.getAncho(),ga,e.getFuente());
                    ancho=fm.stringWidth(texto);
                    alto=fm.getHeight();

                    //COMPROBAMOS QUE ALTO DEL TEXTO ENTRA
                    if (alto<e.getAlto()){
                        int x,y;
                        g2.setColor(e.getColor());
                        g2.setFont(e.getFuente());

                        //ESCRIBIMOS
                        Point po = alinear(p,ancho,alto,e.getAncho(),e.getAlto(),e.getAlineacionHorizontal(),e.getAlineacionVertical());
                        g2.drawString(texto,po.x ,po.y);
                    }

                //DIJAMOS EL BORDE
                    if (e.getBorde()==true){
                        mostrarBorde(g2,e);
                    }

                //MOVEMOS A LA SIGUIENTE POSICION
                    p.y=p.y + e.getAreaTileableY(); 

            }
        }
        private void mostrarLinea(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();

            g2.setColor(e.getColor());
            g2.setStroke(new BasicStroke(e.getEspesorF()));
            if (e.getRotacion()>0){
                g2.rotate(Math.toRadians(e.getRotacion()),p.x , p.y);
            }
            g2.drawLine(p.x ,p.y,e.getAncho(), e.getAlto());
            g2.setStroke(stroke);

            int an=360-e.getRotacion();
            g2.rotate(Math.toRadians(an),p.x , p.y);
        }
        private void mostrarRectangulo(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();

            if (e.getRelleno()==true){
                g2.setColor(e.getRellenoColor());
                g2.fillRect(p.x ,p.y,e.getAncho(), e.getAlto());
            }
            if (e.getBorde()==true){
                mostrarBorde(ga,e);
            }
        }
        private void mostrarBorde(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();

            g2.setColor(e.getBordeColor());
            g2.setStroke(new BasicStroke(e.getBordeEspesorF()));
            g2.drawRect(p.x ,p.y,e.getAncho(), e.getAlto());
            g2.setStroke(stroke);
        }
        private void mostrarCirculo(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();
            if (e.getRelleno()==true){
                g2.setColor(e.getRellenoColor());
                g2.fillArc(p.x, p.y,e.getAncho(), e.getAncho(),0, 360);
            }

            g2.setColor(e.getColor());
            g2.setStroke(new BasicStroke(e.getEspesorF()));
            g2.drawArc(p.x, p.y,e.getAncho(), e.getAncho(),0, 360);
            g2.setStroke(stroke);
        }
        
        //FUNCIONES EN MODO EDICION
        private void mostrarSeleccion(Graphics ga, clsPrintElemento e){
            Graphics2D g2 = (Graphics2D) ga;
            Point p= e.getPunto();


            //MUESTRA RECTANGULO DEL ELEMENTO SELECCIONADO ELEMENTO TIPO RECTANGULO
            Point pI = new Point(p.x-3,p.y-3);
            Point pD = new Point(e.getAncho()+6,e.getAlto()+6);
            
            if (e.getTipo()==4){
                //MODIFICAMOS SELECCION ELEMENTO LINEA
                pD.x =  pD.x-pI.x;
                pD.y =  pD.y-pI.y;
            }else if (e.getTipo()==6){
                //MODIFICAMOS SELECCION ELEMENTO CIRCULO
                pD.y =  pD.x;
            }
            
            Color c = new Color(0,0,255,20);
            g2.setColor(c);
            g2.fillRect(pI.x,pI.y,pD.x, pD.y);
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(pI.x,pI.y,pD.x, pD.y);
            g2.setStroke(stroke);
        }
        private void mostrarTextoEdit(Graphics ga, clsPrintElemento e, int pag){
        Graphics2D g2 = (Graphics2D) ga;
        Point p= new Point();
        String texto="";
        FontMetrics fm= ga.getFontMetrics(e.getFuente());
        
        int ancho;
        int alto;
        
        //CARGAMOS EL PUNTO DE INSERCION
            p.x = e.getPuntoX();
            p.y = e.getPuntoY();
        
        //MUESTRA ZONA DE REPETICION
            g2.setStroke(dashed);
            g2.setColor(Color.lightGray);

            g2.drawRect(p.x, p.y, e.getAreaTileableX(),e.getAreaTileableY());
            g2.setStroke(stroke);
       
        //OBTENEMOS EL TEXTO A MOSTRAR
            texto=e.getTexto();
            
        //DIMENSIONAMOS EL TEXTO VISIBLE EN EL ESPACIO DISPONIBLE
            //RECORTAMOS EL ANCHO DE TEXTO SI NO ENTRA
            texto=recortarTexto(texto,e.getAncho(),ga,e.getFuente());
            ancho=fm.stringWidth(texto);
            alto=fm.getHeight();

            //RECORTAMOS EL ALTO DE TEXTO SI NO ENTRA
            if (alto<e.getAlto()){
                int x,y;
                g2.setColor(e.getColor());
                g2.setFont(e.getFuente());

                //escribimos
                Point po = alinear(p,ancho,alto,e.getAncho(),e.getAlto(),e.getAlineacionHorizontal(),e.getAlineacionVertical());
                g2.drawString(texto,po.x ,po.y);
            }
            
        //DIJAMOS EL BORDE
            if (e.getBorde()==true){
                mostrarBorde(g2,e);
            }
    }
    
    //FUNCIONES DE CREACION
        public clsPrintElemento addImagen(int X, int Y, int Ancho, int Alto, String Direccion, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearImagen(X, Y, Ancho, Alto, Direccion);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);
            
            return ele;
        }
        public clsPrintElemento addTitulo(int X, int Y, int Ancho, int Alto, String Texto, Font Fuente, Color Col, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTitulo(X, Y, Ancho, Alto,Texto,Fuente,Col);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);
            
            return ele;
        }
        public clsPrintElemento addTexto(int X, int Y, int Ancho, int Alto,int Tipo, String Origen, Font Fuente, Color Col, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTexto(X, Y, Ancho, Alto,Tipo,Origen,Fuente,Col);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);

            return ele;
        }
        public clsPrintElemento addLinea(int X, int Y, int Ancho, int Alto, int Espesor, Color color, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearLinea(X, Y, Ancho, Alto, Espesor, color);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);
            
            return ele;
        }
        public clsPrintElemento addRectangulo(int X, int Y, int Ancho, int Alto, int Espesor, Color color, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearRectangulo(X, Y, Ancho, Alto, Espesor, color);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);

            return ele;
        }
        public clsPrintElemento addCirculo(int X, int Y, int Radio, int Espesor, Color color, int layer){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearCirculo(X, Y, Radio, Espesor, color);
            ele.setLayer(layer);
            capas.get(layer).addElemento(ele);
            
            return ele;
        }
    
    //FUNCIONES DE CARGA Y GUARDADO
        public void abrirArchivo(String fichero){
            if (existsFile(fichero)){
                try {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
                        Object aux;
                        int cant=0;
                        //CARGAMOS VARIABLES DEL PRINTER
                            aux=ois.readObject();
                            fuente=(Font)aux;
                            aux=ois.readObject();
                            setFormatoPapel((String)aux);
                            aux=ois.readObject();
                            setOrientacion((Integer)aux);
                            aux=ois.readObject();
                            conexion=(String)aux;
                            aux=ois.readObject();
                            punto0=(Point)aux;
                            aux=ois.readObject();
                            tamaño=(Point)aux;
                            aux=ois.readObject();
                            margenX=(Integer)aux;
                            aux=ois.readObject();
                            margenY=(Integer)aux;
                            aux=ois.readObject();
                            zoom=(Double)aux;
                            aux=ois.readObject();
                            paginas=(Integer)aux;
                            aux=ois.readObject();
                            paginaActual=(Integer)aux;
                            aux=ois.readObject();
                            fileName=(String)aux;
                            aux=ois.readObject();
                            fileDir=(String)aux;
                            aux=ois.readObject();
                            //origenDatos=(String)aux;
                            
                            
                        
                        //CARGAMOS LAS CAPAS
                            //LIMPIAMOS LAS CAPAS ACTUALES
                            capas.clear();
                            
                            //CARGAMOS LAS CAPA
                            aux=ois.readObject();
                            cant=(int)aux;
                            
                            for (int i=0;i<cant;i++){
                                aux=ois.readObject();
                                capas.add((clsPrintLayer)aux);
                            }
                    }
                } catch (IOException | ClassNotFoundException ex) {            
                    JOptionPane.showMessageDialog(null,"ERROR AL ABRIR ARCHIVO BD\nERROR : " + ex.getMessage());
                }
            }
        }
        public void guardarArchivo(String fichero){
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
                //GUARDAMOS VARIABLE DEL PRINTER
                    oos.writeObject(fuente);
                    oos.writeObject(formatoPapel);
                    oos.writeObject(orientacion);
                    oos.writeObject(conexion);
                    oos.writeObject(punto0);
                    oos.writeObject(tamaño);
                    oos.writeObject(margenX);
                    oos.writeObject(margenY);
                    oos.writeObject(zoom);
                    oos.writeObject(paginas);
                    oos.writeObject(paginaActual);
                    oos.writeObject(fileName);
                    oos.writeObject(fileDir);
                    oos.writeObject(origenDatos);
                    
                //GUARDAMOS CAPAS
                    oos.writeObject(capas.size());
                    for (int i=0;i<capas.size();i++){
                        oos.writeObject(capas.get(i));
                    }
                    
                
                //CERRAMOS EL ARCHIVO
                    oos.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"ERROR GUARDANDO ARCHIVO \nERROR : " + ex.getMessage());
            }
        }
    
    //FUNCIONES DE MOVIMIENTO DE ELEMENTOS
        public void moveUP(int layer, int numero){
            if (numero>0){
                capas.get(layer).moveUp(numero);
            }
        }
        public void moveDown(int layer, int numero){
            if (numero>0){
                capas.get(layer).moveDown(numero);
            }
        }
        public void moverElemento(int layerI, int numero,int layerF){
            clsPrintElemento aux1=null;
            
            if (numero>-1 && layerI >-1 && layerF>-1){
                        aux1=capas.get(layerI).getObjeto(numero);
                        capas.get(layerI).removeElemento(numero);
                        addElemento(aux1,layerF);
            }

        }
        
    //FUNCIONES DE GESTION DE CAPAS
        public clsPrintLayer newLayer(){
            clsPrintLayer aux = new clsPrintLayer(capas.size());
            
            capas.add(aux);
            
            return aux;
        }
        public clsPrintLayer newLayer(int id){
            clsPrintLayer aux = new clsPrintLayer(id);
            
            capas.add(aux);
            return aux;
        }
        public clsPrintLayer newLayer(int id, String name){
            clsPrintLayer aux = new clsPrintLayer(id, name);
            
            capas.add(aux);
            return aux;
        }
        public clsPrintLayer newLayer(String name){
            clsPrintLayer aux = new clsPrintLayer(capas.size(), name);
            
            capas.add(aux);
            return aux;
        }
        public void removeLayer(int n){
            if (n>-1 && n<capas.size()) {
                if (selectedLayer.getLayerID()==n) selectedLayer=null;
                capas.remove(n);
            }
            
            //COMPROMBAMOS SI SE HA ELIMINADO LA ULTIMA CAPA
            if (capas.size()<1){
                //SI NO HAY CAPAS SE CREA UNA
                selectedLayer = new clsPrintLayer();
                capas.add(selectedLayer);
            }
        }
        public void clearLayers(){
            capas.clear();
        }
        public boolean isLayer(int n){
            boolean valor=false;
            if (n>-1 && n<capas.size()){
                valor=true;
            }
            
            return valor;
        }
    
    //FUNCIONES DE GESTION DE ELEMENTOS
        public clsPrintElemento addElemento(int layer){
            clsPrintElemento aux1=new clsPrintElemento();    
            capas.get(layer).addElemento(aux1);
                
            return aux1;
        }
        public void addElemento(clsPrintElemento elemento,int layer){
            capas.get(layer).addElemento(elemento);
        }
        public void removeElemento(int layer, int numero){
            clsPrintElemento aux1=null;

            if (numero>-1){
                capas.get(layer).removeElemento(numero);
            }
        }
        public void duplicarElemento(int layer, int numero){
            capas.get(layer).duplicateElemento(numero);   
        }
    
    //FUNCIONES VARIAS
        private int cantidadElementosDB(clsPrintElemento e, String origen){
            //calcula la cantidad de registos ha mostrar de la base de datos
            int cantidad=0;
            if (!"".equals(origen)){
                clsH2R r = DB.newSelect(origen);
                cantidad=(int) r.getCantidad();
                r.CloseSlect();
            }
            return cantidad;
        }
        public int calcularPaginas(String origen){
            int pag;
            clsPrintElemento e;
            pag=1;
            
            for (int l= 0;l<capas.size();l++){
                //REVISAMOS TODAS LAS CAPAS
                for (int i =0;i<capas.get(l).getCantidadElementos();i++){
                    //REVISAMO LA CAPA ACTUAL
                    e=(clsPrintElemento)capas.get(l).getObjeto(i);
                    if (e.getTipo()==3 && e.getTileable()==true){
                        //SI TIPO CAMPO ES TEXTO MIRAMOS CANTIDAD DE ELEMENTOS
                        int cant = cantidadElementosDB(e,origen);
                        int p =(int) cant/e.getRepeticiones();

                        //SI CANTIDAD DE PAGINAS NECESARIAS ES MAYOR QUE LA ACTUAL CAMBIAMOS A LA RESULTANTE
                        if (p>pag) pag=p;
                    }
                }
            }

            return pag;
        }
        private Point alinear(Point p,int anchoT, int altoT,int anchoH, int altoH,int alineacionH, int alineacionV){
            Point po= new Point();
            int x,y;
            //alineacion horizontal
                switch (alineacionH){
                    case 0:
                        //central
                        x=(anchoH/2)-(anchoT/2);
                        po.x= p.x+x;
                        break;
                    case 1:
                        //izquierda
                        x=2;
                        po.x= p.x+x;
                        break;
                    case 2:
                        //derecha
                        x=anchoH-anchoT;
                        po.x= p.x+x;
                        break;
                }
                //alineacion vertical
                switch (alineacionV){
                    case 0:
                        //central
                        y=(altoH/2)+(altoT/2);
                        y=y-2;
                        po.y= p.y+y;
                        break;
                    case 1:
                        //arriba
                        y=altoT-2;
                        po.y= p.y+y;
                        break;
                    case 2:
                        //abajo
                        y=altoH;
                        po.y= p.y+y;
                        break;
                }

                return po;
        }
        private String buscarOrigen(clsPrintElemento e){
            String origen=e.getOrigenDatos();

            if (origen.equals("")){
                //INSERTAMOS EL ORIGEN DE DATOS DE LA PAGINA
                origen =origenDatos;
            }else{
                //insertamos el valor de los ARG[]
                if (arg!=null && !origen.equals("")){
                    origen = insertarValor(origen,e.getTipoOrigen());
                }else{
                    origen="";
                }
            }

            return origen;
        }
        private String insertarValor(String g, int origen){
            String texto = g;
            if (arg!=null){
                for (int i =0;i<arg.length;i++){
                    String aux="ARG[" + i + "]";

                    texto= texto.replace(aux, arg[i]);
                    if (origen==5){
                        //TIPO ID
                        try {
                            int n =Integer.valueOf(texto);
                            texto = formatoID(n);
                        }catch (NumberFormatException e) {
                            //nada
                        }
                    }
                }
            }else{
                texto="";
            }
            return texto;
        }
        private String buscarTexto(clsPrintElemento e, int posicion, String origen){
            String texto="";
            clsH2R r = DB.newSelect(origen);    
                if (r.getCantidad()>0 ){        
                    r.moveTo(posicion);
                    switch (e.getTipoOrigen()){
                        case 0:
                            //texto
                            texto=r.getString(e.getColumna());
                            break;
                        case 1:
                            //entero
                            texto=String.valueOf(r.getInt(e.getColumna()));
                            break;
                        case 2:
                            //double
                            texto=String.valueOf(redondear(r.getDouble(e.getColumna()),3));
                            break;
                        case 3:
                            //fecha
                            texto=formatStringDateSQLToStringDate(String.valueOf(r.getDate(e.getColumna())));
                            break;
                        case 4:
                            //boolean
                            texto=String.valueOf(r.getBoolean(e.getColumna()));
                            break;
                        case 5:
                            //ID
                            texto=formatoID(r.getInt(e.getColumna()));
                            break;
                    }
                }
            r.CloseSlect();
            return texto;
        }
        private String recortarTexto(String Texto, int Ancho, Graphics ga, Font Fuente){
            String texto=Texto;
            int cantidad = texto.length();

            FontMetrics fm= ga.getFontMetrics(Fuente);
            while (fm.stringWidth(texto)>Ancho){
                cantidad--;
                texto=texto.substring(0, cantidad);
            }

            return texto;
        }
        private int puntosToMM(Double a){
        double n=(a/72)*25.4;
        
        return (int)n;
    }
        public boolean existsFile(String s){
            boolean valor=false;
            File f = new File(s);
            if (f.exists()){
                valor=true;
                fileName=f.getName();
                fileDir=f.getPath();
            }
            
            return valor;
        }
        
    //FUNCIONES DE BUSQUEDA
        public void searchElement(clsPrintElemento s){
            boolean valor=false;
            
            for (int l=capas.size()-1;l>-1;l--){
                for (int i=0;i<capas.get(i).getCantidadElementos();i++){
                    if (capas.get(i).getObjeto(i)==s){
                        valor = true;
                        selectedLayer=capas.get(i);
                        selectedId=i;
                        break;
                    }
                }
                if (valor) break;
            }
        }
        public void searchClick(Point p){
            clsPrintElemento valor=null;
            for (int l=capas.size()-1;l>-1;l--){
                valor=capas.get(l).searchClick(p);
                if (valor!=null) break;
            }
            
            selected=valor;
        }
        public void searchClickNext(Point p){
            clsPrintElemento valor=null;
            int layerA = selectedLayer.getSelectedId();
            
            valor=capas.get(layerA).searchClickNext(p);
            
            if (valor==null){
                for (int l=layerA-1;l>-1;l--){
                    valor=capas.get(l).searchClick(p);
                    if (valor!=null) break;
                }
            }
            
            
            if (valor!=null)selected=valor;
        }
        
    // FUNCIONES DE CONVERSION
        public String formatoID(Double n){
            DecimalFormat format = new DecimalFormat("0000000");
            if (n>0)return format.format(n);//numero 
            else return ""; //vacio
        }
        public String formatoID(int n){
            DecimalFormat format = new DecimalFormat("0000000");
            if (n>0)return format.format(n);//numero 
            else return ""; //vacio
        }
        public double redondear(double n){
            return Math.round(n*100)/100;
        }
        public double redondear(double n, int decimales){
            int de = 1;
            for (int i=0;i<decimales;i++){
                de=de*10;
            }

            return (double)Math.round (n*de)/de;
        }
        
        public String formatStringDateSQLToStringDate(String s){
                SimpleDateFormat formato=new SimpleDateFormat("dd-MM-yyyy");
                    String valor="";
                    if (s!=null){
                        Date d = convertStringToDateSQL(s);
                        if (d!=null) valor = formato.format(d);
                    }
                    return valor;
        }
        public Date convertStringToDateSQL(String d){
        SimpleDateFormat formatoSQL= new SimpleDateFormat("yyyy-MM-dd");
        Date fecha=null;

        if (!d.equals("null")){
            try {
                fecha=formatoSQL.parse(d);
            } catch (ParseException ex) {
                //Logger.getLogger(clsComunes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fecha;
    }
            
}//FIN DE LA CLASE Impresora

