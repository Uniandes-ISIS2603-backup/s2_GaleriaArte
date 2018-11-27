
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
 * @author LauraManrique y ja.penat
 */
@Entity
public class KindEntity extends BaseEntity implements Serializable
{
    
    @PodamExclude
    @ManyToMany
    private List<PaintworkEntity> obra;
    /**
     * Nombre
     */
    private String name;
    
    /**
     * Descripción
     */
    private String description;
    
    public  KindEntity()
    {
        
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription() 
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the obra
     */
    public List<PaintworkEntity> getObra() 
    {
        return obra;
    }

    /**
     * @param obra the obra to set
     */
    public void setObra(List<PaintworkEntity> obra) 
    {
        this.obra = obra;
    }
    
  
}