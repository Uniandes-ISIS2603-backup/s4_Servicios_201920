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
        /**
        
        //PrecioMateriales > 0
        if(factura.getPrecioMateriales()<0){
            throw new BusinessLogicException("El precio de los materiales no puede ser negativo.");
        }
        // fecha != null
        if(factura.getFecha()==null){
            throw new BusinessLogicException("La factura debe tener una fecha de finalizacion.");
        }
        // primerPago = true
        if(factura.isPrimerPago()==false){
            throw new BusinessLogicException("No se puede crear la factura sin recibir el primer pago.");
        }
        // pagada = false
        if(factura.isPagada()==true){
            throw new BusinessLogicException("La factura no puede estar pagada al momento de su creacion.");
        }
        // solicitud != null
        if(factura.getSolicitud()==null){
            throw new BusinessLogicException("La factura debe tener una solicitud asociada.");
        }
        // tarjetaPago != null
        if(factura.getTarjetaPago() == null){
            throw new BusinessLogicException("Debe poseer una tarjeta");
        }
        *  */
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
