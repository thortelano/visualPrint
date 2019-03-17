/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Print;

import clasesBase.clsPrint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 *
 * @author toni
 */
public class clsVisor extends JComponent {
    //VARIABLES
    private clsPrint printer;
    private Graphics graphics;
    //VARIALES DE MOVIMIENTO
    private Point desplazamiento1= new Point();
    private Point desplazamiento2= new Point();
    
    //CONTRUCTOR
    clsVisor(clsPrint p){
        super();
        graphics = this.getGraphics();
        printer=p;
        
        inicializarListeners();
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    }
    
    /**
     * ACTUALIZA EL CONTENIDO DEL VISOR
     */
    public void actualizar(){
        this.repaint();
    }
    
    //FUNCIONES DE MOVIMIENTO
    private void centrarObjeto(Point pos, Point size){
        Point cVisor = new Point();
        cVisor.x = (int)(this.getWidth()/2);
        cVisor.y = (int)(this.getHeight()/2);
        
        Point cObjeto= new Point();
        cObjeto.x =(int) ((pos.x+(size.x/2))*printer.getZoom());
        cObjeto.y =(int) ((pos.y+(size.y/2))*printer.getZoom());
        
        Point des = new Point();
        des.x=(int)((cObjeto.x-cVisor.x)/printer.getZoom());
        des.y=(int)((cObjeto.y-cVisor.y)/printer.getZoom());
        
        //des.x=(int)((cObjeto.x-cVisor.x));
        //des.y=(int)((cObjeto.y-cVisor.x));
        
        printer.setDesplazamiento(des);
        
        repaint();
    }
    
    //FUNCIONES GET & SET
    public clsPrint getPrint(){
        return printer;
    }
    
    public void setPrint(clsPrint p){
        printer=p;
    }
    
    //FUNCIONES DE CLICK
    private void buscarObjetoVista(Point e){
        Point p = new Point();
        p.x = (int)(e.getX()/printer.getZoom());
        p.y = (int)(e.getY()/printer.getZoom());
                                
        p.x = p.x-(printer.getPunto0().x+printer.getMargenX());
        p.y = p.y-(printer.getPunto0().y+printer.getMargenY());
                                
        printer.searchClick(p);
        repaint();
    }
    private void buscarObjetoVistaNext(Point e){
        Point p = new Point();
        p.x = (int)(e.getX()/printer.getZoom());
        p.y = (int)(e.getY()/printer.getZoom());
                                
        p.x = p.x-(printer.getPunto0().x+printer.getMargenX());
        p.y = p.y-(printer.getPunto0().y+printer.getMargenY());
                                
        printer.searchClickNext(p);
        repaint();
    }
    private void centrarVista(){
        if (printer.getSelected()!=null){
            Point p = new Point(printer.getPunto0().x + printer.getSelected().getPunto().x , printer.getPunto0().y + printer.getSelected().getPunto().y);
            Point t = new Point(printer.getSelected().getAncho(),printer.getSelected().getAlto());
            centrarObjeto(p,t);
        }else{
            centrarObjeto(printer.getPunto0(),printer.getTamaño());
        }
    }
    
    //FUNCIONES LISTENER
    private void inicializarListeners(){
        this.addMouseListener(inicializarListenerMouse());
        this.addMouseMotionListener(inicializarListenerMotion());
        this.addMouseWheelListener(inicializarListenerRueda());
    }
    
    private MouseListener inicializarListenerMouse(){
        return new MouseListener(){
                        @Override
			public void mouseClicked(MouseEvent e) {
                            if (e.getButton()==MouseEvent.BUTTON1){
                                //BOTON IZQUIERDO
                                if (e.getClickCount()==1){
                                    //PUNTO CLICK
                                    buscarObjetoVista(e.getPoint());
                                }else if (e.getClickCount()==2){
                                    centrarVista();
                                }
                            }else{
                                //BOTON DERECHO
                                if (e.getClickCount()==1){
                                    //PUNTO CLICK
                                    buscarObjetoVistaNext(e.getPoint());
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
                            if (e.getButton()==MouseEvent.BUTTON1){
                                desplazamiento1.setLocation(e.getX(), e.getY());
                            }
			}
                        @Override
			public void mouseReleased(MouseEvent e) {
                            //soltar boton
			}
        };
    }
    private MouseMotionListener inicializarListenerMotion(){
        return new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                //MOVEMOS LA PAGINA
                desplazamiento2.x=(int)((desplazamiento1.x-e.getX())/printer.getZoom());
                desplazamiento2.y=(int)((desplazamiento1.y-e.getY())/printer.getZoom());
                
                printer.setDesplazamiento(desplazamiento2);
                
                desplazamiento1.x=e.getX();
                desplazamiento1.y=e.getY();
                
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    private MouseWheelListener inicializarListenerRueda(){
        return new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                double steps = mwe.getWheelRotation();
                steps= steps/100;
                double zoom = printer.getZoom() - steps;
                
                printer.setZoom(zoom);
                
                repaint();
            }
        };
    }
    
    /*
    @Override
    protected void paintComponent(Graphics grphcs) {
        //super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        grphcs.setColor(Color.LIGHT_GRAY);
        grphcs.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
        
        if (printer!=null)printer.VistaPreliminar(grphcs);
    }*/
    
    
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        
        printer.VistaPreliminar(grphcs);
    }
}
