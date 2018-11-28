/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.dtos;


import java.io.Serializable;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;

/**
 *
 * @author s.acostav
 */
public class BuyerDTO implements Serializable
{
    private long id;
    private String password;
    private String name;
    private String adress;
    private String email;
    private String phone;
    private String creditcard;
    private String country;
    
    public BuyerDTO()
    {
        
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param buyerEntity
     */
    
    public BuyerDTO (BuyerEntity buyerEntity)
    {
        if (buyerEntity != null) 
        {
            this.adress = buyerEntity.getAdress();
            this.country = buyerEntity.getCountry();
            this.creditcard = buyerEntity.getCreditCard();
            this.email = buyerEntity.getEmail();
            this.name = buyerEntity.getName();
            this.password = buyerEntity.getPassword();
            this.phone = buyerEntity.getPhone();
            this.id = buyerEntity.getId(); 
        }
    }
    
    /**
     * Convertir DTO a Entity
     */
    public BuyerEntity toEntity()
    {   
        BuyerEntity entidad = new BuyerEntity();
        entidad.setAdress(this.getAdress());
        entidad.setCountry(this.getCountry());
        entidad.setCreditCard(this.getCreditcard());
        entidad.setEmail(this.getEmail());
        entidad.setName(this.getName());
        entidad.setPassword(this.getPassword());
        entidad.setPhone(this.getPhone());
        entidad.setId(this.getId());
        return entidad;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the creditcard
     */
    public String getCreditcard() {
        return creditcard;
    }

    /**
     * @param creditcard the creditcard to set
     */
    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
}
