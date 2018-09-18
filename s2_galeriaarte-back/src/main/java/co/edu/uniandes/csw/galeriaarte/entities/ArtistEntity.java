/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
    
    @PodamExclude
    @OneToMany(mappedBy = "artist",fetch=FetchType.LAZY)
    private List<PaintworkEntity> obras;
    
    /**
     * constructor vacio de la clase @Artist
     */
    public ArtistEntity(){}
    
    /**
     * retorna la imagen asociada al artista
     * @return ruta de la imagen
     */
    public String getImage(){
        return this.image;
    }
    
    /**
     * Cambia la imagen actual por la del parametro
     * @param pImage ruta de la nueva imagen
     */
    public void setImage(String pImage){
        this.image = pImage;
    }
    
    /**
     * retorna la hoja de vida
     * @return hoja de vida asociada al artist
     */
    public CVEntity getCV(){
        return this.hojaDeVida;
    }
    
    /**
     * Cambia la hoja de vida por la entrante
     * @param pCVEntity nueva hoja de vida
     */
    public void setCVEntity(CVEntity pCVEntity){
        this.hojaDeVida = pCVEntity;
    }
    
    /**
     * 
     * @return 
     */
    public String getName(){
        return this.name;
    }
    public void setName(String pName){
        this.name = pName;
    }
    
    /**
     * Obtiene ls obras.
     * @return las obras del artista.
     */
    public List<PaintworkEntity> getObras() {
        return obras;
    }
    
    /**
     * Establece el valor de la lista de obras.
     * @param paintworks nuevo valor de la colección.
     */
    public void setPaintworks(List<PaintworkEntity> paintworks) {
        this.obras = paintworks;
    }
    
    
}
