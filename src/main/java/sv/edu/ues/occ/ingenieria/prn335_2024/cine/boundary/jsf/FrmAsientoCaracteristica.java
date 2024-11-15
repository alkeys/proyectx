package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmAsientoCaracteristica extends AbstractPfFrm<AsientoCaracteristica> implements Serializable {

    @Inject
    AsientoCaracteristicaBean asientoCaracteristicaBean;

    @Inject
    FacesContext facesContext;

    @Inject
    AsientoBean asientoBean;

    @Inject
    TipoAsientoBean tipoAsientoBean;

    List<TipoAsiento> tipoAsientoList;
    List<Asiento> asientoList;
    Long idAsiento;

    @PostConstruct
    public void inicializar(){
        try{
            super.inicializar();
            tipoAsientoList = tipoAsientoBean.findRange(0,Integer.MAX_VALUE);
            asientoList = asientoBean.findRange(0,Integer.MAX_VALUE);
        }catch (Exception e){

        }
    }

    @Override
    public int contar() {
        try {
            if (asientoCaracteristicaBean != null) {
                return asientoCaracteristicaBean.countAsiento(this.idAsiento);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public List<AsientoCaracteristica> cargarDatos(int firstResult, int maxResult) {
        try {
            if (asientoCaracteristicaBean != null) {
                return asientoCaracteristicaBean.findByIdAsiento(this.idAsiento, firstResult, maxResult);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public Long getIdTipoAsientoSeleccionado() {
        if (this.registro != null && this.registro.getIdAsiento() != null) {
            return this.registro.getIdAsiento().getIdAsiento();
        }
        return null;
    }

    public void setIdTipoAsientoSeleccionado(final Long idTipoAsiento) {
        if (this.registro != null && this.tipoAsientoList != null && !this.tipoAsientoList.isEmpty()) {
            this.registro.setIdTipoAsiento(this.tipoAsientoList.stream()
                    .filter(r -> r.getIdTipoAsiento().equals(idTipoAsiento))
                    .findFirst()
                    .orElse(null));

        }
    }


    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public AbstractDataPersistence<AsientoCaracteristica> getDataBean(){
        return asientoCaracteristicaBean;
    }

    @Override
    public AsientoCaracteristica createNewEntity(){
        return new AsientoCaracteristica();
    }

    @Override
    public Object getId(AsientoCaracteristica o){
        return o.getIdAsientoCaracteristica();
    }

    @Override
    public String getTituloPag(){
        return "Asiento Característica";
    }

    @Override
    public AsientoCaracteristica buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(AsientoCaracteristica entity) {
        return "";
    }

    public AsientoCaracteristicaBean getAsientoCaracteristicaBean() {
        return asientoCaracteristicaBean;
    }

    public void setAsientoCaracteristicaBean(AsientoCaracteristicaBean asientoCaracteristicaBean) {
        this.asientoCaracteristicaBean = asientoCaracteristicaBean;
    }

    public Long getIdTipoAsiento() {
        return idAsiento;
    }

    public void setIdTipoAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    public void setTipoAsientoList(List<TipoAsiento> tipoAsientoList) {
        this.tipoAsientoList = tipoAsientoList;
    }

    public TipoAsientoBean getTipoAsientoBean() {
        return tipoAsientoBean;
    }

    public void setTipoAsientoBean(TipoAsientoBean tipoAsientoBean) {
        this.tipoAsientoBean = tipoAsientoBean;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public AsientoBean getAsientoBean() {
        return asientoBean;
    }

    public void setAsientoBean(AsientoBean asientoBean) {
        this.asientoBean = asientoBean;
    }

    public List<Asiento> getAsientoList() {
        return asientoList;
    }

    public void setAsientoList(List<Asiento> asientoList) {
        this.asientoList = asientoList;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }
}
