/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.servicios.ejb;

import co.edu.uniandes.csw.servicios.entities.ReporteMensualEntity;
import co.edu.uniandes.csw.servicios.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.servicios.persistence.ReporteMensualPersistence;
import javax.inject.Inject;

/**
 *
 * @author Violeta Rodríguez
 */
public class ReporteMensualLogic {
    
    @Inject
    private ReporteMensualPersistence persistencia;
    
    public ReporteMensualEntity createReporteMensual(ReporteMensualEntity entity) throws BusinessLogicException
    {
       return null;
    }
    
    
}
