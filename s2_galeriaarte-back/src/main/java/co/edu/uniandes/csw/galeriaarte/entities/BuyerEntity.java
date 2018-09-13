/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
/**
 *
 * @author s.acostav
 */
@Entity
public class BuyerEntity extends BaseEntity implements Serializable
{
    String name;
<<<<<<< HEAD
=======

    String password;
    String user;
    String email;
    String phone;
    String creditcard;
    String country;
    Long idUser;
    SaleEntity sale;
    PaintworkEntity paintwork;
    String adress;

>>>>>>> 54b641d6bceba462731e85a62304fc4b3a1c8119
    
    public BuyerEntity(){
        
    }
    public String getName(){
        return this.name;
    }
    
<<<<<<< HEAD
    public void setName(String nName){
        this.name = nName;
    }
 }
=======

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
    
    public String getUser(){
        return this.user;
    }
    
    public SaleEntity getSale(){
        return sale;
    }

    
    public PaintworkEntity getPaintwork( ){
        return paintwork;
    }
    public void setName(String nName){
        this.name = nName;
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
    
    public void setUser(String nUser){
        user = nUser;
    }
    
    public void setPaintwork(PaintworkEntity nPaintwork){
        paintwork = nPaintwork;
    }
    
    public void setSale(SaleEntity pSale){
        sale = pSale;
    }

}
>>>>>>> 54b641d6bceba462731e85a62304fc4b3a1c8119
