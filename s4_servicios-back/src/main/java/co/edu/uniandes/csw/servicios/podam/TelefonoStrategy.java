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
public class TelefonoStrategy implements AttributeStrategy<Integer>{
    
    @Override
    public Integer getValue(){
        int numero;
        numero = (int)(Math.random()*99999999);
        if(numero < 0){
            numero = -1*(numero);
        }
        return numero;
    }
}
