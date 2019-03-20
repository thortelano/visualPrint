package importExport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import clasesBase.clsPrint;
import clasesBase.clsPrintElemento;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author anhor
 */
public class clsImportExportV1 {
    private String sep="|";
    private String sep1="\\|";
    
    private clsPrint printer;
    private String archivo;
    
    public clsImportExportV1(){
        inicializar();
    }
    private void inicializar(){
        printer=null;
        archivo="";
    }
    
    public void setPrint(clsPrint p){
        printer=p;
    }
    public void setArchivo(String a){
        archivo=a;
    }
    
    @Deprecated
    public void exportV1(){
        String sep="|";
        String linea="";
        FileWriter fichero = null;
        PrintWriter pw = null;
        clsPrintElemento aux;
        /*
        try
        {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            
            //LINEA TIPO 0 DATOS DEL ARCHIVO
                linea = "0" + sep + 
                        printer.getPaginas() + sep + 
                        printer.getMargenX() + sep + 
                        printer.getMargenY() + sep + 
                        printer.getOrigenDatos() + sep + 
                        printer.getCantidadElementosEncabezado() + sep +
                        printer.getCantidadElementos() + sep +
                        printer.getCantidadElementosPie();
                pw.println(linea);
                
            //LINEA TIPO 1 OBJETOS EN ENCABEZADO
                for (int i =0; i<printer.getCantidadElementosEncabezado();i++){
                    aux= printer.getObjeto(0, i);
                    linea = "1" + sep + aux.getTipo() + sep +
                            aux.getPunto().x + sep +
                            aux.getPunto().y + sep +
                            aux.getAreaTileableX() + sep +
                            aux.getAreaTileableY() + sep +
                            aux.getAncho() + sep +
                            aux.getAlto() + sep +
                            aux.getTexto() + sep +
                            aux.getOrigenDatos() + sep +
                            aux.getColumna() + sep +
                            aux.getTipoOrigen() + sep +
                            aux.getFuente().getName() + sep +
                            aux.getFuente().getStyle() + sep +
                            aux.getFuente().getSize() + sep +
                            aux.getEspesor() + sep +
                            aux.getColor().getRed() + sep +
                            aux.getColor().getGreen() + sep +
                            aux.getColor().getBlue() + sep +
                            aux.getRotacion() + sep +
                            aux.getBorde() + sep +
                            aux.getBordeColor().getRed() + sep +
                            aux.getBordeColor().getGreen() + sep +
                            aux.getBordeColor().getBlue() + sep +
                            aux.getBordeEspesor() + sep +
                            aux.getRelleno() + sep +
                            aux.getRellenoColor().getRed() + sep +
                            aux.getRellenoColor().getGreen() + sep +
                            aux.getRellenoColor().getBlue() + sep +
                            aux.getSeccion() + sep +
                            aux.getAlineacionVertical() + sep +
                            aux.getAlineacionHorizontal() + sep +
                            aux.getRepeticiones() + sep +
                            aux.getTileable() + sep +
                            aux.getPosicion() + sep;
                    pw.println(linea);
                }
            //LINEA TIPO 2 OBJETOS EN CONTENIDO
                for (int i =0; i<printer.getCantidadElementos();i++){
                    aux= printer.getObjeto(2, i);
                    linea = "2" + sep + aux.getTipo() + sep +
                            aux.getPunto().x + sep +
                            aux.getPunto().y + sep +
                            aux.getAreaTileableX() + sep +
                            aux.getAreaTileableY() + sep +
                            aux.getAncho() + sep +
                            aux.getAlto() + sep +
                            aux.getTexto() + sep +
                            aux.getOrigenDatos() + sep +
                            aux.getColumna() + sep +
                            aux.getTipoOrigen() + sep +
                            aux.getFuente().getName() + sep +
                            aux.getFuente().getStyle() + sep +
                            aux.getFuente().getSize() + sep +
                            aux.getEspesor() + sep +
                            aux.getColor().getRed() + sep +
                            aux.getColor().getGreen() + sep +
                            aux.getColor().getBlue() + sep +
                            aux.getRotacion() + sep +
                            aux.getBorde() + sep +
                            aux.getBordeColor().getRed() + sep +
                            aux.getBordeColor().getGreen() + sep +
                            aux.getBordeColor().getBlue() + sep +
                            aux.getBordeEspesor() + sep +
                            aux.getRelleno() + sep +
                            aux.getRellenoColor().getRed() + sep +
                            aux.getRellenoColor().getGreen() + sep +
                            aux.getRellenoColor().getBlue() + sep +
                            aux.getSeccion() + sep +
                            aux.getAlineacionVertical() + sep +
                            aux.getAlineacionHorizontal() + sep +
                            aux.getRepeticiones() + sep +
                            aux.getTileable() + sep +
                            aux.getPosicion() + sep;
                    pw.println(linea);
                }
            //LINEA TIPO 3 OBJETOS EN PIE
                for (int i =0; i<printer.getCantidadElementosPie();i++){
                    aux= printer.getObjeto(1, i);
                    linea = "3" + sep + aux.getTipo() + sep +
                            aux.getPunto().x + sep +
                            aux.getPunto().y + sep +
                            aux.getAreaTileableX() + sep +
                            aux.getAreaTileableY() + sep +
                            aux.getAncho() + sep +
                            aux.getAlto() + sep +
                            aux.getTexto() + sep +
                            aux.getOrigenDatos() + sep +
                            aux.getColumna() + sep +
                            aux.getTipoOrigen() + sep +
                            aux.getFuente().getName() + sep +
                            aux.getFuente().getStyle() + sep +
                            aux.getFuente().getSize() + sep +
                            aux.getEspesor() + sep +
                            aux.getColor().getRed() + sep +
                            aux.getColor().getGreen() + sep +
                            aux.getColor().getBlue() + sep +
                            aux.getRotacion() + sep +
                            aux.getBorde() + sep +
                            aux.getBordeColor().getRed() + sep +
                            aux.getBordeColor().getGreen() + sep +
                            aux.getBordeColor().getBlue() + sep +
                            aux.getBordeEspesor() + sep +
                            aux.getRelleno() + sep +
                            aux.getRellenoColor().getRed() + sep +
                            aux.getRellenoColor().getGreen() + sep +
                            aux.getRellenoColor().getBlue() + sep +
                            aux.getSeccion() + sep +
                            aux.getAlineacionVertical() + sep +
                            aux.getAlineacionHorizontal() + sep +
                            aux.getRepeticiones() + sep +
                            aux.getTileable() + sep +
                            aux.getPosicion() + sep;
                    pw.println(linea);
                }
                

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
        */
    }
    public void importV1(){
        String linea="";
        File arc = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        clsPrintElemento aux;
        
        try
        {
            arc = new File (archivo);
            fr = new FileReader (arc);
            br = new BufferedReader(fr);
            
            while((linea=br.readLine())!=null){
                //LEEMOS LAS LINEAS DEL ARCHIVO
                switch (linea.charAt(0)){
                    case '0':
                        //LINEA TIPO 0 DATOS DEL ARCHIVO
                            String[] partes = linea.split(sep1);
                            printer.setMargenX(Integer.valueOf(partes[2]));
                            printer.setMargenY(Integer.valueOf(partes[3]));
                            printer.setOrigenDatos(partes[4]);
                        break;
                    case '1':
                        //LINEA TIPO 1 OBJETOS EN ENCABEZADO
                        insertElemento(linea,0);
                        break;
                    case '2':
                        //LINEA TIPO 2 OBJETOS EN CONTENIDO
                        insertElemento(linea,1);
                        break;
                    case '3':
                        //LINEA TIPO 3 OBJETOS EN PIE
                        insertElemento(linea,2);
                        break;
                }
            }
            printer.setLayerName(0, "ENCABEZADO");
            printer.setLayerName(1, "CONTENIDO");
            printer.setLayerName(2, "PIE");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // asegurarnos que se cierra el fichero.
           if (null != fr)
              fr.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    private void insertElemento(String s, int layer){
        while (!printer.isLayer(layer)) printer.newLayer();
        
        clsPrintElemento aux= new clsPrintElemento();
        String[] partes = s.split(sep1);
        
        aux.setTipo(Integer.valueOf(partes[1]));
        aux.setPunto(new Point(Integer.valueOf(partes[2]),Integer.valueOf(partes[3])));
        aux.setAreaTileable(new Point(Integer.valueOf(partes[4]),Integer.valueOf(partes[5])));
        aux.setAncho(Integer.valueOf(partes[6]));
        aux.setAlto(Integer.valueOf(partes[7]));
        aux.setTexto(partes[8]);
        aux.setOrigenDatos(partes[9]);
        aux.setColumna(partes[10]);
        aux.setTipoOrigen(Integer.valueOf(partes[11]));
        aux.setFuente(new Font(partes[12],Integer.valueOf(partes[13]),Integer.valueOf(partes[14])));
        aux.setEspesor(Integer.valueOf(partes[15]));
        aux.setColor(new Color(Integer.valueOf(partes[16]),Integer.valueOf(partes[17]),Integer.valueOf(partes[18])));
        aux.setRotacion(Integer.valueOf(partes[19]));
        aux.setBorde(Boolean.valueOf(partes[20]));
        aux.setBordeColor(new Color(Integer.valueOf(partes[21]),Integer.valueOf(partes[22]),Integer.valueOf(partes[23])));
        aux.setBordeEspesor(Integer.valueOf(partes[24]));
        aux.setRelleno(Boolean.valueOf(partes[25]));
        aux.setRellenoColor(new Color(Integer.valueOf(partes[26]),Integer.valueOf(partes[27]),Integer.valueOf(partes[28])));
        aux.setLayer(Integer.valueOf(partes[29]));
        aux.setAlineacionVertical(Integer.valueOf(partes[30]));
        aux.setAlineacionHorizontal(Integer.valueOf(partes[31]));
        aux.setRepeticiones(Integer.valueOf(partes[32]));
        aux.setTileable(Boolean.valueOf(partes[33]));
        aux.setPosicion(Integer.valueOf(partes[34]));
        aux.setLayer(layer);
        
        printer.addElemento(aux, layer);
    }
    
}
