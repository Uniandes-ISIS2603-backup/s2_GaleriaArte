
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author s.restrepos1
 */
public class SaleDTO implements Serializable
{

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
    * identificador de la compra
    */
        private Long id;
        
    /**
    * precio de la compra
    */
	private double price;/**
    * descripcion de la compra
    */
	private String description;
    /**
    * taxes de la compra
    */
	private Double taxes;
    /**

	 /**
    * constructor vacio de la compra
    */
	public SaleDTO()
	{
		
	}
         /**
    * constructor de entidad de la compra
    */
        public SaleDTO( SaleEntity entidad)
        {
            if(entidad!=null)
            {
            this.id= entidad.getId();
            this.price= entidad.getPrice();
            this.description= entidad.getDescription();
            this.taxes= entidad.getTaxes();
            }
        }
	 /**
    * metodo que transforma el dto en una entidad
    */
        public SaleEntity toEntity()
        {
            
        SaleEntity entidad = new SaleEntity();
        entidad.setId(this.getId());
        entidad.setDescription(this.description);
        entidad.setPrice(this.price);
        entidad.setTaxes(this.taxes);
        
        
        return entidad;
        }
        
         /**
    * retorna el precio de la compra
    * @return price
    */
	public double getPrice()
	{
		return price;
	}
	     /**
    * retorna la descripcion de la compra
    * @return description
    */
	public String getDescription()
	{
		return description;
	}
	     /**
    * retorna el tax de la compra
    * @return taxes
    */
	public double getTaxes()
	{
		return taxes;
	}
	     /**
    * establece el precio de la compra
    */
	public void setPrice(double pPrice)
	{
		this.price= pPrice;
	}
              /**
    * establece la descripcion de la compra
    */
	public void setDescription(String pDes)
	{
		this.description= pDes;
	}
              /**
    * establece el tax de la compra
    */
	public void setTaxes(double pTax)
	{
		this.taxes= pTax;
	}
   
         @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}
