/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class SaleDetailDTO extends SaleDTO implements Serializable {
    
    private List<ExtraServiceDTO> extraServices;
    
    public SaleDetailDTO(){
        super();
    }
    
    public SaleDetailDTO(SaleEntity saleEntity){
      
        super(saleEntity);
        
        if (saleEntity != null)
        {
            
        }
    }
}
