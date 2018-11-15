/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.ArtistEntity;
import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 *
 * @author a.barragan Anderon Barragan Agudelo
 */
public class ArtistDetailDTO extends ArtistDTO implements Serializable {

    // relación  cero o muchas paintworks
    private List<PaintworkDTO> paintworks;

    // relación  con su hoja de vida
    private CVDTO cv;

    public ArtistDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ArtistDetailDTO a partir de un objeto ArtistEntity
     * incluyendo los atributos de ArtistDTO.
     *
     * @param artistEntity Entidad ArtistEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ArtistDetailDTO(ArtistEntity artistEntity) {
        super(artistEntity);
        if (artistEntity != null) {
            paintworks = new ArrayList<>();
            for (PaintworkEntity entityPaintworks : artistEntity.getPaintworks())
                paintworks.add(new PaintworkDTO(entityPaintworks));
            
            cv = new CVDTO(artistEntity.getCV());
        }
    }

    
    /**
     * Convierte un objeto ArtistDetailDTO a ArtistEntity incluyendo los
     * atributos de ArtistDTO.
     *
     * @return Nueva objeto ArtistEntity.
     *
     */
    @Override
    public ArtistEntity toEntity() {
        ArtistEntity artistEntity = super.toEntity();
        
        if(artistEntity != null){
        if (paintworks != null) {
            List<PaintworkEntity> paintworksEntity = new ArrayList<>();
            for (PaintworkDTO dtoPaintwork : paintworks) {
                paintworksEntity.add(dtoPaintwork.toEntity());
            }
            artistEntity.setPaintworks(paintworksEntity);
        }

        if (cv != null) {
            CVEntity cvEntity = cv.toEntity();
            artistEntity.setCV(cvEntity);
        }
        
        }
        return artistEntity;
    }

    /**
     * Obtiene la lista de paintworks del artista
     *
     * @return the paintworks
     */
    public List<PaintworkDTO> getPaintworks() {
        return paintworks;
    }

    /**
     * Modifica la lista de libros para el artista
     *
     * @param paintworks the paintworks to set
     */
    public void setPaintworks(List<PaintworkDTO> paintworks) {
        this.paintworks = paintworks;
    }

    /**
     * Obtiene la hoja de vida del artista
     *
     * @return the CV
     */
    public CVDTO getCv() {
        return cv;
    }

    /**
     * Modifica la hoja de vida para el artista
     *
     * @param cv the CV to set
     */
    public void setCv(CVDTO cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
