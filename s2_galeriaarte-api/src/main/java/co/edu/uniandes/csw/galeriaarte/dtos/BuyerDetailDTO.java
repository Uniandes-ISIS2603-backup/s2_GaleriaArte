/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;

import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class BuyerDetailDTO extends BuyerDTO implements Serializable{
    
    //Relacion de cero o muchas  ventas
    private List<SaleDTO> sales;
    
    //Relacion de cero o muchas obras
    
    private List<PaintworkDTO> obrasFavoritas;
    
    public BuyerDetailDTO(){
        super();
    }
    
    public BuyerDetailDTO(BuyerEntity buyerEntity) {
        super(buyerEntity);
        if (buyerEntity != null) {
            sales = new ArrayList<>();
            for (SaleEntity entitySales : buyerEntity.getSales()) {
                sales.add(new SaleDTO(entitySales));
            }
            obrasFavoritas = new ArrayList();
            for (PaintworkEntity entityObras : buyerEntity.getPaintworks()) {
                obrasFavoritas.add(new PaintworkDTO(entityObras));
            }
        }
     
    }
    
     @Override
    public BuyerEntity toEntity() {
        BuyerEntity buyerEntity = super.toEntity();
        if (sales != null) {
            List<SaleEntity> salesEntity = new ArrayList<>();
            for (SaleDTO dtoSale : sales) {
                salesEntity.add(dtoSale.toEntity());
            }
            buyerEntity.setSales(salesEntity);
        }
        if (obrasFavoritas != null) {
            List<PaintworkEntity> obrasEntity = new ArrayList<>();
            for (PaintworkDTO dtoObras : obrasFavoritas) {
                obrasEntity.add(dtoObras.toEntity());
            }
            buyerEntity.setPaintworks(obrasEntity);
        }
        return buyerEntity;
    }
    
    
    public List<SaleDTO> getSales(){
        return sales;
    }
    
    public void setSales(List<SaleDTO> sale){
        sales = sale;
    }
    
    public List<PaintworkDTO> getObrasFavoritas(){
        return obrasFavoritas;
    }
    
    public void setObrasFavoritas(List<PaintworkDTO> obras){
        obrasFavoritas = obras;
    }
     @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
