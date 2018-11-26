/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ja.penat
 */
public class KindDetailDTO  extends KindDTO implements Serializable
{ 
    private PaintworkDTO paintwork;

    /**
     * Constructor por defecto
     */
    public KindDetailDTO() 
    {
        super();
    }

    /**
     * Crea un objeto KindDetailDTO a partir de un objeto KindEntity
     * incluyendo los atributos de KindDTO.
     *
     * @param kindEntity Entidad KindEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public KindDetailDTO(KindEntity kindEntity)
    {
        super(kindEntity);
        if (kindEntity.getObra() != null) 
        {
            this.paintwork = new PaintworkDTO(kindEntity.getObra());
        }
    }

    /**
     * Convierte un objeto KindDetailDTO a KindEntity incluyendo los atributos
     * de KindDTO.
     *
     * @return Nueva objeto kindEntity.
     *
     */
    @Override
    public KindEntity toEntity() 
    {
        KindEntity kindEntity = super.toEntity();
        if (getPaintwork() != null) 
        {
            kindEntity.setObra(getPaintwork().toEntity());
        }
        return kindEntity;
    }

    

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the paintwork
     */
    public PaintworkDTO getPaintwork() 
    {
        return paintwork;
    }

    /**
     * @param paintwork the paintwork to set
     */
    public void setPaintwork(PaintworkDTO paintwork)
    {
        this.paintwork = paintwork;
    }
}
