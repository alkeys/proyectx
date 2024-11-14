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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmSucursal implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    SucursalBean sucursalBean;

    LazyDataModel<Sucursal> modelo;
    Sucursal registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<Sucursal>() {
            @Override
            public String getRowKey(Sucursal object) {
                return object != null && object.getIdSucursal() != null ? object.getIdSucursal().toString() : null;
            }

            @Override
            public Sucursal getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() != null) {
                    return getWrappedData().stream()
                            .filter(sucursal -> rowKey.equals(sucursal.getIdSucursal().toString()))
                            .findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return sucursalBean.count();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            public List<Sucursal> load(int desde, int max, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    return sucursalBean.findRange(desde, max);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }

    public LazyDataModel<Sucursal> getModelo() {
        return modelo;
    }

    public Sucursal getRegistro() {
        return registro;
    }

    public void setRegistro(Sucursal registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new Sucursal();
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        sucursalBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucursal guardada exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnEliminarHandler(ActionEvent ae) {
        sucursalBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucursal eliminada exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnModificarHandler(ActionEvent ae) {
        sucursalBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucursal modificada exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }
}
