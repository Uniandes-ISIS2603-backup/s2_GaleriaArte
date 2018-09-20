
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author LauraManrique
 */
@Entity
public class KindEntity extends BaseEntity implements Serializable{
   

    @PodamExclude   
   @OneToOne(mappedBy="paintwork", fetch=FetchType.LAZY)
    private PaintworkEntity paintwork;  
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
public void setPaintwor(PaintworkEntity pP)
{
    this.paintwork=pP;
}
}