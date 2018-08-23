/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

/**
 *
 * @author s.restrepos1
 */
public class FeedBackDTO 
{
    private Long id;
    
    private String name;
    
    //private ObraDTO obra;
    
    //private UserDTO usuario;
    
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
    
    //public ObraDTO getObra()
    //{
      //  return null;
    //}
    
      //public UsuarioDTO getObra()
    //{
      //  return null;
    //}
    
    public void setId()
    {
        id=this.id;
    }
    
    public void setName()
    {
        name=this.name;
    }
    
    public void setObra()
    {
        
    }
    
    public void setUsuario()
    {
        
    }
    
}
