/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

/**
 * *Clase que extiende de {@link MedioPagoDTO} para manejar las relaciones entre
 * los MedioPagoDTO y otros DTOs. Para conocer el contenido de la un medio de pago vaya a la
 * documentacion de {@link MedioPagoDTO}
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": long,
 *      "description": String,
 *      "number": long,
 *      "bank": String
 *      "sale": {@link SaleDTO}
 *   }
 * </pre> Por ejemplo un metodo de pago  se representa asi:<br>
 *
 *
 * </pre>
 * @author ja.penat
 */
public class MedioPagoDetailDTO 
{
    
}
