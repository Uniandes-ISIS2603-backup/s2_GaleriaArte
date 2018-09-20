/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
//import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


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
    private Long idUser;
    
    
    public BuyerDTO(){
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param BuyerEntity: Es la entidad que se va a convertir a DTO
     */
    
    
    
    public BuyerDTO (BuyerEntity buyerEntity) {
        if (buyerEntity != null) {
            this.adress = buyerEntity.getAdress();
            this.country = buyerEntity.getCountry();
            this.creditcard = buyerEntity.getCreditCard();
            this.email = buyerEntity.getEmail();
            this.name = buyerEntity.getName();
            this.password = buyerEntity.getPassword();
            this.phone = buyerEntity.getPhone();
            this.user = buyerEntity.getUser();
            
           
        }
    }
    
    /**
     * Convertir DTO a Entity
    */
  
    public BuyerEntity toEntity() 
     {
    
        BuyerEntity entidad = new BuyerEntity();
        entidad.setAdress(this.adress);
        entidad.setCountry(this.country);
        entidad.setCreditCard(this.creditcard);
        entidad.setEmail(this.email);
        entidad.setName(this.name);
        entidad.setPassword(this.password);
        entidad.setPhone(this.phone);
        entidad.setUser(this.user);
        
        
       
        return entidad;
    }
    /**
     * @return clave del usuario  
     */
    
    public String getPassword(){
        return password;
    }
    
    /**
     * @param  nueva contraseña del usuario  
     * 
     */
    
    public void setPassword(String newPassword){
        password = newPassword;
    }
    
    /**
     * @return nombre del usuario 
     */
    
    public String getName(){
        return name;
    }
    
   
    
    /**
     * 
     * @param the  nuevo nombre del usuario  
     */
    
    public void setName(String newName){
        
        name = newName;
    }
    /**
     * 
     * @return login del usuario
     */
    public String getUser(){
        return user;
    }
    
    /**
     * @param nuevo login del usuario  
     */
    public void setUser(String newUser){
        user = newUser;
    }
    
   
    /**
     * @return País de origen del usuario 
     */
    
    public String getCountry(){
        return country;
    }
    
    /**
     * @param nuevo país del usuario 
     */
    public void setCountry(String newCountry){
        
        country = newCountry;
    }
    
    
   /**
    * @return tarjeta de credito del usuario 
    */
    
    public String getCreditCard(){
        return creditcard;
    }
    
    /**
     * @param nueva tarjeta de credito del usuario 
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
     * @return el id del usuario 
     */
    
    public Long getIdUser(){
        return idUser;
    }
    
    /**
     * @param nuevo id del usuario 
     */
    
    public void setIdUser(Long newId){
        
        idUser= newId;
    }
    
    /**
     * 
     * @return email del usuario 
     */
            
    public String getEmail(){
        return email;
    }
    
    /**
     *
     * @param nuevo email del usuario 
     */
    public void setEmail(String newEmail){
        email = newEmail;
    }
    
    /**
     * 
     * @return telefono del usuario  
     */
    
    public String getPhone(){
        return phone;
    }
    
    /**
     * 
     * @param nuevo telefono del usuario 
     */
    
    public void setPhone(String newPhone){
        phone = newPhone;
    }

    @Override 
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

