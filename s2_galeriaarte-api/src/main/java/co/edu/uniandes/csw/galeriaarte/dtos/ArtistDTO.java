package co.edu.uniandes.csw.galeriaarte.dtos;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Anderson Barragan
 */
public class ArtistDTO implements Serializable{
    
    private Long userId;    
    private String name;    
    private CVDTO hojaDeVida;
    private String image;
    
    /**
     * Constructor vacío de la clase <b>Artist</b>
     */
    public ArtistDTO(){}
    
    /**
     * Crea un objeto ArtistDTO a partir de un objeto ArtistEntity.
     */
    public ArtistDTO(ArtistEntity ArtistEntity) {
        if (ArtistEntity != null) {
            this.userId = ArtistEntity.getId();
            this.name = ArtistEntity.getName();
            this.image = ArtistEntity.getImage();
        }
    }
    
    public ArtistEntity toEntity() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(this.getId());
        artistEntity.setName(this.getName());
        artistEntity.setImage(this.image);
        return artistEntity;
    }
    
    
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
    public String getName(){
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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
