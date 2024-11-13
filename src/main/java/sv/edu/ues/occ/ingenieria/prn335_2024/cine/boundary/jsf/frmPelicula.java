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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class frmPelicula implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    PeliculaBean tpeliBean;
    LazyDataModel<Pelicula> modelo;
    Pelicula registro;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<Pelicula>() {

            @Override
            public String getRowKey(Pelicula object) {
                if (object != null && object.getIdPelicula() != null) {
                    return object.getIdPelicula().toString();
                }
                return null;
            }

            @Override
            public Pelicula getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() !=null) {
                    return getWrappedData().stream().filter(r->rowKey.trim().equals(r.getIdPelicula().toString())).findFirst().orElse(null);
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
            public List<Pelicula> load(int desde, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
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

    public LazyDataModel<Pelicula> getModelo() {
        return modelo;
    }

    public Pelicula getRegistro() {
        return registro;
    }

    public void setRegistro(Pelicula registro) {
        this.registro = registro;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.registro = new Pelicula();
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tpeliBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnEliminarHandler(ActionEvent ae) {
        tpeliBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);

    }

    public void btnModificarHandler(ActionEvent ae) {
        tpeliBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

}