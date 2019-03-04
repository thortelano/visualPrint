/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

/**
 *
 * @author anhor
 */
public class clsIdioma {
    public static final String LANGUAGE_SPANISH="ESPAÑOL";
    public static final String LANGUAGE_ENGLISH="INGLES";
    
    private String idiomaActual;
    
    public clsIdioma(){
        inicializar();
    }
    
    private void inicializar(){
        idiomaActual=LANGUAGE_SPANISH;
    }
    
}
