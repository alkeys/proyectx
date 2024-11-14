package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoSala implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoSalaBean tipoSalaBean;
    LazyDataModel<TipoSala> modelo;
    TipoSala registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<TipoSala>() {
            @Override
            public String getRowKey(TipoSala object) {
                return object != null && object.getIdTipoSala() != null ? object.getIdTipoSala().toString() : null;
            }

            @Override
            public TipoSala getRowData(String rowKey) {
                return rowKey != null && getWrappedData() != null
                        ? getWrappedData().stream().filter(r -> rowKey.trim().equals(r.getIdTipoSala().toString())).findFirst().orElse(null)
                        : null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                return tipoSalaBean.count();
            }

            @Override
            public List<TipoSala> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                return desde >= 0 && max > 0 ? tipoSalaBean.findRange(desde, max) : List.of();
            }
        };
    }

    public LazyDataModel<TipoSala> getModelo() {
        return modelo;
    }

    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoSala();
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tipoSalaBean.create(registro);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null));
    }

    public void btnEliminarHandler(ActionEvent ae) {
        tipoSalaBean.delete(registro);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null));
    }

    public void btnModificarHandler(ActionEvent ae) {
        tipoSalaBean.update(registro);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null));
    }
}
