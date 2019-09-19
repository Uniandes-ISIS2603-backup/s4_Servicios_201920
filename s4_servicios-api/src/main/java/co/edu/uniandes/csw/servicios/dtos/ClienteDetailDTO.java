/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ClienteEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable{
    
    private List<Object> servicios;
    
    public ClienteDetailDTO(){
        super();
    }
    
    public ClienteDetailDTO(ClienteEntity clienteEntity){
        super(clienteEntity);
        if(clienteEntity != null){
            servicios = new ArrayList<Object>();
            
        }
    }
    
}
