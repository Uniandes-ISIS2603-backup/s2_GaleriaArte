/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
@Entity 
public class FeedBackEntity extends BaseEntity implements Serializable
{
    private Long id;
    
    private String name;
    
    private PaintworkEntity obra;
    
    private UserEntity usuario;
    
    public FeedBackEntity()
    {
        
    }
    public Long getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public PaintworkEntity getObra()
    {
        return obra;
    }
    
    public UserEntity getUser()
    {
      return usuario;
    }
    
    public void setId(Long pId)
    {
        this.id=pId;
    }
    
    public void setName(String pString)
    {
        this.name= pString;
    }
    
    public void setObra(PaintworkEntity pObra)
    {
       this.obra= pObra; 
    }
    
    public void setUsuario(UserEntity pUsuario)
    {
        this.usuario= pUsuario;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PaintworkEntity toEntity() {
        PaintworkEntity paintworkEntity = new PaintworkEntity();
        paintworkEntity.setId(this.id);
        paintworkEntity.setName(this.name);
        return paintworkEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
