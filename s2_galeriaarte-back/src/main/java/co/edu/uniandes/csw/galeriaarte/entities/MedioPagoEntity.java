/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un medio de pago  en la persistencia y permite su
 * serialización.
 *
 * @author ja.penat
 */
@Entity
public class MedioPagoEntity extends BaseEntity implements Serializable
{
    
    private String description;
    private Long number;
    private String bank;
    @PodamExclude
    @OneToOne	
    private SaleEntity sale;

    
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

    /**
     * @return the sale
     */
    public SaleEntity getSale() {
        return sale;
    }

    /**
     * @param sale the sale to set
     */
    public void setSale(SaleEntity sale) {
        this.sale = sale;
    }
      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
