/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author LauraManrique
 */
@Entity
public class KindEntity extends BaseEntity implements Serializable{
      // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
        //private Long id;
       
          // @PodamExclude
//@OneToOne(mappedBy="paintwork", fetch=FetchType.LAZY)
//private PaintworkEntity paintwork;  
  /**
     * Identificación del tipo
     */
    //private Long  idType;
    
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

    //public Long getIdType() {
    //    return idType;
    //}

    //public void setIdType(Long idType) {
    //    this.idType = idType;
    //}

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
    public KindEntity toEntity(){
        return null;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
