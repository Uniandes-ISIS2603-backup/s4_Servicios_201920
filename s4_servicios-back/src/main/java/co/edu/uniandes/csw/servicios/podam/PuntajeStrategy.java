/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.podam;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * 
 * @author ca.torrese
 */
public class PuntajeStrategy implements AttributeStrategy<Integer>{

    @Override
    public Integer getValue() {
        return (int)(Math.random() * 5) + 1;
    }
    
}
