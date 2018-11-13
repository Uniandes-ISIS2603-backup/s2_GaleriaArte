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
    /**
    * identificador de la calificacion
    */
    private Long id;
     /**
    * nombre de la calificacion
    */
    private String name;
     /**
    * obra de la calificacion
    */
    private PaintworkDTO obra;
     /**
    * usuario de la calificacion
    */
    private UserDTO usuario;
     /**
    * comentario de la calificacion
    */
    private String comentario;
     /**
    * constructor vacio de la calificacion
    */
    public FeedBackDTO()
    {
        
    }
     /**
    * constructor de la calificacion
    */
    public FeedBackDTO(FeedBackEntity entidad)
    {
        if(entidad!=null)
        {
        this.id= entidad.getId();
        this.name= entidad.getName();
        this.comentario=entidad.getComentario();
         }
        
    }
     /**
    *  transforma la calificacion dto en una entidad
    */
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
      /**
    * retorna el identificador de la calificacion
    * @return id
    */
    public Long getId()
    {
        return id;
    }
      /**
    * retorna el nombre de la calificacion
    * @return name
    */
    public String getName()
    {
        return name;
    }
     /**
    * retorna la obra de la calificacion
    * @return obra
    */
    public PaintworkDTO getObra()
    {
        return obra;
    }
     /**
    * retorna el usuario de la calificacion
    * @return usuario
    */
    public UserDTO getUser()
    {
      return usuario;
    }
     /**
    * establece el identificador de la calificacion
    */
    public void setId(Long pId)
    {
        this.id=pId;
    }
    /**
    * establece el nombre de la calificacion
    */
    public void setName(String pString)
    {
        this.name= pString;
    }
    /**
    * establece la obra de la calificacion
    */
    public void setObra(PaintworkDTO pObra)
    {
               this.obra= pObra; 

    }
    /**
    * establece el usuario de la calificacion
    */
    public void setUsuario(UserDTO pUsuario)
    {
               this.usuario= pUsuario; 

    }
    /**
    * retorna el comentario de la calificacion
    */
    public String getComentario()
    {
        return comentario;
    }
    /**
    * establece el comentario de la calificacion
    */
    public void setComentario(String pComentario)
    {
        this.comentario= pComentario;
    }
    
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
