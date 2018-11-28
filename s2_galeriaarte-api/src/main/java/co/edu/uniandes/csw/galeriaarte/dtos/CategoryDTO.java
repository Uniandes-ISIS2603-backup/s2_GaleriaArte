/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CategoryDTO  implements Serializable
{
    private Long idCategory;
    private String name;
    private String description;
    
    public CategoryDTO(CategoryEntity categoryEntity) {
        if (categoryEntity != null) {
            this.idCategory = categoryEntity.getId();
            this.name = categoryEntity.getName();
        }
    }
    
    public CategoryDTO(){
    }
    


    /**
     * @return the idCategory
     */
    public Long getIdCategory() {
        return idCategory;
    }

    /**
     * @param idCategory the idCategory to set
     */
    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the Description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CategoryEntity toEntity() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(this.idCategory);
        categoryEntity.setName(this.name);
        categoryEntity.setDescription(this.description);
        return categoryEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
