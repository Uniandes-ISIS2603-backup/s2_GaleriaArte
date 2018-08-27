/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.dtos;
/**
 * ExtraServiceDTO Objeto de transferencia de datos de la clase ExtraService. }
 * Los DTO contienen las representaciones de los JSON
 * que se transfieren entre el cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": long,
 *      "name": String,
 *      "description": String,
 *      "price": double,
 *      "availability" : boolean
 *
 *   }
 * </pre> Por ejemplo un metodo de pago  se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "long" : id,
 *      "name": "Enmarcado",
 *      "description": "Se usa un marco de madera donde se pondra la obra de arte",
 *      "price": 100000,
 *      "availability" : true
 *   }
 *
 * </pre>
 * @author ja.penat
 */
public class ExtraServiceDTO
{
    private long id;
    private String name;
    private String description;
    private double price;
    private boolean availability;
    
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the availability
     */
    public boolean isAvailability() {
        return availability;
    }
    
    /**
     * @param availability the availability to set
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    
}
