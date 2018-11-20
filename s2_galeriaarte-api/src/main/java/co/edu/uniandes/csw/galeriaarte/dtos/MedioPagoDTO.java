/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.MedioPagoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * MedioPagoDTO Objeto de transferencia de datos de la clase Medio de Pago. }
 * Los DTO contienen las representaciones de los JSON
 * que se transfieren entre el cliente y elservidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": long,
 *      "description": String,
 *      "number": long,
 *      "bank": String
 *   }
 * </pre> Por ejemplo un metodo de pago  se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "description": "Tarjeta de crédito",
 *      "number": 23091935,
 *      "bank": "Bancolombia"
 *   }
 *
 * </pre>
 * @author ja.penat
 */
public class MedioPagoDTO  implements Serializable
{
    private Long id ;
    private String description;
    private Long number;
    private String bank;
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public MedioPagoEntity toEntity()
    {
        MedioPagoEntity medioPagoEntity = new MedioPagoEntity();
        medioPagoEntity.setId(this.id);
        medioPagoEntity.setDescription(this.description);
        medioPagoEntity.setBank(this.bank);
        medioPagoEntity.setNumber(this.number);
        return medioPagoEntity;
    }
    
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param medioPagoEntity: Es la entidad que se va a convertir a DTO
     */
    public MedioPagoDTO(MedioPagoEntity medioPagoEntity)
    {
        if (medioPagoEntity != null)
        {
            this.id = medioPagoEntity.getId();
            this.number = medioPagoEntity.getNumber();
            this.description = medioPagoEntity.getDescription();
            this.bank = medioPagoEntity.getBank();
        }
    }
    /**
     * Constructor por defecto
     */
    public MedioPagoDTO()
    {
        
    }
    
    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
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
     * @return the number
     */
    public Long getNumber()
    {
        return number;
    }
    
    /**
     * @param number the number to set
     */
    public void setNumber(Long number)
    {
        this.number = number;
    }
    
    /**
     * @return the bank
     */
    public String getBank()
    {
        return bank;
    }
    
    /**
     * @param bank the bank to set
     */
    public void setBank(String bank)
    {
        this.bank = bank;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
