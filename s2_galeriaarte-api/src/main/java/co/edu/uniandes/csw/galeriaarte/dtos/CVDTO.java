package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa la hoja de vida de un artista.
 *
 * @author Laura
 *
 */
public class CVDTO implements Serializable {

  
    
    //Atributos
      private Long id;
    
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
  public  CVDTO(CVEntity cvEntity)
   {
    if (cvEntity!=null)
    {
        this.id=cvEntity.getId();
        this.educationDTO=cvEntity.geteducationDTO();
        this.fechaNacimientoDTO=cvEntity.getfechaNacimientoDTO();
        this.informacionAdicionalDTO=cvEntity.getinformacionAdicionalDTO();
        this.name=cvEntity.getName();
        this.obraMasConocidaDTO= new PaintworkDTO();
        this.obrasDTO=cvEntity.getobrasDTO();
    }
   }
    //Métodos
    
    public Long getId()
    {
        return this.id;
    }
    
    public void setId(Long pId)
    {
        id=pId;
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
         /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CVEntity toEntity(){
        CVEntity cv= new CVEntity();
        cv.setId(this.id);
        cv.seteducationDTO(educationDTO);
        cv.setfechaNacimientoDTO(fechaNacimientoDTO);
        cv.setinformacionAdicionalDTO(informacionAdicionalDTO);
        cv.setobraMasConocidaDTO(this.obraMasConocidaDTO.toEntity());
        cv.setobrasDTO(obrasDTO);
        cv.setName(this.name);
       
        return cv;
    }
      @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
