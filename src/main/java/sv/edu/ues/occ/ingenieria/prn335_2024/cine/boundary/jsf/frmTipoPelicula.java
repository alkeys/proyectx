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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmTipoPelicula implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    TipoPeliculaBean tpeliBean;
    LazyDataModel<TipoPelicula> modelo;
    TipoPelicula registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<TipoPelicula>() {

            @Override
            public String getRowKey(TipoPelicula object) {
                if (object != null && object.getIdTipoPelicula() != null) {
                    return object.getIdTipoPelicula().toString();
                }
                return null;
            }

            @Override
            public TipoPelicula getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdTipoPelicula().toString())).findFirst().orElse(null);
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return tpeliBean.count();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            public List<TipoPelicula> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {
                    if (desde >= 0 && max > 0) {
                        return tpeliBean.findRange(desde, max);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }

    public LazyDataModel<TipoPelicula> getModelo() {
        return modelo;
    }

    public TipoPelicula getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPelicula registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new TipoPelicula();
        this.registro.setActivo(true);
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tpeliBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }

    public void btnEliminarHandler(ActionEvent ae) {
        tpeliBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }

    public void btnModificarHandler(ActionEvent ae) {
        tpeliBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
        this.registro = null;
    }
}
