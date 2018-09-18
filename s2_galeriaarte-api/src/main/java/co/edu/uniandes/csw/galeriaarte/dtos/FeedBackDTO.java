package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 *
 * @author s.restrepos1
 */
public class FeedBackDTO implements Serializable
{
    private Long id;
    
    private String name;
    
    private PaintworkDTO obra;
    
    private UserDTO usuario;
    
    private String comentario;
    
    public FeedBackDTO()
    {
        
    }
    
    public FeedBackDTO(FeedBackEntity entidad)
    {
        if(entidad!=null)
        {
        this.id= entidad.getId();
        this.name= entidad.getName();
         }
        
    }
     public FeedBackEntity toEntity() 
     {
    
        FeedBackEntity entidad = new FeedBackEntity();
        entidad.setId(this.getId());
        entidad.setName(this.getName());
        entidad.setObra(this.obra.toEntity());
        entidad.setComentario(this.comentario);
        //entidad.setUsuario(this.usuario);
        return entidad;
    }
    public Long getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public PaintworkDTO getObra()
    {
        return obra;
    }
    
    public UserDTO getUser()
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
    
    public void setObra(PaintworkDTO pObra)
    {
               this.obra= pObra; 

    }
    
    public void setUsuario(UserDTO pUsuario)
    {
               this.usuario= pUsuario; 

    }
    
    public String getComentario()
    {
        return comentario;
    }
    
    public void setComentario(String pComentario)
    {
        this.comentario= pComentario;
    }
    
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
