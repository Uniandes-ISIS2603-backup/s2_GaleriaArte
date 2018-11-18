package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.CVEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa la hoja de vida de un artista.
 *
 * @author Laura
 *
 */
public class CVDTO implements Serializable 
{
    
    //ATRIBUTOS
    
    
    /**
     * Atributo que corresponde al id de de la hoja de vida
     */
    private Long id;
    
    /**
     * Nivel de educación del artista
     */
    private String education;
    
    /**
     * Nombre de la obra más conocida del artista
     */
    private String nombreObraMasConocida;
    
    /**
     * Fecha de nacimiento del artista
     */
    private String fechaNacimiento;
    
    /**
     * Información adicional sobre el artista
     */
    private String informacionAdicional;
    
    //Constructor
    public CVDTO()
    {
        
    }
    
    //METODOS
    
    public  CVDTO(CVEntity cvEntity)
    {
        if (cvEntity!=null)
        {
            this.id = cvEntity.getId();
            this.education = cvEntity.getEducation();
            this.fechaNacimiento = cvEntity.getFechaNacimiento();
            this.informacionAdicional = cvEntity.getInformacionAdicional();
            this.nombreObraMasConocida= cvEntity.getNombreObraMasConocida();
        }
    }
    //Métodos
    
    public Long getId()
    {
        return this.id;
    }
    
    public void setId(Long pId)
    {
        id = pId;
    }
    
    /**
     * Retorna el nivel de educación del artista
     *
     * @return nivel de educación del artista
     */
    public String getEducation()
    {
        return education;
    }
    
    /**
     * Modifica el nivel de educación del artista
     *
     * @param pEducation, el nuevo nivel de educacion del artista
     */
    public void setEducation(String pEducation)
    {
        this.education = pEducation;
    }
    
    /**
     * Retorna la Obra más conocida del artista
     *
     * @return Obra más conocida del artista
     */
    public String getNombreObraMasConocida()
    {
        return nombreObraMasConocida;
    }
    
    /**
     * Modifica la Obra más conocida del artista
     *
     * @param pNombreObraMasConocida, el nombre de la obra mas conocida del artista
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
     * @param pFechaNacimientoDTO
     */
    public void setFechaNacimiento(String pFechaNacimientoDTO)
    {
        this.fechaNacimiento = pFechaNacimientoDTO;
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
     * @param pInformacionAdicional
     */
    public void setInformacionAdicional(String pInformacionAdicional)
    {
        this.informacionAdicional = pInformacionAdicional;
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CVEntity toEntity()
    {
        CVEntity cv = new CVEntity();
        cv.setId(this.id);
        cv.setEducation(this.education);
        cv.setFechaNacimiento(this.fechaNacimiento);
        cv.setInformacionAdicional(this.informacionAdicional);
        cv.setNombreObraMasConocida(this.nombreObraMasConocida);
        
        return cv;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
