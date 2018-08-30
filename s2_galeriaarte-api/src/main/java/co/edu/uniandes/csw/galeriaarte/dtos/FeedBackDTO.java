package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;


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
    
    public FeedBackDTO()
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
    
}
