/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class PaintworkEntity extends BaseEntity implements Serializable
{
   
    private String  name;
    private String  country;
    private String  description;
    private Long    valor;
    private Boolean verificacionObra;
    private String  imagePath;
    private String  videoPath;
    @PodamExclude
    @OneToMany
    private Collection<KindEntity> kind;
    @PodamExclude
    @OneToMany
    private Collection<CategoryEntity> category;
    @PodamExclude
    @OneToMany
    private Collection<FeedBackEntity> feedback;
    @PodamExclude
    @ManyToOne
    private BuyerEntity buyer;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the value
     */
    public Long getValor() {
        return valor;
    }

    /**
     * @param value the value to set
     */
    public void setValor(Long value) {
        this.valor = value;
    }

    /**
     * @return the verificacionObra
     */
    public Boolean getVerificacionObra() {
        return verificacionObra;
    }

    /**
     * @param verificacionObra the verificacionObra to set
     */
    public void setVerificacionObra(Boolean verificacionObra) {
        this.verificacionObra = verificacionObra;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the videoPath
     */
    public String getVideoPath() {
        return videoPath;
    }

    /**
     * @param videoPath the videoPath to set
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }


    
     /**
     * @return the feedback
     */
    public Collection<FeedBackEntity> getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(Collection<FeedBackEntity> feedback) {
        this.feedback = feedback;
    }
    
        /**
     * @return the kind
     */
    public Collection<KindEntity> getKind() {
        return kind;
    }

    /**
     * @param kind the kind to set
     */
    public void setKind(Collection<KindEntity> kind) {
        this.kind = kind;
    }
    
        /**
     * @return the category
     */
    public Collection<CategoryEntity> getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Collection<CategoryEntity> category) {
        this.category = category;
    }

    /**
     * @return the buyer
     */
    public BuyerEntity getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(BuyerEntity buyer) {
        this.buyer = buyer;
    }


    
}
