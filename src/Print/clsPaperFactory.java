/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Print;

import java.awt.print.Paper;

/**
 *
 * @author anhor
 */
public class clsPaperFactory {
    public static final String[][] types ={ 
            {"A0","2384", "3370"},
            {"A1","1684", "2384"},
            {"A2","1191", "2384"},
            {"A3","842", "1191"},
            {"A4","595", "842"},
            {"A5","420", "595"},
            {"A6","297", "420"},
    };
    
    public clsPaperFactory(){
        
    }
    
    //FUNCIONES GET & SET
    public int getSize(){
        return types.length;
    }
    public String getName(int type){
        return types[type][0];
    }
    public int getWidth(int type){
        return Integer.valueOf(types[type][1]);
    }
    public int getHeight(int type){
        return Integer.valueOf(types[type][2]);
    }
    public int getID(String nombre){
        int valor =-1;
        for (int i=0;i<types.length;i++){
            if (nombre.equals(types[i][0])){
                valor=i;
                break;
            }
        }
        
        return valor;
    }
    
    public Paper createPaper(String type){
        Paper papel = new Paper();
        int i;
        for (i=0;i<types.length;i++){
            if (types[i][0].equals(type)){
                break;
            }
        }
        
        papel.setSize(Integer.valueOf(types[i][1]), Integer.valueOf(types[i][2]));
        
        return papel;
    }
}
