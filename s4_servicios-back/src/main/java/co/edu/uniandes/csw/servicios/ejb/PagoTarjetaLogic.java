/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.PagoTarjetaEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
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
    
    public PagoTarjetaEntity createPagoTarjeta(PagoTarjetaEntity pagoTarjeta) throws BusinessLogicException{
        pagoTarjeta = persistence.create(pagoTarjeta);
        return pagoTarjeta;
    }
    
    public List<PagoTarjetaEntity> getTarjetas() {
        List<PagoTarjetaEntity> lista = persistence.findAll();
        return lista;
    }
    
    public PagoTarjetaEntity getTarjeta(Long idTarjeta) {
        PagoTarjetaEntity tarjetaEntity = persistence.find(idTarjeta);
        return tarjetaEntity;
    }
    
        
    public PagoTarjetaEntity updatePagoTarjeta(Long idTarjeta, PagoTarjetaEntity tarjetaEntity) throws BusinessLogicException{
        PagoTarjetaEntity newTarjetaEntity = persistence.update(tarjetaEntity);
        return newTarjetaEntity;
    }
    
    public void deletePagoTarjeta(Long idTarjeta) {
        persistence.remove(idTarjeta);
    }
}
