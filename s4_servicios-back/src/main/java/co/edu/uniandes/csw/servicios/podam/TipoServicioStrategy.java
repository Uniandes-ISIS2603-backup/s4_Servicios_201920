/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.podam;

import uk.co.jemos.podam.common.AttributeStrategy;

/**
 *
 * @author Estudiante
 */
public class TipoServicioStrategy implements AttributeStrategy<String>{

    @Override
    public String getValue() {
         int r = (int)(Math.random() * 7) + 1;
        switch (r) {
            case 1:
                return "Pintura";
            case 2:
                return  "Plomeria";
            case 3:
                return "Electricidad";
            case 4:
                return  "Cerrajeria";
            case 5:
                return  "Ventaneria";
            case 6:
                return  "Carpinteria";
            default:
                return  "Aseo";
        }
    }
    
}
