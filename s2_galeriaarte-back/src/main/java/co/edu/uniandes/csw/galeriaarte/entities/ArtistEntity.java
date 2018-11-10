package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * Clase que representa un artista en la persistencia y permite su serialización
 *
 * @author Anderson Barragán
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    //@PodamStrategyValue(DateStrategy.class)
    private Date birthDate;

    @PodamExclude
    @OneToOne(mappedBy = "artist",fetch=FetchType.LAZY)
    private CVEntity cv = new CVEntity();
    
    @PodamExclude
    @OneToMany(mappedBy = "artists")
    private List<PaintworkEntity> paintworks = new ArrayList<>();

    private String name;
    private String description;
    private String image;

    /**
     * Devuelve el nombre del artista.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el nombre del artista.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el atributo birthDate.
     *
     * @return atributo birthDate.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Establece el valor del atributo birthDate.
     *
     * @param birthDate nuevo valor del atributo
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Obtiene la colección de paintworks.
     *
     * @return colección paintworks.
     */
    public List<PaintworkEntity> getPaintworks() {
        return paintworks;
    }

    /**
     * Establece el valor de la colección de paintworks.
     *
     * @param paintworks nuevo valor de la colección.
     */
    public void setPaintworks(List<PaintworkEntity> paintworks) {
        this.paintworks = paintworks;
    }

     /**
     * Devuelve la descripción del artista
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifica la descripción del artista
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Devuelve la imagen del artista
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Obtiene la hoja de vida.
     *
     * @return hoja de vida del artista.
     */
    public CVEntity getCV() {
        return cv;
    }
    
    /**
     * Modifica la imagen del artista
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
    public void setCV(CVEntity newCV){
        this.cv = newCV;
    }
}

