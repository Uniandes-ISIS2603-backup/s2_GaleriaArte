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
     * 
     */
    public ArtistDTO(){}
    
    /**
     * 
     * @return 
     */
    public Long getId(){
        return userId;
    }
    
    /**
     * 
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
     * 
     * @param pId 
     */
    public void setId(Long pId){
        this.id=pId;
    }
    
    /**
     * 
     * @param pName 
     */
    public void setName(String pName){
        this.name= pName;
    }
     
    /**
     * 
     * @param pCV 
     */
    public void setCV(CVDTO pCV){
        this.hojaDeVida = pCV;
    }
    
    /**
     * 
     * @param pNewPaintwork 
     */
    public void addPaintwork(PaintworkDTO pNewPaintwork){
        //this.hojaDeVida.addPaint(pNewPaintwork);
    }
    
    /**
     * 
     * @param pPaint
     * @return 
     */
    public Paintwork getPaintwork(PaintworkDTO pPaint){
        this.hojaDeVida.getPaint(pPaint);
    }
}
