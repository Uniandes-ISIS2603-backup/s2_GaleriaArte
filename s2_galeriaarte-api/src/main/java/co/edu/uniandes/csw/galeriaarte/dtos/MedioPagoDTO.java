/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.galeriaarte.dtos;

/**
 * MedioPagoDTO Objeto de transferencia de datos de la clase Medio de Pago. }
 * Los DTO contienen las representaciones de los JSON
 * que se transfieren entre el cliente y elservidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": long,
 *      "description": String,
 *      "number": long,
 *      "bank": String
 *   }
 * </pre> Por ejemplo un metodo de pago  se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "description": "Tarjeta de crédito",
 *      "number": 23091935,
 *      "bank": "Bancolombia"
 *   }
 *
 * </pre>
 * @author ja.penat
 */
public class MedioPagoDTO
{
    private Long id ;
    private String description;
    private Long number;
    private String bank;
    
    /**
     * Constructor por defecto
     */
    public MedioPagoDTO()
    {
        
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the number
     */
    public Long getNumber() {
        return number;
    }
    
    /**
     * @param number the number to set
     */
    public void setNumber(Long number) {
        this.number = number;
    }
    
    /**
     * @return the bank
     */
    public String getBank() {
        return bank;
    }
    
    /**
     * @param bank the bank to set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }
    
    
}
