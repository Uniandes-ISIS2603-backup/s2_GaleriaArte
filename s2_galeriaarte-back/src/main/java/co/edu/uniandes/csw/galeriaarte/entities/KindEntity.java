
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author LauraManrique y ja.penat
 */
@Entity
public class KindEntity extends BaseEntity implements Serializable
{
    
    @PodamExclude
    @ManyToOne
    private PaintworkEntity obra;
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
    
    public PaintworkEntity getObra()
    {
        return this.obra;
    }
    public void setObra(PaintworkEntity pObra)
    {
        this.obra=pObra;
    }
}