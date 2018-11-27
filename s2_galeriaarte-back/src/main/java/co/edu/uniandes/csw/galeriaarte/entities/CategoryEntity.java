/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author jf.copete
 */
@Entity
public class CategoryEntity extends BaseEntity implements Serializable{
    
    private String name;
    private String Description;
    
    @PodamExclude
    @ManyToMany
    private List<PaintworkEntity> obra;

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
        return Description;
    }

    /**
     * @param Description the Description to set
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return the obra
     */
    public List<PaintworkEntity> getObra() {
        return obra;
    }

    /**
     * @param obra the obra to set
     */
    public void setObra(List<PaintworkEntity> obra) {
        this.obra = obra;
    }

   
    
    
}
