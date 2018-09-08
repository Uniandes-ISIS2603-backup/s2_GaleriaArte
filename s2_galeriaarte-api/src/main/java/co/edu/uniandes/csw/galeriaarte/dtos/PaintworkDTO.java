package co.edu.uniandes.csw.galeriaarte.dtos;
import java.io.Serializable;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author aquí irá el usuario/nombre de quien lo tenga que hacer (supongo)
 */
public class PaintworkDTO implements Serializable {
    private Long idPaintwork;
    private String name;
    private String country;
    private KindDTO kind;
    private CategoryDTO category;
    private FeedBackDTO feedback;
    private String description;
    private Long value;
    private Boolean verificacionObra;
    private String imagePath;
    private String videoPath;

    public PaintworkDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param paintwirkEntity: Es la entidad que se va a convertir a DTO
     */
    
    
    public PaintworkDTO (PaintworkEntity paintworkEntity) {
        if (paintworkEntity != null) {
            this.idPaintwork = paintworkEntity.getId();
            this.name = paintworkEntity.getName();
            this.country=paintworkEntity.getCountry();
            this.kind = new KindDTO();
            this.category=new CategoryDTO();
            this.feedback = new FeedBackDTO();
            this.description = paintworkEntity.getDescription();
            this.value = paintworkEntity.getValor();
            this.verificacionObra = paintworkEntity.getVerificacionObra();
            this.imagePath = paintworkEntity.getImagePath();
            this.videoPath = paintworkEntity.getVideoPath();
        }
    }

    
    
    /**
     * @return the idPaintwork
     */
    public Long getIdPaintwork() {
        return idPaintwork;
    }

    /**
     * @param idPaintwork the idPaintwork to set
     */
    public void setIdPaintwork(Long idPaintwork) {
        this.idPaintwork = idPaintwork;
    }

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
     * @return the kind
     */
    public KindDTO getKind() {
        return kind;
    }

    /**
     * @param kind the kind to set
     */
    public void setKind(KindDTO kind) {
        this.kind = kind;
    }

    /**
     * @return the category
     */
    public CategoryDTO getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    /**
     * @return the feedback
     */
    public FeedBackDTO getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(FeedBackDTO feedback) {
        this.feedback = feedback;
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
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
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
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public PaintworkEntity toEntity() {
        PaintworkEntity paintworkEntity = new PaintworkEntity();
        paintworkEntity.setId(this.idPaintwork);
        paintworkEntity.setName(this.name);
        paintworkEntity.setCountry(this.country);
        paintworkEntity.setCategory(this.category.toEntity());
        paintworkEntity.setFeedback(this.feedback.toEntity());
        paintworkEntity.setDescription(this.description);
        paintworkEntity.setImagePath(this.imagePath);
        paintworkEntity.setKind(this.kind.toEntity());
        paintworkEntity.setValor(this.value);
        paintworkEntity.setVerificacionObra(this.verificacionObra);
        paintworkEntity.setVideoPath(this.videoPath);
        return paintworkEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}



