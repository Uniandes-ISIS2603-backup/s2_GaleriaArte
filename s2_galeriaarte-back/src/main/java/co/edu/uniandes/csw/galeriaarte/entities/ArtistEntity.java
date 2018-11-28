/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import co.edu.uniandes.csw.galeriaarte.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author a.barragan Anderson Barragan
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable
{
    @PodamExclude
    @OneToOne(mappedBy="artist", fetch=FetchType.LAZY)
    private CVEntity cv;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date birthDate;
    
    @PodamExclude
    @OneToMany(mappedBy="artist")
    private List<PaintworkEntity> paintworks;
    
    private String name;
    private String description;
    private String image;

    /**
     * Devuelve el nombre del artista.
     *
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Modifica el nombre del artista.
     *
     * @param name the name to set
     */
    public void setName(String name) 
    {
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
     * Obtiene la colección de premios.
     *
     * @return colección cv.
     */
    public CVEntity getCV() {
        return cv;
    }

    /**
     * Establece el valor de la colección de cv.
     *
     * @param cv nuevo valor de la colección.
     */
    public void setCV(CVEntity cv) {
        this.cv = cv;
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
     * Modifica la imagen del artista
     *
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
}
