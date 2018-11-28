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
 * @author estudiante
 */
public class ExtraServiceDetailDTO  extends ExtraServiceDTO implements Serializable{
    
    
    public ExtraServiceDetailDTO(){
        super();
    }
    
    public ExtraServiceDetailDTO(ExtraServiceEntity extraService){
        super(extraService);
        
    }
    
    
    
      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
