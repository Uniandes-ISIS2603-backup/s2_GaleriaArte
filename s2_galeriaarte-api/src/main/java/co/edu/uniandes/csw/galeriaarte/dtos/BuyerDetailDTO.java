/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.galeriaarte.dtos;
import co.edu.uniandes.csw.galeriaarte.entities.SaleEntity;
import co.edu.uniandes.csw.galeriaarte.entities.BuyerEntity;
import co.edu.uniandes.csw.galeriaarte.entities.PaintworkEntity;
import java.util.ArrayList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
import java.util.List;



/**
 *
 * @author estudiante
 */
public class BuyerDetailDTO extends BuyerDTO implements Serializable {
   
   //relación cero a muchos
    
    private List<SaleDTO> compras;
    
    //relación cero a muchos 
    
    private List<PaintworkDTO> obras; 
    
    public BuyerDetailDTO(){
        super();
    }
    
    /**
     * Crea un objeto BuyerDetailDTO a partir de un objeto BuyerEntity
     * incluyendo los atributos de BuyerDTO.
     *
     * @param buyerEntity Entidad BuyerEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public BuyerDetailDTO(BuyerEntity buyerEntity) {
        super(buyerEntity);
        if (buyerEntity != null) {
            compras = new ArrayList();
            for (SaleEntity saleEntity : buyerEntity.getSale()) {
                compras.add(new SaleDTO(saleEntity));
            }
            obras = new ArrayList();
            for (PaintworkEntity entityPaintwork : buyerEntity.getPaintwork()) {
                obras.add(new PaintworkDTO(entityPaintwork));
            }
        }
    }

    /**
     * Convierte un objeto BuyerDetailDTO a BuyerEntity incluyendo los
     * atributos de BuyerDTO.
     *
     * @return Nueva objeto BuyerEntity.
     *
     */
    @Override
    public BuyerEntity toEntity() {
        BuyerEntity buyerEntity = super.toEntity();
        
        if(buyerEntity!=null){
        if (compras != null) {
            ArrayList<SaleEntity> saleEntity = new ArrayList<>();
            for (SaleDTO dtoSale : compras) {
                saleEntity.add(dtoSale.toEntity());
            }
            buyerEntity.setSale(saleEntity);
        }
        if (obras != null) {
            ArrayList<PaintworkEntity> paintworkEntity = new ArrayList<>();
            for (PaintworkDTO dtoPrize : obras) {
                paintworkEntity.add(dtoPrize.toEntity());
            }
            buyerEntity.setPaintwork(paintworkEntity);
        }
        }
        return buyerEntity;
    }

    /**
     * Obtiene la lista de compras del comprador
     *
     * @return the books
     */
    public List<SaleDTO> getSales() {
        return compras;
    }

    /**
     * Modifica la lista de obras favoritas para el comprador
     *
     * @param books the books to set
     */
    public void setPaintworks(List<PaintworkDTO> obra) {
        this.obras = obra;
    }

    /**
     * Obtiene la lista de obras favoritas del comprador 
     *
     * @return the prizes
     */
    public List<PaintworkDTO> getPaintwork() {
        return obras;
    }

    /**
     * Modifica la lista de compras del comrpador
     *
     * @param sales the prizes to set
     */
    public void setPrizes(List<SaleDTO> sales) {
        this.compras = sales;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
      
}
