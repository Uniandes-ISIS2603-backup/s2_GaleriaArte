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
 * Ja.penat, implemente muchos cambios a la estructura de la entidad y sus metodos
 * @author Laura y  Ja.penat
 */
@Entity
public class CVEntity extends BaseEntity implements Serializable
{
    @PodamExclude
    @OneToOne
    private ArtistEntity artist;
    
    private String nombreObraMasConocida;
    
    private String education;
    
    private String fechaNacimiento;
    
    private String informacionAdicional;
    
    
  
    /**
     * Retorna el nivel de educación del artista
     * @return nivel de educación del artista
     */
    public String getEducation()
    {
        return education;
    }
    
    /**
     * Modifica el nivel de educación del artista
     *
     * @param education
     */
    public void setEducation(String education)
    {
        this.education = education;
    }
    
    /**
     * Método que retorna El nombre de la Obra más conocida del artista
     *
     * @return El nombre de la Obra más conocida del artista
     */
    public String getNombreObraMasConocida()
    {
        return nombreObraMasConocida;
    }
    
    /**
     * Modifica la Obra más conocida del artista
     *
     * @param pNombreObraMasConocida
     */
    public void setNombreObraMasConocida(String pNombreObraMasConocida)
    {
        this.nombreObraMasConocida = pNombreObraMasConocida;
    }
    
    /**
     * Retorna la Fecha de nacimiento del artista
     *
     * @return Fecha de nacimiento del artista
     */
    public String getFechaNacimiento()
    {
        return fechaNacimiento;
    }
    
    /**
     * Modifica la Fecha de nacimiento del artista
     *
     * @param fechaNacimiento
     */
    public void setFechaNacimiento(String fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * Retorna la Información adicional sobre el artista
     *
     * @return Información adicional sobre el artista
     */
    public String getInformacionAdicional()
    {
        return informacionAdicional;
    }
    
    /**
     * Modifica la Información adicional sobre el artista
     *
     * @param informacionAdicional
     */
    public void setInformacionAdicional(String informacionAdicional)
    {
        this.informacionAdicional = informacionAdicional;
    }
    
    /**
     * Método que retorna el artista asociado a la hoja de vida.
     * @return Artista de la hoja de vida.
     */
    public ArtistEntity getArtist()
    {
        return this.artist;
    }
    
    /**
     * Método que se encarga de cambiar el Artista asociado a la hoja de vida.
     * @param pArtist, El nuevo artista que se le asigna a la hoja de vida.
     */
    public void setArtist(ArtistEntity pArtist)
    {
        this.artist = pArtist ;
    }
}


