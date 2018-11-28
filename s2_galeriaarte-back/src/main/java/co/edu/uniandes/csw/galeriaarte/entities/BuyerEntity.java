/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author s.acostav
 */
@Entity
public class BuyerEntity extends BaseEntity implements Serializable
{
   private  String name;
   private  String password;
    private String usuario;
    private String email;
    private String phone;
    private String creditcard;
    private String country;
    
    @PodamExclude
    @OneToMany(mappedBy = "buyer")
    private List<SaleEntity> sales;
    @PodamExclude
    @OneToMany (mappedBy = "buyer")
    private List<PaintworkEntity> paintworks;
    private String adress;
    
 
    public String getName(){
        return this.name;
    }
    

    public void setName(String nName){
        this.name = nName;
    }
 
    public String getCountry(){
    return this.country;
    }
    public String getCreditCard(){
        return this.creditcard;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getPhone(){
        return this.phone;
    }
    
    public String getAdress(){
        return adress;
    }
    

    
    public List<SaleEntity> getSales(){
        return sales;
    }

    
    public List<PaintworkEntity> getPaintworks( ){
        return paintworks;
    }    

    public void setAdress(String nAdress){
        this.adress = nAdress;
    }
    
    public void setCountry(String nCountry){
     country = nCountry;
    }
    public void setCreditCard(String nCreditCard){
        creditcard = nCreditCard;
    }
    
    public void setEmail(String nEmail){
        email = nEmail;
    }
    
    public void setPassword(String nPassword){
        password = nPassword;
    }
    
    public void setPhone(String nPhone){
        phone = nPhone;
    }
    
    
    public void setPaintworks(List<PaintworkEntity> nPaintwork){
        paintworks = nPaintwork;
    }
    
    public void setSales(List<SaleEntity> pSale){
        sales = pSale;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

      @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

