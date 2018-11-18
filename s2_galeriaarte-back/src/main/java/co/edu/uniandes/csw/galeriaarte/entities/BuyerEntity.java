/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
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
    private Long idUsuario;
    @PodamExclude
    @OneToMany(mappedBy = "buyer",fetch=FetchType.LAZY)
    private List<SaleEntity> sale;
    private List<PaintworkEntity> paintwork;
    private String adress;
 
    
    
    
    

    
    public BuyerEntity(){
        
    }
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
    
    public String getUsuario(){
        return this.usuario;
    }
    
    public List<SaleEntity> getSale(){
        return sale;
    }

    
    public List<PaintworkEntity> getPaintwork( ){
        return paintwork;
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
    
    public void setUsuario(String nUsuario){
        usuario = nUsuario;
    }
    
    public void setPaintwork(List<PaintworkEntity> nPaintwork){
        paintwork = nPaintwork;
    }
    
    public void setSale(List<SaleEntity> pSale){
        sale = pSale;
    }

}

