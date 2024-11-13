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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoProducto implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoProductoBean tpBean;
    LazyDataModel<TipoProducto> modelo;
    TipoProducto registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<TipoProducto>() {

            @Override
            public String getRowKey(TipoProducto object) {
                if (object != null && object.getIdTipoProducto() != null) {
                    return object.getIdTipoProducto().toString();
                }
                return null;
            }

            @Override
            public TipoProducto getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdTipoProducto().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return tpBean.count();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            public List<TipoProducto> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {
                    if (desde >= 0 && max > 0) {
                        return tpBean.findRange(desde, max);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }


    public LazyDataModel<TipoProducto> getModelo() {
        return modelo;
    }

    public TipoProducto getRegistro() {
        return registro;
    }

    public void setRegistro(TipoProducto registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoProducto();

        this.registro.setActivo(true);
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tpBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnEliminarHandler(ActionEvent ae) {
        tpBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnModificarHandler(ActionEvent ae) {
        tpBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }
}
