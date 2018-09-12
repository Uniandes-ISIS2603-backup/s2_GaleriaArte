/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author s.acostav
 */
@Entity
public class BuyerEntity extends BaseEntity implements Serializable
{
    String name;

    
    public String getName(){
        return this.name;
    }
    
   
    
    public void setName(String nName){
        this.name = nName;
    }
    

}
