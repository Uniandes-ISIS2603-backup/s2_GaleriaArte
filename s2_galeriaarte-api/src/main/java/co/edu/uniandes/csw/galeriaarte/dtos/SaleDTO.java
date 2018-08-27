package co.edu.uniandes.csw.galeriaarte.dtos;

public class SaleDTO 
{
	private double price;
	
	private String description;
	
	private double taxes;
	
	private ArtistDTO artist;
	
	private PaintWorkDTO obra;
	
	private MetodoPagoDTO metodo;
	
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
	
	public PaintWorkDTO getObra()
	{
		return obra;
	}
	public MetodoPagoDTO getMethod()
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
	public void setObra(PaintWorkDTO pPaint)
	{
		this.obra= pPaint;
	}
	public void setMetodoPago(MetodoPagoDTO pMeth)
	
	{
		this.metodo= pMeth;
	}
	
	public void setBuyer(BuyerDTO pBuy)
	{
		this.buyer=pBuy;
	}
	
}
