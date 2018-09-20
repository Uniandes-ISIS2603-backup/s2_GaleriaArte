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
import javax.persistence.Id;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author LauraManrique
 */

@Entity
public class CVEntity extends BaseEntity implements Serializable{
    
        
@PodamExclude
@OneToOne(mappedBy="cv", fetch=FetchType.LAZY)
private ArtistEntity artist;


     private Long id;
     private String name;
     private String educationDTO;
     private PaintworkEntity obraMasConocidaDTO;
     private String fechaNacimientoDTO;
     private String informacionAdicionalDTO;
     private ArrayList obrasDTO;
    
     
    public Long getId()
    {
        return this.id;
    }
     
    public void setId(Long pId)
    {
        this.id=pId;
    }
    
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
    public String geteducationDTO() {
        return educationDTO;
    }

    /**
     * Modifica el nivel de educación del artista
     *
     * @param educationDTO
     */
    public void seteducationDTO(String educationDTO) {
        this.educationDTO = educationDTO;
    }

    /**
     * Retorna la Obra más conocida del artista
     *
     * @return Obra más conocida del artista
     */
    public PaintworkEntity getobraMasConocidaDTO() {
        return obraMasConocidaDTO;
    }

    /**
     * Modifica la Obra más conocida del artista
     *
     * @param obraMasConocidaDTO
     */
    public void setobraMasConocidaDTO(PaintworkEntity pobraMasConocidaDTO) {
        this.obraMasConocidaDTO = pobraMasConocidaDTO;
    }

    /**
     * Retorna la Fecha de nacimiento del artista
     *
     * @return Fecha de nacimiento del artista
     */
    public String getfechaNacimientoDTO() {
        return fechaNacimientoDTO;
    }

    /**
     * Modifica la Fecha de nacimiento del artista
     *
     * @param fechaNacimientoDTO
     */
    public void setfechaNacimientoDTO(String fechaNacimientoDTO) {
        this.fechaNacimientoDTO = fechaNacimientoDTO;
    }

    /**
     * Retorna la Información adicional sobre el artista
     *
     * @return Información adicional sobre el artista
     */
    public String getinformacionAdicionalDTO() {
        return informacionAdicionalDTO;
    }

    /**
     * Modifica la Información adicional sobre el artista
     *
     * @param informacionAdicionalDTO
     */
    public void setinformacionAdicionalDTO(String informacionAdicionalDTO) {
        this.informacionAdicionalDTO = informacionAdicionalDTO;
    }

    /**
     * Retorna las obrasDTO del artista
     *
     * @return las obrasDTO del artista
     */
    public ArrayList getobrasDTO() {
        return obrasDTO;
    }

    /**
     * Modifica las obrasDTO del artista
     *
     * @param informacionAdicionalDTO
     */
    public void setobrasDTO(ArrayList obrasDTO) {
        this.obrasDTO = obrasDTO;
    }
}

