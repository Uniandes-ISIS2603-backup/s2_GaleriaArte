/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.ejb;

import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jf.copete
 */
public class PaintworkLogic {
    
        private static final Logger LOGGER = Logger.getLogger(PaintworkLogic.class.getName());

    
    /**
     * Crea una editorial en la persistencia.
     *
     * @param paintwork La entidad que representa la editorial a
     * persistir.
     * @return La entiddad de la editorial luego de persistirla.
     * @throws BusinessLogicException Si la editorial a persistir ya existe.
     */
    public PaintworkEntity createPaintWork(PaintworkEntity paintwork) throws BusinessLogicException {
        /*
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la editorial");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (persistence.findByName(paintwork.getName()) != null) {
            throw new BusinessLogicException("Ya existe una Editorial con el nombre \"" + paintwork.getName() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(paintwork);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la editorial");
        */
        return paintwork;
        
    }

    /**
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<PaintworkEntity> getEditorials() {
        
        /*LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las editoriales");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<EditorialEntity> editorials = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las editoriales");
        */
        return null;
        
    }

    
}
