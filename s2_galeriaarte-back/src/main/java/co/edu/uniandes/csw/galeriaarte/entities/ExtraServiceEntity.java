/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;

/**
 * Clase que representa un servicio extra  en la persistencia y permite su
 * serialización.
 *
 * @author ja.penat
 */
public class ExtraServiceEntity extends BaseEntity implements Serializable
{
    private String name;
    private String description;
    private double price;
    private boolean availability;

    /**
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() 
    {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) 
    {
        this.price = price;
    }

    /**
     * @return the availability
     */
    public boolean getAvailability()
    {
        return availability;
    }

    /**
     * @param availability the availability to set
     */
    public void setAvailability(boolean availability) 
    {
        this.availability = availability;
    }
}
