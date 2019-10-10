/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.podam;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *
 * @author Santiago Cala
 */
public class CorreoStrategy implements AttributeStrategy<String> {
    
    @Override
    public String getValue(){
        String primeraParte = "ljhalkdbei";
        String segundaParte = "lkjadflñj";
        String[] arreglo = new String[4];
        arreglo[0] = ".org";
        arreglo[1] = ".edu";
        arreglo[2] = ".com";
        arreglo[3] = ".gov";
        return primeraParte + "@" + segundaParte + arreglo[(int) (Math.random() * 4)];
    }
}
