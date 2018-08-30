/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;

/**
 *
 * @author s.restrepos1
 */
public class SaleEntity extends BaseEntity implements Serializable
{
        private double price;
	
	private String description;
	
	private double taxes;
	
	private ArtistEntity artist;
	
	private PaintworkEntity obra;
	
	private MedioPagoEntity metodo;
	
	private BuyerEntity buyer;
        
        public SaleEntity()
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
	
	public ArtistEntity getArtist()
	{
		return artist;
	}
	
	public PaintworkEntity getObra()
	{
		return obra;
	}
	public MedioPagoEntity getMethod()
	{
		return metodo;
	}
	public BuyerEntity getBuyer()
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
	public void setArtist(ArtistEntity pArt)
	{
		this.artist= pArt;
	}
	public void setObra(PaintworkEntity pPaint)
	{
		this.obra= pPaint;
	}
	public void setMetodoPago(MedioPagoEntity pMeth)
	
	{
		this.metodo= pMeth;
	}
	
	public void setBuyer(BuyerEntity pBuy)
	{
		this.buyer=pBuy;
	}
}
