/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import co.edu.uniandes.csw.servicios.entities.TrabajadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author c.otalora
 */
public class TrabajadorDetailDTO extends TrabajadorDTO implements Serializable{
    private List<SolicitudServicioDTO> solicitudes;
    private List<ServicioOfrecidoDTO> servicios;
    
    public TrabajadorDetailDTO(){
        super();
    }
    
    public TrabajadorDetailDTO(TrabajadorEntity trabajadorEntity){
        super(trabajadorEntity);
        if(trabajadorEntity.getSolicitudes() != null){
            solicitudes = new ArrayList<>();
            for(SolicitudServicioEntity solicitud : trabajadorEntity.getSolicitudes()){
                solicitudes.add(new SolicitudServicioDTO(solicitud));
            }
        }
        if(trabajadorEntity.getServicios() != null)
        {
            servicios = new ArrayList<>();
            for(ServicioOfrecidoEntity servicio : trabajadorEntity.getServicios()){
                servicios.add(new ServicioOfrecidoDTO(servicio));
            }
        }
        
    }
    
    @Override
    public TrabajadorEntity toEntity(){
        TrabajadorEntity trabajadorEntity = super.toEntity();
        if(getSolicitudes() != null){
            List<SolicitudServicioEntity> solicitudesEntity = new ArrayList<>();
            for(SolicitudServicioDTO solicitud : getSolicitudes()){
                solicitudesEntity.add(solicitud.toEntity());
            }
                    trabajadorEntity.setSolicitudes(solicitudesEntity);
        }
         if(getServicios() != null){
            List<ServicioOfrecidoEntity> serviciosEntity = new ArrayList<>();
            for(ServicioOfrecidoDTO servicio : getServicios()){
                serviciosEntity.add(servicio.toEntity());
            }
                    trabajadorEntity.setServicios(serviciosEntity);
        }
         
        return trabajadorEntity;
    }

    /**
     * @return the solicitudes
     */
    public List<SolicitudServicioDTO> getSolicitudes() {
        return solicitudes;
    }

    /**
     * @param solicitudes the solicitudes to set
     */
    public void setSolicitudes(List<SolicitudServicioDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }

    /**
     * @return the servicios
     */
    public List<ServicioOfrecidoDTO> getServicios() {
        return servicios;
    }

    /**
     * @param servicios the servicios to set
     */
    public void setServicios(List<ServicioOfrecidoDTO> servicios) {
        this.servicios = servicios;
    }

    
 
}