package co.edu.uniandes.csw.galeriaarte.dtos;
/**
 *
 * @author Anderson Barragan
 */
public class ArtistDTO {
    
    private Long userId;    
    private String name;    
    private CVDTO hojaDeVida;
    
    /**
     * Constructor vacío de la clase <b>Artist</b>
     */
    public ArtistDTO(){}
    
    /**
     * Retorna el id del artista en cuestion
     * @return 
     */
    public Long getId(){
        return userId;
    }
    
    /**
     * retorna el nombre (único) del artista en cuestion
     * @return 
     */
    public String getArtistName(){
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public CVDTO getCV(){
        return hojaDeVida;
    }
   
    /**
     * Modifica el id existente
     * @param pId 
     */
    public void setId(Long pId){
        this.userId=pId;
    }
    
    /**
     * Modifica el nombre actual
     * @param pName 
     */
    public void setName(String pName){
        this.name= pName;
    }
     
    /**
     * modifica la hoja de vida del artista en cuestion
     * @param pCV 
     */
    public void setCV(CVDTO pCV){
        this.hojaDeVida = pCV;
    }
    
    /**
     * aniade una pintura a las pinturas del artista 
     * @param pNewPaintwork 
     */
    public void addPaintwork(PaintworkDTO pNewPaintwork){
        //this.hojaDeVida.addPaint(pNewPaintwork);
    }
    
  
}
