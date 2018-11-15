/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author LauraManrique
 */

@Entity
public class CVEntity extends BaseEntity implements Serializable{
    
        
@PodamExclude
@OneToOne()
private ArtistEntity artist;


     private String name;
     private String education;
     private PaintworkEntity obraMasConocida;
     private String fechaNacimiento;
     private String informacionAdicional;
     private ArrayList obras;
    

    public String getName(){
        return name;
    }
    public void setName(String pName){
        name=pName;
    }
     /**
     * Retorna el nivel de educación del artista
     *
     * @return nivel de educación del artista
     */
    public String getEducation() {
        return education;
    }

    /**
     * Modifica el nivel de educación del artista
     *
     * @param education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * Retorna la Obra más conocida del artista
     *
     * @return Obra más conocida del artista
     */
    public PaintworkEntity getobraMasConocida() {
        return obraMasConocida;
    }

    /**
     * Modifica la Obra más conocida del artista
     *
     * @param obraMasConocida
     */
    public void setobraMasConocida(PaintworkEntity obraMasConocida) {
        this.obraMasConocida = obraMasConocida;
    }

    /**
     * Retorna la Fecha de nacimiento del artista
     *
     * @return Fecha de nacimiento del artista
     */
    public String getfechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Modifica la Fecha de nacimiento del artista
     *
     * @param fechaNacimiento
     */
    public void setfechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Retorna la Información adicional sobre el artista
     *
     * @return Información adicional sobre el artista
     */
    public String getinformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * Modifica la Información adicional sobre el artista
     *
     * @param informacionAdicional
     */
    public void setinformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    /**
     * Retorna las obras del artista
     *
     * @return las obras del artista
     */
    public ArrayList getobras() {
        return obras;
    }

    /**
     * Modifica las obras del artista
     *
     * @param informacionAdicional
     */
    public void setobras(ArrayList obras) {
        this.obras = obras;
    }
    
    public void setArtista(ArtistEntity pA)
    {
        this.artist=pA;
    }
}

