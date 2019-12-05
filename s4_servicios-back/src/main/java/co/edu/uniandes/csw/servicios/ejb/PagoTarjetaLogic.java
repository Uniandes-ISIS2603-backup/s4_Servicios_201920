/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.persistence.PagoTarjetaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Estudiante
 */
@Stateless
public class PagoTarjetaLogic {
    
    @Inject
    private PagoTarjetaPersistence persistence;
    
    public PagoTarjetaEntity createPagoTarjeta(PagoTarjetaEntity pagoTarjeta){
        return persistence.create(pagoTarjeta);
    }
    
    public List<PagoTarjetaEntity> getTarjetas() {
        return persistence.findAll();
    }
    
    public PagoTarjetaEntity getTarjeta(Long idTarjeta) {
        return persistence.find(idTarjeta);
    }
    
        
    public PagoTarjetaEntity updatePagoTarjeta(PagoTarjetaEntity tarjetaEntity){
        return persistence.update(tarjetaEntity);
    }
    
    public void deletePagoTarjeta(Long idTarjeta) {
        persistence.remove(idTarjeta);
    }
}
