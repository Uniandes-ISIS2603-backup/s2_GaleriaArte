package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;

public class SaleDTO implements Serializable
{
	private double price;
	
	private String description;
	
	private double taxes;
	
	private ArtistDTO artist;
	
	private PaintworkDTO obra;
	
	private MedioPagoDTO metodo;
	
	private BuyerDTO buyer;
	
	public SaleDTO()
	{
		
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
