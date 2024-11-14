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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class frmPeliculaCaracteristica implements Serializable {

    @Inject
    FacesContext facesContext;

    @Inject
    PeliculaCaracteristicaBean caracteristicaBean;

    private LazyDataModel<PeliculaCaracteristica> modelo;
    private PeliculaCaracteristica registro;
    private String regexValidacion;
    private String mensajeValidacion;

    @PostConstruct
    public void inicializar() {
        modelo = new LazyDataModel<>() {
            @Override
            public String getRowKey(PeliculaCaracteristica object) {
                return object != null && object.getId() != null ? object.getId().toString() : null;
            }

            @Override
            public PeliculaCaracteristica getRowData(String rowKey) {
                return getWrappedData() != null
                        ? getWrappedData().stream().filter(r -> rowKey.equals(r.getId().toString())).findFirst().orElse(null)
                        : null;
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return caracteristicaBean.count();
            }

            @Override
            public List<PeliculaCaracteristica> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                return caracteristicaBean.findRange(first, pageSize);
            }
        };
    }


    public void btnNuevoHandler(ActionEvent event) {
        registro = new PeliculaCaracteristica();
    }

    public void btnGuardarHandler(ActionEvent event) {
        if (Pattern.matches(regexValidacion, registro.getValor())) {
            caracteristicaBean.create(registro);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado exitosamente", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeValidacion, null));
        }
    }

    public void btnEliminarHandler(ActionEvent event) {
        caracteristicaBean.delete(registro);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null));
    }

    public void btnModificarHandler(ActionEvent event) {
        if (Pattern.matches(regexValidacion, registro.getValor())) {
            caracteristicaBean.update(registro);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeValidacion, null));
        }
    }

    // Getters and Setters
    public LazyDataModel<PeliculaCaracteristica> getModelo() { return modelo; }
    public PeliculaCaracteristica getRegistro() { return registro; }
    public void setRegistro(PeliculaCaracteristica registro) { this.registro = registro; }
    public String getMensajeValidacion() { return mensajeValidacion; }
}
