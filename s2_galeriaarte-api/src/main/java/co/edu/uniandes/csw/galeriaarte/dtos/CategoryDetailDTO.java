/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class CategoryDetailDTO extends CategoryDTO implements Serializable {
     // relación  cero o muchos categorys
    private List<PaintworkDTO> paintworks;
    
     public CategoryDetailDTO() 
    {
        super();
    }
    
     /**
     * Crea un objeto CategoryDetailDTO a partir de un objeto CategoryEntity
     * incluyendo los atributos de CategoryDTO.
     *
     * @param categoryEntity Entidad CategoryEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
      public CategoryDetailDTO(CategoryEntity categoryEntity) 
    {
        super(categoryEntity);
        if (categoryEntity != null)
        {
            paintworks = new ArrayList<>();
            for (PaintworkEntity entityPaintworks : categoryEntity.getObra()) 
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
