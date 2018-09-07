
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.FeedBackEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.io.Serializable;

public class SaleDTO implements Serializable
{
        private Long id;
	private double price;
	
	private String description;
	
	private Double taxes;
	
	private ArtistDTO artist;
	
	private PaintworkDTO obra;
	
	private MedioPagoDTO metodo;
	
	private BuyerDTO buyer;
	
	public SaleDTO()
	{
		
	}
        public SaleDTO( SaleEntity entidad)
        {
            this.id= entidad.getId();
            this.price= entidad.getPrice();
            this.description= entidad.getDescription();
            this.taxes= entidad.getTaxes();
        }
	
        public SaleEntity toEntity() {
        SaleEntity entidad = new SaleEntity();
        entidad.setId(this.id);
        entidad.setObra(this.obra.toEntity());
        entidad.setDescription(this.description);
        entidad.setPrice(this.price);
        entidad.setTaxes(this.taxes);
        //falta artist, medioPago, buyer
        return entidad;
        }
        
	public double getPrice()
	{
		return price;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public double getTaxes()
	{
		return taxes;
	}
	
	public ArtistDTO getArtist()
	{
		return artist;
	}
	
	public PaintworkDTO getObra()
	{
		return obra;
	}
	public MedioPagoDTO getMethod()
	{
		return metodo;
	}
	public BuyerDTO getBuyer()
	{
		return buyer;
	}
	public void setPrice(double pPrice)
	{
		this.price= pPrice;
	}
	public void setDescription(String pDes)
	{
		this.description= pDes;
	}
	public void setTaxes(double pTax)
	{
		this.taxes= pTax;
	}
	public void setArtist(ArtistDTO pArt)
	{
		this.artist= pArt;
	}
	public void setObra(PaintworkDTO pPaint)
	{
		this.obra= pPaint;
	}
	public void setMetodoPago(MedioPagoDTO pMeth)
	
	{
		this.metodo= pMeth;
	}
	
	public void setBuyer(BuyerDTO pBuy)
	{
		this.buyer=pBuy;
	}
	
}
