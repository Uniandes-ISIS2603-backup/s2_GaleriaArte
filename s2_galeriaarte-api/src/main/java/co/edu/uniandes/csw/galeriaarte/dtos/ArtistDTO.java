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
    
    private Long id;
    private String name;    
    //private CVDTO hojaDeVida;
    private String image;
    
    /**
     * Constructor vacío de la clase <b>Artist</b>
     */
    public ArtistDTO(){}
    
    /**
     * Crea un objeto ArtistDTO a partir de un objeto ArtistEntity.
     */
    public ArtistDTO(ArtistEntity artistEntity) {
        if (artistEntity != null) {
            this.name = artistEntity.getName();
            this.image = artistEntity.getImage();
            this.id = artistEntity.getId();
        }
    }
    
    public ArtistEntity toEntity() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(this.id);
        artistEntity.setName(this.name);
        artistEntity.setImage(this.image);
        return artistEntity;
    }
    
    
    /**
     * retorna el nombre (único) del artista en cuestion
     * @return 
     */
    public String getName(){
        return name;
    }
    
    public Long getid(){
        return this.id;
    }
    public void setid(Long newId){
        this.id = newId;
    }
    /**
     * 
     * @return 
     */
    public CVDTO getCV(){
        return null;//hojaDeVida;
    }

    /**
     * Modifica el nombre actual
     * @param pName 
     */
    public void setName(String pName){
        this.name= pName;
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
