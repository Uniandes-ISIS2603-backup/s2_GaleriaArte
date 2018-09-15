/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author a.barragan Anderson Barragan
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable
{
    private String name;
    private String image;
    
    @PodamExclude
    @OneToOne
    private CVEntity hojaDeVida;
    
    
    public ArtistEntity(){}
    
    public String getImage(){
        return this.image;
    }
    public void setImage(String pImage){
        this.image = pImage;
    }
    public CVEntity getCV(){
        return this.hojaDeVida;
    }
    public void setCVEntity(CVEntity pCVEntity){
        this.hojaDeVida = pCVEntity;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String pName){
        this.name = pName;
    }
    
    
}
