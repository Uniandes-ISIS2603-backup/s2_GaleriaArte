
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
    * artista de la compra
    */
	private ArtistDTO artist;
	 /**
    * obra de la compra
    */
	private PaintworkDTO obra;
	 /**
    * metodo de pago de la compra
    */
	private MedioPagoDTO metodo;
	 /**
    * comprador de la compra
    */
	private BuyerDTO buyer;
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
        entidad.setId(this.id);
        entidad.setObra(this.obra.toEntity());
        entidad.setDescription(this.description);
        entidad.setPrice(this.price);
        entidad.setTaxes(this.taxes);
        entidad.setArtist(this.artist.toEntity());
        entidad.setMetodoPago(this.metodo.toEntity());
        entidad.setBuyer(this.buyer.toEntity());
        
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
    * retorna el artista de la compra
    * @return artist
    */
	public ArtistDTO getArtist()
	{
		return artist;
	}
	     /**
    * retorna la obra de la compra
    * @return obra
    */
	public PaintworkDTO getObra()
	{
		return obra;
	}
             /**
    * retorna el metodo de pago de la compra
    * @return metodo
    */
	public MedioPagoDTO getMethod()
	{
		return metodo;
	}
             /**
    * retorna el comprador de la compra
    * @return buyer
    */
	public BuyerDTO getBuyer()
	{
		return buyer;
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
              /**
    * establece el artista de la compra
    */
	public void setArtist(ArtistDTO pArt)
	{
		this.artist= pArt;
	}
              /**
    * establece la obra de la compra
    */
	public void setObra(PaintworkDTO pPaint)
	{
		this.obra= pPaint;
	}
              /**
    * establece el metodo de pago de la compra
    */
	public void setMetodoPago(MedioPagoDTO pMeth)
	
	{
		this.metodo= pMeth;
	}
	/**
    * establece el comprador de la compra
    */
	public void setBuyer(BuyerDTO pBuy)
	{
		this.buyer=pBuy;
	}
        
         @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}
