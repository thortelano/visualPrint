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
    
    private int idElemento;
    private clsPrintElemento selected;
    private int selectedLayer;
    private int selectedId;
    
    //VARIABLES ARCHIVO
    private String fileName;
    private String fileDir;
    
    //VARIABLE CONEXION DB
    private String origenDatos; //sentencia SQL para acceder a la DB
    private clsH2 DB;
    
    //OBJETOS DE IMPRESION
    ArrayList<clsPrintElemento> elementosEncabezado;
    ArrayList<clsPrintElemento> elementosPie;
    ArrayList<clsPrintElemento> elementos;
    
    
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
        
        elementosEncabezado= new ArrayList();
        elementosPie= new ArrayList();
        elementos= new ArrayList();

        paginas=1;
        paginaActual=1;
        origenDatos="";
        
        zoom=1;
        
        fileName="";
        fileDir="";
        
        selected=null;
        selectedLayer=-1;
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
            
            
                mostrarEncabezado(g2,pageIndex);
                mostrarPie(g2,pageIndex);
                mostrarContenido(g2,pageIndex);
            
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
                mostrarEncabezadoEdit(ga,paginaActual-1);
                mostrarPieEdit(ga,paginaActual-1);
                mostrarContenidoEdit(ga,paginaActual-1);
                if (selected != null){
                    mostrarSeleccion(ga,selected);
                }
            }else if (mode==PRINT_MODE){
                mostrarFondoHoja(ga);
                mostrarEncabezado(ga,paginaActual-1);
                mostrarPie(ga,paginaActual-1);
                mostrarContenido(ga,paginaActual-1);
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
        public void setIdElemento(int i){
            idElemento=i;
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
            selectedLayer=n;
        }
        public void setSelectedId(int n){
            selectedId=n;
        }
        
        public Point getPunto0(){
            return punto0;
        }
        public int getCantidadElementosEncabezado(){
            return elementosEncabezado.size();
        }
        public int getCantidadElementosPie(){
            return elementosPie.size();
        }
        public int getCantidadElementos(){
            return elementos.size();
        }
        public int getCantidadElementos(int tipo){
            int cantidad=0;
            switch (tipo){
                case 0:
                    cantidad= elementosEncabezado.size();
                    break;
                case 1:
                    cantidad= elementosPie.size();
                    break;
                case 2:
                    cantidad= elementos.size();
                    break;
            }
            return cantidad;
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
        public clsPrintElemento getObjeto(int tipo,int numero){
            clsPrintElemento aux=null;
            switch (tipo){
                case 0:
                    aux=elementosEncabezado.get(numero);
                    break;
                case 1:
                    aux=elementosPie.get(numero); 
                    break;
                case 2:
                    aux=elementos.get(numero);
                    break;
            }
            return aux;

        }
        public int getMode(){
            return mode;
        }
        public int getIdElemento(){
            return idElemento;
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
        public int getSelectedLayer(){
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

        private void mostrarEncabezado(Graphics ga, int pag){
            clsPrintElemento ele;

            for (int i =0;i<elementosEncabezado.size();i++ ){
                ele=(clsPrintElemento)elementosEncabezado.get(i);

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
                }
            }

        }
        private void mostrarPie(Graphics ga, int pag){
            clsPrintElemento ele;

            for (int i =0;i<elementosPie.size();i++ ){
                ele=(clsPrintElemento)elementosPie.get(i);

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
        private void mostrarContenido(Graphics ga, int pag){
            clsPrintElemento ele;

            for (int i =0;i<elementos.size();i++ ){
                ele=(clsPrintElemento)elementos.get(i);

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

        private void mostrarEncabezadoEdit(Graphics ga, int pag){
            clsPrintElemento ele;

            for (int i =0;i<elementosEncabezado.size();i++ ){
                ele=(clsPrintElemento)elementosEncabezado.get(i);

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
                }
            }

        }
        private void mostrarPieEdit(Graphics ga, int pag){
            clsPrintElemento ele;

            for (int i =0;i<elementosPie.size();i++ ){
                ele=(clsPrintElemento)elementosPie.get(i);

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
        private void mostrarContenidoEdit(Graphics ga, int pag){
        clsPrintElemento ele;
        
        for (int i =0;i<elementos.size();i++ ){
            ele=(clsPrintElemento)elementos.get(i);
            
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
        public clsPrintElemento addImagen(int X, int Y, int Ancho, int Alto, String Direccion, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearImagen(X, Y, Ancho, Alto, Direccion);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }

            return ele;
        }
        public clsPrintElemento addTitulo(int X, int Y, int Ancho, int Alto, String Texto, Font Fuente, Color Col, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTitulo(X, Y, Ancho, Alto,Texto,Fuente,Col);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }

            return ele;
        }
        public clsPrintElemento addTexto(int X, int Y, int Ancho, int Alto,int Tipo, String Origen, Font Fuente, Color Col, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearTexto(X, Y, Ancho, Alto,Tipo,Origen,Fuente,Col);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }

            return ele;
        }
        public clsPrintElemento addLinea(int X, int Y, int Ancho, int Alto, int Espesor, Color color, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearLinea(X, Y, Ancho, Alto, Espesor, color);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }
            return ele;
        }
        public clsPrintElemento addRectangulo(int X, int Y, int Ancho, int Alto, int Espesor, Color color, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearRectangulo(X, Y, Ancho, Alto, Espesor, color);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }

            return ele;
        }
        public clsPrintElemento addCirculo(int X, int Y, int Radio, int Espesor, Color color, int Seccion){
            clsPrintElemento ele=new clsPrintElemento();
            ele.crearCirculo(X, Y, Radio, Espesor, color);
            ele.setSeccion(Seccion);

            switch (Seccion){
                case 0:
                    elementosEncabezado.add(ele);
                    break;
                case 1:
                    elementosPie.add(ele);
                    break;
                case 2:
                    elementos.add(ele);
                    break;
            }
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
                            elementosEncabezado.clear();
                            elementosPie.clear();
                            elementos.clear();

                            //CARGAMOS EL CONTENIDO DE CADA CAPA
                            aux=ois.readObject();
                            cant=(int)aux;
                            for (int i=0;i<cant;i++){
                                aux=ois.readObject();
                                elementosEncabezado.add((clsPrintElemento)aux);
                            }
                            aux=ois.readObject();
                            cant=(int)aux;
                            for (int i=0;i<cant;i++){
                                aux=ois.readObject();
                                elementosPie.add((clsPrintElemento)aux);
                            }
                            aux=ois.readObject();
                            cant=(int)aux;
                            //while (aux!=null){
                            for (int i=0;i<cant;i++){
                                aux=ois.readObject();
                                elementos.add((clsPrintElemento)aux);
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
                    oos.writeObject(elementosEncabezado.size());
                    for (int i=0;i<elementosEncabezado.size();i++){
                        oos.writeObject(elementosEncabezado.get(i));
                    }
                    oos.writeObject(elementosPie.size());
                    for (int i=0;i<elementosPie.size();i++){
                        oos.writeObject(elementosPie.get(i));
                    }
                    oos.writeObject(elementos.size());
                    for (int i=0;i<elementos.size();i++){
                        oos.writeObject(elementos.get(i));
                    }
                
                //CERRAMOS EL ARCHIVO
                    oos.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"ERROR GUARDANDO ARCHIVO \nERROR : " + ex.getMessage());
            }
        }
    
    //FUNCIONES DE MOVIMIENTO DE ELEMENTOS
        public void subirElemento(int seccion, int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=null;

            if (numero>0){
                switch (seccion){
                    case 0:
                        aux1= elementosEncabezado.get(numero-1);
                        aux2= elementosEncabezado.get(numero);
                        elementosEncabezado.set(numero-1,aux2);
                        elementosEncabezado.set(numero,aux1);
                        break;
                    case 1:
                        aux1= elementosPie.get(numero-1);
                        aux2= elementosPie.get(numero);
                        elementosPie.set(numero-1,aux2);
                        elementosPie.set(numero,aux1);
                        break;
                    case 2:
                        aux1= elementos.get(numero-1);
                        aux2= elementos.get(numero);
                        elementos.set(numero-1,aux2);
                        elementos.set(numero,aux1);
                        break;
                }
            }
        }
        public void bajarElemento(int seccion, int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=null;

            switch (seccion){
                    case 0:
                        if (numero<elementosEncabezado.size()-1){
                            aux1= elementosEncabezado.get(numero+1);
                            aux2= elementosEncabezado.get(numero);

                            elementosEncabezado.set(numero+1,aux2);
                            elementosEncabezado.set(numero,aux1);
                        }
                        break;
                    case 1:
                        if (numero<elementosPie.size()-1){
                            aux1= elementosPie.get(numero+1);
                            aux2= elementosPie.get(numero);
                            elementosPie.set(numero+1,aux2);
                            elementosPie.set(numero,aux1);
                        }
                        break;
                    case 2:
                        if (numero<elementos.size()-1){
                            aux1= elementos.get(numero+1);
                            aux2= elementos.get(numero);
                            elementos.set(numero+1,aux2);
                            elementos.set(numero,aux1);
                        }
                        break;
                }

        }
        public void moverElemento(int seccionI, int numero,int seccionF){
            clsPrintElemento aux1=null;
            if (numero>-1){
                switch (seccionI){
                    case 0:
                        aux1=elementosEncabezado.get(numero);
                        elementosEncabezado.remove(numero);
                        addElemento(aux1,seccionF);
                        break;
                    case 1:
                        aux1=elementosPie.get(numero);
                        elementosPie.remove(numero);                    
                        addElemento(aux1,seccionF);
                        break;
                    case 2:
                        aux1=elementos.get(numero);
                        elementos.remove(numero);
                        addElemento(aux1,seccionF);
                        break;
                }
            }

        }
    
    //FUNCIONES DE GESTION DE ELEMENTOS
        public clsPrintElemento addElemento(int seccion){
            clsPrintElemento aux1=new clsPrintElemento();
                switch (seccion){
                    case 0:
                        elementosEncabezado.add(aux1);
                        break;
                    case 1:
                        elementosPie.add(aux1);
                        break;
                    case 2:
                        elementos.add(aux1);
                        break;
                }
            return aux1;
        }
        public void addElemento(clsPrintElemento elemento,int seccion){
                switch (seccion){
                    case 0:
                        elementosEncabezado.add(elemento);
                        break;
                    case 1:
                        elementosPie.add(elemento);
                        break;
                    case 2:
                        elementos.add(elemento);
                        break;
                }
        }
        public void eliminarElemento(int seccion, int numero){
            clsPrintElemento aux1=null;

            if (numero>-1){
                switch (seccion){
                    case 0:
                        elementosEncabezado.remove(numero);
                        break;
                    case 1:
                        elementosPie.remove(numero);
                        break;
                    case 2:
                        elementos.remove(numero);
                        break;
                }
            }
        }
        public void duplicarElemento(int seccion, int numero){
            clsPrintElemento aux1=null;
            clsPrintElemento aux2=new clsPrintElemento();

            if (numero>=0){
                switch (seccion){
                    case 0:
                        aux1=elementosEncabezado.get(numero);
                        break;
                    case 1:
                        aux1=elementosPie.get(numero);
                        break;
                    case 2:
                        aux1=elementos.get(numero);
                        break;
                }
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
                aux2.setSeccion(clone(aux1.getSeccion()));
                aux2.setTexto(clone(aux1.getTexto()));
                aux2.setTipo(clone(aux1.getTipo()));
                aux2.setTipoOrigen(clone(aux1.getTipoOrigen()));

            addElemento(aux2,seccion);
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
            ele.setSeccion(elemento.getSeccion());


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

            for (int i =0;i<elementos.size();i++){
                e=(clsPrintElemento)elementos.get(i);
                if (e.getTipo()==3 && e.getTileable()==true){
                    //si tipo campo texto
                    int cant = cantidadElementosDB(e,origen);
                    int p =(int) cant/e.getRepeticiones();

                    //si cantidad de paginas necesarias en mayor que el actual cambiamos a estas
                    if (p>pag) pag=p;
                }
            }

            return pag;
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
        public void searchElement(clsPrintElemento s){
            boolean valor=false;
            
            //BUSCAR EN CONTENIDO
            for (int i=0;i<elementos.size();i++){
                if (elementos.get(i)==s){
                    valor = true;
                    selectedLayer=2;
                    selectedId=i;
                    break;
                }
            }
            
            //BUSCAR EN PIE
            if (!valor){
                for (int i=0;i<elementosPie.size();i++){
                    if (elementosPie.get(i)==s){
                        valor = true;
                        selectedLayer=1;
                        selectedId=i;
                        break;
                    }
                }
            }
            
            //BUSCAR EN ENCABEZADO
            if (!valor){
                for (int i=0;i<elementosEncabezado.size();i++){
                    if (elementosEncabezado.get(i)==s){
                        valor = true;
                        selectedLayer=0;
                        selectedId=i;
                        break;
                    }
                }
            }
        }
        public void searchDown(Point p){
            clsPrintElemento valor=null;
            
            //BUSCAR EN CONTENIDO
            valor = searchInLayer(2,p,0);
            
            //BUSCAR EN PIE
            if (valor==null) valor = searchInLayer(1,p,0);
            
            //BUSCAR EN ENCABEZADO
            if (valor==null) valor = searchInLayer(0,p,0);
            
            selected=valor;
        }
        public void searchDownNext(Point p){
            clsPrintElemento valor=null;
            
            if (selectedLayer==2){
                //EMPIEZA A BUSCAR EN CONTENIDO
                //BUSCAR EN CONTENIDO
                    valor = searchInLayer(2,p,selectedId+1);
                //BUSCAR EN PIE
                    if (valor==null) valor = searchInLayer(1,p,0);
                //BUSCAR EN ENCABEZADO
                    if (valor==null) valor = searchInLayer(0,p,0);
            }else if (selectedLayer==1){
                //EMPIEZA A BUSCAR EN PIE
                //BUSCAR EN PIE
                    valor = searchInLayer(1,p,selectedId+1);
                //BUSCAR EN ENCABEZADO
                    if (valor==null) valor = searchInLayer(0,p,0);
            }else if (selectedLayer==0){
                //EMPIEZA A BUSCAR EN ENCABEZADO
                //BUSCAR EN ENCABEZADO
                    valor = searchInLayer(0,p,selectedId+1);
            }
            
            if (valor!=null)selected=valor;
        }
        public clsPrintElemento searchInLayer(int layer, Point p, int inicio){
            clsPrintElemento valor=null;
            selectedLayer=-1;
            selectedId=-1;
            
            //BUSCAR EN ENCABEZADO
            if (layer==0){
                for (int i=inicio;i<elementosEncabezado.size();i++){
                    if (elementosEncabezado.get(i).isDown(p)){
                        valor = elementosEncabezado.get(i);
                        
                        selectedLayer=0;
                        selectedId=i;
                        break;
                    }
                }
            }
            
            //BUSCAR EN PIE
            if (layer==1){
                for (int i=inicio;i<elementosPie.size();i++){
                    if (elementosPie.get(i).isDown(p)){
                        valor = elementosPie.get(i);
                        selectedLayer=1;
                        selectedId=i;
                        break;
                    }
                }
            }
            
            
            //BUSCAR EN CONTENIDO
            if (layer==2){
                for (int i=inicio;i<elementos.size();i++){
                    if (elementos.get(i).isDown(p)){
                        valor = elementos.get(i);
                        selectedLayer=2;
                        selectedId=i;
                        break;
                    }
                }
            }
            
            return valor;
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

