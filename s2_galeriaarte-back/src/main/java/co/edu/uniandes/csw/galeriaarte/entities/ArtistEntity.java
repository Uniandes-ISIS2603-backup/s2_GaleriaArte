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
 * @author 
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable
{
    String name;
    String image;
   
    public String getImage(){
        return this.image;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String pName){
        this.name = pName;
    }
    public void setImage(String pImage){
        this.image = pImage;
    }
}
