/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;


/**
 *
 * @author estudiante
 */
public class BuyerDTO implements Serializable {
    
    private String password;
    private String name;
    private String user;
    private String adress;
    private String email;
    private String phone;
    private String creditcard;
    private String country;
    private String idUser;
    
    public BuyerDTO(){
        
    }
    
    /**
     * @return the password 
     */
    
    public String getPassword(){
        return password;
    }
    
    /**
     * @param the password to set 
     * 
     */
    
    public void setPassword(String newPassword){
        password = newPassword;
    }
    
    /**
     * @return name
     */
    
    public String getName(){
        return name;
    }
    
    /**
     * 
     * @param the  name to set  
     */
    
    public void setName(String newName){
        
        name = newName;
    }
    /**
     * 
     * @return user 
     */
    public String getUser(){
        return user;
    }
    
    /**
     * @param the user to set 
     */
    public void setUser(String newUser){
        user = newUser;
    }
    
    /**
     * 
     */
    
    public String getCountry(){
        return country;
    }
    
    /**
     * 
     */
    public void setCountry(String newCountry){
        
        country = newCountry;
    }
    
    
   /**
    * 
    */
    
    public String getCreditCard(){
        return creditcard;
    }
    
    /**
     * 
     */
    
    public void setCreditCard(String newCreditCard){
        
        creditcard = newCreditCard;
    }
    
    /**
     * 
     */
    public String getAdress(){
        return adress;
    }
    
    public void setAdress(String newAdress){
        adress = newAdress;
    }
    /**
     * 
     * @return 
     */
    
    public String getIdUser(){
        return idUser;
    }
    
    /**
     * 
     */
    
    public void setIdUser(String newId){
        
        idUser= newId;
    }
            
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String newEmail){
        email = newEmail;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String newPhone){
        phone = newPhone;
    }
}


