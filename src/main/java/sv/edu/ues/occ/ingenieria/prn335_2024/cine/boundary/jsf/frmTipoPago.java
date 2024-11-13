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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoPago extends AbstractFrm<TipoPago> implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoPagoBean tPagoBean;
            LazyDataModel <TipoPago> modelo;
                TipoPago registro;

    @Override
    public AbstractDataPersistence<TipoPago> getDataAccess() {
        return this.tPagoBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return this.facesContext;
    }

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<TipoPago>() {

            @Override
            public String getRowKey(TipoPago object) {
                if (object != null && object.getIdTipoPago() != null) {
                    return object.getIdTipoPago().toString();
                }
                return null;
            }

            @Override
            public TipoPago getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdTipoPago().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return tPagoBean.count();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            public List<TipoPago> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {
                    if (desde >= 0 && max > 0) {
                        return tPagoBean.findRange(desde, max);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }

    @Override
    public String getIdPorObjeto(TipoPago object) {
        return "";
    }

    @Override
    public TipoPago getObjetoPorId(String id) {
        return null;
    }

    @Override
    public void instanciarRegistro() {

    }


    public LazyDataModel<TipoPago> getModelo() {
        return modelo;
    }

    public TipoPago getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPago registro) {
        this.registro = registro;
    }


    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoPago();
        this.registro.setActivo(true);
        this.estado = Estado_crud.CREAR;
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tPagoBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnEliminarHandler(ActionEvent ae) {
        tPagoBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnModificarHandler(ActionEvent ae) {
        tPagoBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }
}
