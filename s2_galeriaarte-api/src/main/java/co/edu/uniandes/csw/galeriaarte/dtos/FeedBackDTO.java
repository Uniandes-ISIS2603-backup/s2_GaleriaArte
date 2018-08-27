package co.edu.uniandes.csw.galeriaarte.dtos;

import UserDTO;

/**
 *
 * @author s.restrepos1
 */
public class FeedBackDTO 
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
    
    public ObraDTO getObra()
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
    
    public void setObra(ObraDTO pObra)
    {
        
    }
    
    public void setUsuario(UsuarioDTO pUsuario)
    {
        
    }
    
}
