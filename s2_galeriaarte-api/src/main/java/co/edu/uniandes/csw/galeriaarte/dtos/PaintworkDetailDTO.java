/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.CategoryEntity;
import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.KindEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que extiende de {@link PaintworkDTO} para manejar las relaciones entre los
 * FeedbackDTO y otros DTOs. Para conocer el contenido de un paintwork vaya a la
 * documentacion de {@link PaintworkDTO}
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name": string,
 *      "birthDate": date,
 *      "image": string,
 *      "books": [{@link BookDTO}]
 *   }
 * </pre> Por ejemplo un autor se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "name": "Gabriel García Márquez",
 *      "birthDate": "23091935",
 *      "image": "mifoto.com",
 *      "books" : [
 *          {
 *              "id" : 1,
 *              "name" : "Cien años de soledad",
 *              "description": "El libro se compone de 20 capítulos no titulados, en los cuales se narra una historia con una estructura cíclica temporal, puesto que los acontecimientos del pueblo y de la familia Buendía, así como los nombres de los personajes, se repiten una y otra vez, fusionando la fantasía con la realidad.",
 *              "isbn" : "0307474720",
 *              "image" : "http://goo.gl/IWNdCX",
 *              "publishDate" : "01071967"
 *          },
 *          {
 *              "id" : 2,
 *              "name" : "El coronel no tiene quien le escriba",
 *              "description" : "...",
 *              "isbn" : "0307474721",
 *              "image" : "http://goo.gl/IWNdCX",
 *              "publishDate" : "01071967"
 *          }
 *      ] ,
 *      "prizes" : [
 *              "id" : 3,
 *              "name" : "Premio Nobel",
 *              "description": "Premio dado al mejor escritor del aÃ±o",
 *              "fechaEntrega": "1927-03-03T00:00:00-05:00",
 *      ]
 *   }
 *
 * </pre>
 *
 * @author ISIS2603
 */

public class PaintworkDetailDTO extends PaintworkDTO implements Serializable 
{
     // relación  cero o muchos kinds
    private List<KindDTO> kinds;

    // relación  cero o muchos feedbacks 
    private List<FeedBackDTO> feedBacks;

    // relacion cero o mucbos categories.
    private List<CategoryDTO> categories;
    
    public PaintworkDetailDTO() 
    {
        super();
    }
    
     /**
     * Crea un objeto PaintworkDetailDTO a partir de un objeto PaintworkEntity
     * incluyendo los atributos de PaintworkDTO.
     *
     * @param paintworkEntity Entidad PaintworkEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public PaintworkDetailDTO(PaintworkEntity paintworkEntity) 
    {
        super(paintworkEntity);
        if (paintworkEntity != null)
        {
            feedBacks = new ArrayList<>();
            for (FeedBackEntity entityFeedBacks : paintworkEntity.getFeedback()) 
            {
                feedBacks.add(new FeedBackDTO(entityFeedBacks));
            }
            categories = new ArrayList();
            for (CategoryEntity entityCategory : paintworkEntity.getCategory())
            {
                categories.add(new CategoryDTO(entityCategory));
            }
            kinds = new ArrayList<>();
            for(KindEntity entityKinds: paintworkEntity.getKind())
            {
                kinds.add(new KindDTO(entityKinds));
            }
        }
    }

   /**
     * Convierte un objeto PaintworkDetailDTO a PaintworkEntity incluyendo los
     * atributos de PaintworkDTO.
     *
     * @return Nueva objeto PaintworkEntity.
     *
     */
    @Override
    public PaintworkEntity toEntity() 
    {
        PaintworkEntity paintworkEntity = super.toEntity();
        if (feedBacks != null) 
        {
            List<FeedBackEntity> feedBacksEntity = new ArrayList<>();
            for (FeedBackDTO dtoFeedBack : feedBacks)
            {
                feedBacksEntity.add(dtoFeedBack.toEntity());
            }
            paintworkEntity.setFeedback(feedBacksEntity);
        }
        if (categories != null) 
        {
            List<CategoryEntity> categoriesEntity = new ArrayList<>();
            for (CategoryDTO dtoCategory : categories) 
            {
                categoriesEntity.add(dtoCategory.toEntity());
            }
            paintworkEntity.setCategory(categoriesEntity);
        }
        if (kinds != null) 
        {
            List<KindEntity> kindsEntity = new ArrayList<>();
            for (KindDTO dtoKind : kinds) 
            {
                kindsEntity.add(dtoKind.toEntity());
            }
            paintworkEntity.setKind(kindsEntity);
        }
        return paintworkEntity;
    }

    /**
     * @return the kinds
     */
    public List<KindDTO> getKinds() 
    {
        return kinds;
    }

    /**
     * @param kinds the kinds to set
     */
    public void setKinds(List<KindDTO> kinds) 
    {
        this.kinds = kinds;
    }

    /**
     * @return the feedBacks
     */
    public List<FeedBackDTO> getFeedBacks() 
    {
        return feedBacks;
    }

    /**
     * @param feedBacks the feedBacks to set
     */
    public void setFeedBacks(List<FeedBackDTO> feedBacks) 
    {
        this.feedBacks = feedBacks;
    }

    /**
     * @return the categories
     */
    public List<CategoryDTO> getCategories() 
    {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<CategoryDTO> categories) 
    {
        this.categories = categories;
    }
    
     @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
