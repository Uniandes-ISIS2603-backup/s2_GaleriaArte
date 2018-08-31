/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import java.io.Serializable;

/**
 *
 * @author LauraManrique
 */
public class KindDTO implements Serializable {

    //Atributos
    
    /**
     * Identificación del tipo
     */
    private Long  idType;
    
    /**
     * Nombre
     */
    private String name;
    
    /**
     * Descripción
     */
    private String description;
    
    public  KindDTO()
    {
        
    }

    public Long getIdType() {
        return idType;
    }

    public void setIdType(Long idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

