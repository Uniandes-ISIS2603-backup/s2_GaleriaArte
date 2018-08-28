package co.edu.uniandes.csw.galeriaarte.dtos;


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
        
    }
    
    public void setUsuario(UserDTO pUsuario)
    {
        
    }
    
}
