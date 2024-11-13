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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoReserva implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoReservaBean trBean;
                LazyDataModel <TipoReserva> modelo;
                    TipoReserva registro;

    @PostConstruct
public void inicializar() {
        modelo = new LazyDataModel<TipoReserva>() {

            @Override
            public String getRowKey(TipoReserva object) {
                if (object != null && object.getIdTipoReserva() != null) {
                    return object.getIdTipoReserva().toString();
                }
                return null;
            }

            @Override
            public TipoReserva getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdTipoReserva().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
                public int count(Map<String, FilterMeta> map) {
                    try {
                        return trBean.count();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return 0;
                }

                @Override
                public List<TipoReserva> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                    try {
                        if (desde >= 0 && max > 0) {
                            return trBean.findRange(desde, max);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return List.of();
                }
            };
        }


    public LazyDataModel<TipoReserva> getModelo() {
        return modelo;
    }

    public TipoReserva getRegistro() {
        return registro;
    }

    public void setRegistro(TipoReserva registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoReserva();

        this.registro.setActivo(true);
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
    trBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnEliminarHandler(ActionEvent ae) {
        trBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnModificarHandler(ActionEvent ae) {
    trBean.update(registro);
    FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
    facesContext.addMessage(null, mensaje);
    }
}
