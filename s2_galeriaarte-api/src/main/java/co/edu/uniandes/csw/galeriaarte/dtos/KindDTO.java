/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author LauraManrique
 */
public class KindDTO implements Serializable {

    //Atributos
    
    /**
     * Identificación del tipo
     */
    private Long  idType;
    
    /**
     * Nombre
     */
    private String name;
    
    /**
     * Descripción
     */
    private String description;
    
    public  KindDTO()
    {
        
    }
    
    public KindDTO (KindEntity kindEntity)
    {
        if(kindEntity!=null){
       this.idType=kindEntity.getId();
       this.name=kindEntity.getName();
       this.description= kindEntity.getDescription();
     }
    }

    public Long getIdType() {
        return idType;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public KindEntity toEntity(){
        KindEntity kind= new KindEntity();
        kind.setId(this.idType);
        kind.setName(this.name);
        kind.setDescription(this.description);
       
        return kind;
    }
    
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

