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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoAsiento implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoAsientoBean taBean;
    LazyDataModel<TipoAsiento> modelo;
    TipoAsiento registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<TipoAsiento>() {

            @Override
            public String getRowKey(TipoAsiento object) {
                if (object != null && object.getIdTipoAsiento() != null) {
                    return object.getIdTipoAsiento().toString();
                }
                return null;
            }

            @Override
            public TipoAsiento getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdTipoAsiento().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return taBean.count();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            public List<TipoAsiento> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {
                    if (desde >= 0 && max > 0) {
                        return taBean.findRange(desde, max);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }


    public LazyDataModel<TipoAsiento> getModelo() {
        return modelo;
    }

    public TipoAsiento getRegistro() {
        return registro;
    }

    public void setRegistro(TipoAsiento registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoAsiento();

        this.registro.setActivo(true);
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        taBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }

    public void btnEliminarHandler(ActionEvent ae) {
        taBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }

    public void btnModificarHandler(ActionEvent ae) {
        taBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }
}
