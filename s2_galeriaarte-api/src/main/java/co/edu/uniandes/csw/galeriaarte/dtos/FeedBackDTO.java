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
