/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class ExtraServiceDetailDTO  extends ExtraServiceDTO implements Serializable{
    
    public ExtraServiceDetailDTO(){
        super();
    }
    
    public ExtraServiceDetailDTO(ExtraServiceEntity extraService){
        super(extraService);
    }
}
