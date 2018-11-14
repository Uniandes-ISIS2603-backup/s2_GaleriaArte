/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.ExtraServiceEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ExtraServiceDTO Objeto de transferencia de datos de la clase ExtraService. }
 * Los DTO contienen las representaciones de los JSON
 * que se transfieren entre el cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": long,
 *      "name": String,
 *      "description": String,
 *      "price": double,
 *      "availability" : boolean
 *
 *   }
 * </pre> Por ejemplo un metodo de pago  se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "long" : id,
 *      "name": "Enmarcado",
 *      "description": "Se usa un marco de madera donde se pondra la obra de arte",
 *      "price": 100000,
 *      "availability" : true
 *   }
 *
 * </pre>
 * @author ja.penat
 */
public class ExtraServiceDTO
{
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean availability;
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param extraServiceEntity: Es la entidad que se va a convertir a DTO
     */
    public ExtraServiceDTO(ExtraServiceEntity extraServiceEntity)
    {
        if (extraServiceEntity != null) 
        {
            this.id = extraServiceEntity.getId();
            this.name = extraServiceEntity.getName();
            this.price = extraServiceEntity.getPrice();
            this.availability = extraServiceEntity.getAvailability();
            this.description = extraServiceEntity.getName();
        }
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }
    
    /**
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    /**
     * @return the availability
     */
    public boolean getAvailability()
    {
        return availability;
    }
    
    /**
     * @param availability the availability to set
     */
    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ExtraServiceEntity toEntity()
    {
        ExtraServiceEntity extraServiceEntity = new ExtraServiceEntity();
        extraServiceEntity.setId(this.id);
        extraServiceEntity.setName(this.name);
        extraServiceEntity.setPrice(this.price);
        extraServiceEntity.setAvailability(this.availability);
        extraServiceEntity.setDescription(this.description);
        return extraServiceEntity;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}
