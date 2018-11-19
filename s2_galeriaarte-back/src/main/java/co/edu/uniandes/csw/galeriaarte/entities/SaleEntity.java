/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.Collection;
/**
 *
 * @author s.restrepos1
 */
@Entity
public class SaleEntity extends BaseEntity implements Serializable
{
        private Double price;
	
	private String description;
	
	private Double taxes;
	
        @PodamExclude
        @OneToOne
	private ArtistEntity artist;
	
        @PodamExclude
        @OneToOne
	private PaintworkEntity obra;
        
	@PodamExclude
        @OneToOne
	private MedioPagoEntity metodo;
        
        @PodamExclude
	@OneToMany(mappedBy = "sale")
	private Collection<ExtraServiceEntity> services;
	
        @PodamExclude
        @OneToOne
	private BuyerEntity buyer;
        
       
	
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

    /**
     * @return the services
     */
    public Collection<ExtraServiceEntity> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<ExtraServiceEntity> services) {
        this.services = services;
    }

   
}
