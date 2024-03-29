/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity 
public class FeedBackEntity extends BaseEntity implements Serializable
{
    
    private String name;
    
    private String comentario;
    
    @PodamExclude
    @ManyToOne
    private PaintworkEntity obra;
    

    public String getName()
    {
        return name;
    }
    
    public PaintworkEntity getObra()
    {
        return obra;
    }
   
    public void setName(String pString)
    {
        this.name= pString;
    }
    
    public void setObra(PaintworkEntity pObra)
    {
       this.obra= pObra; 
    }
    
  
    
    public String  getComentario()
    {
      return comentario;  
    }
   
    public void setComentario(String pComentario)
    {
        this.comentario= pComentario;
    }
      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
