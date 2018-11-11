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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
     private String educationDTO;
     private PaintworkEntity obraMasConocida;
     private String fechaNacimientoDTO;
     private String informacionAdicionalDTO;
     private ArrayList obrasDTO;
    

  
    
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
    public PaintworkEntity getobraMasConocida() {
        return obraMasConocida;
    }

    /**
     * Modifica la Obra más conocida del artista
     *
     * @param pobraMasConocida
     */
    public void setobraMasConocida(PaintworkEntity pobraMasConocida) {
        this.obraMasConocida = pobraMasConocida;
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
     * @param obrasDTO
     */
    public void setobrasDTO(ArrayList obrasDTO) {
        this.obrasDTO = obrasDTO;
    }
    
    public void setArtista(ArtistEntity pA)
    {
        this.artist=pA;
    }
        @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

