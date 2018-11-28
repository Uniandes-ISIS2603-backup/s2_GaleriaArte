/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author ja.penat
 */
public class KindDetailDTO  extends KindDTO implements Serializable
{ 
      // relación  cero o muchos kinds
    private List<PaintworkDTO> paintworks;
    
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
        if (kindEntity != null)
        {
            paintworks = new ArrayList<>();
            for (PaintworkEntity entityPaintworks : kindEntity.getObra()) 
            {
                paintworks.add(new PaintworkDTO(entityPaintworks));
            }
        }
    }

    /**
     * @return the paintworks
     */
    public List<PaintworkDTO> getPaintworks() {
        return paintworks;
    }

    /**
     * @param paintworks the paintworks to set
     */
    public void setPaintworks(List<PaintworkDTO> paintworks) {
        this.paintworks = paintworks;
    }
    
      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
