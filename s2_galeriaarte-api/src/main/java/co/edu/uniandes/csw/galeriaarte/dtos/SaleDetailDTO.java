/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;

import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author s.acostav
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
            extraServices= new ArrayList<>();
            for (ExtraServiceEntity entityServices : saleEntity.getServices()) 
            {
                extraServices.add(new ExtraServiceDTO(entityServices));
            }
            
        }
    }
    
    @Override
    public SaleEntity toEntity() 
    {
        SaleEntity saleEntity = super.toEntity();
        if (extraServices != null) 
        {
            List<ExtraServiceEntity> extraServicesEntity = new ArrayList<>();
            for (ExtraServiceDTO dtoExtraService : extraServices)
            {
                extraServicesEntity.add(dtoExtraService.toEntity());
            }
            saleEntity.setServices(extraServicesEntity);
        }
        return saleEntity;
    }
    
    public List<ExtraServiceDTO> getServices() 
    {
        return extraServices;
    }

    /**
     * @param kinds the kinds to set
     */
    public void setServices(List<ExtraServiceDTO> services) 
    {
        this.extraServices = services;
    }
    
     @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}


