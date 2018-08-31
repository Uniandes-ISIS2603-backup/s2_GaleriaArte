package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa la hoja de vida de un artista.
 *
 * @author Laura
 *
 */
public class CVDTO implements Serializable {

    //Atributos
    private String name;
    /**
     * nivel de educación del artista
     */
    private String educationDTO;

    /**
     * Obra más conocida del artista
     */
    private PaintworkDTO obraMasConocidaDTO;

    /**
     * Fecha de nacimiento del artista
     */
    private String fechaNacimientoDTO;

    /**
     * Información adicional sobre el artista
     */
    private String informacionAdicionalDTO;

    /**
     * obrasDTO del artista
     */
    private ArrayList obrasDTO;

    //Constructor
    public CVDTO() {

    }

    //Métodos
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
    public PaintworkDTO getobraMasConocidaDTO() {
        return obraMasConocidaDTO;
    }

    /**
     * Modifica la Obra más conocida del artista
     *
     * @param obraMasConocidaDTO
     */
    public void setobraMasConocidaDTO(PaintworkDTO obraMasConocidaDTO) {
        this.obraMasConocidaDTO = obraMasConocidaDTO;
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
