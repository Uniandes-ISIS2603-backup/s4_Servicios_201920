/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.dtos;

import co.edu.uniandes.csw.servicios.entities.ServicioOfrecidoEntity;
import co.edu.uniandes.csw.servicios.entities.SolicitudServicioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ca.torrese
 */
public class SolicitudServicioDetailDTO extends SolicitudServicioDTO implements Serializable{
    
     // relación  uno o muchos servicios
    private List<ServicioOfrecidoDTO> servicios;

    public SolicitudServicioDetailDTO() {
        super();
    }
    
    /**
     * Crea un objeto SolicitudServicioDetailDTO a partir de un objeto SolicitudServicioEntity
     * incluyendo los atributos de SolicitidServicioDTO.
     *
     * @param solicitudEntity Entidad SolicitudServicioEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public SolicitudServicioDetailDTO(SolicitudServicioEntity solicitudEntity) {
        super(solicitudEntity);
        if (solicitudEntity != null) {
            servicios = new ArrayList<>();
            for (ServicioOfrecidoEntity entityServicios : solicitudEntity.getServicios()) {
                servicios.add(new ServicioOfrecidoDTO(entityServicios));
            }
        }
    }
    
     /**
     * Convierte un objeto SolicitudServicioDetailDTO a SolicitudServicioEntity incluyendo los
     * atributos de SolicitudServicioDTO.
     *
     * @return Nuevo objeto SolicitudServicioEntity.
     *
     */
    @Override
    public SolicitudServicioEntity toEntity() {
        SolicitudServicioEntity solicitudEntity = super.toEntity();
        if (getServicios() != null) {
            List<ServicioOfrecidoEntity> serviciosEntity = new ArrayList<>();
            for (ServicioOfrecidoDTO dtoServicio : getServicios()) {
                serviciosEntity.add(dtoServicio.toEntity());
            }
            solicitudEntity.setServicios(serviciosEntity);
        }
        return solicitudEntity;
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
    
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
