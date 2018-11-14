package co.edu.uniandes.csw.galeriaarte.dtos;
import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author a.barragan Anderson Barragan
 */
public class ArtistDTO implements Serializable{
    
    private Long id;
    private String name;
    private Date birthDate;
    private String description;
    private String image;
    
    /**
     * Constructor vacio
     */
    public ArtistDTO() {
    }

    /**
     * Crea un objeto ArtistDTO a partir de un objeto ArtistEntity.
     *
     * @param artistEntity Entidad ArtistEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ArtistDTO(ArtistEntity artistEntity) {
        if (artistEntity != null) {
            this.id = artistEntity.getId();
            this.name = artistEntity.getName();
            this.birthDate = artistEntity.getBirthDate();
            this.description = artistEntity.getDescription();
            this.image = artistEntity.getImage();
        }
    }

    /**
     * Convierte un objeto ArtistDTO a ArtistEntity.
     *
     * @return Nueva objeto ArtistEntity.
     *
     */
    public ArtistEntity toEntity() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(this.getId());
        artistEntity.setName(this.getName());
        artistEntity.setBirthDate(this.getBirthDate());
        artistEntity.setDescription(this.description);
        artistEntity.setImage(this.image);
        return artistEntity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el atributo name.
     *
     * @return atributo name.
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el valor del atributo name.
     *
     * @param name nuevo valor del atributo
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el atributo birthDate.
     *
     * @return atributo birthDate.
     *
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Establece el valor del atributo birthDate.
     *
     * @param birthdate nuevo valor del atributo
     *
     */
    public void setBirthDate(Date birthdate) {
        this.birthDate = birthdate;
    }

    /**
     * Obtiene el atributo descripción
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ontiene el atributo de imagen
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Establece el atributo de descripción
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Establece la imagen del autor
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
