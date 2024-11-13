package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class frmTipoSala implements Serializable {

    @Inject
    private FacesContext facesContext;

    @Inject
    private TipoSalaBean tsBean;

    private LazyDataModel<TipoSala> modelo;
    private List<TipoSala> registros;
    private TipoSala registro;

    @PostConstruct
    public void init() {
        registros = tsBean.findRange(0, 1000000); // Cambié Optional<Object> a List<TipoSala>
    }

    public void btnSeleccionarRegistroHandler(final Integer idTipoSala) {
        if(idTipoSala == null) {
            this.registro = registros.stream()
                    .filter(r -> r.getIdTipoSala().equals(idTipoSala))
                    .findFirst()
                    .orElse(null);
        }
        this.registro = null;
    }

    public List<TipoSala> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoSala> registros) {
        this.registros = registros;
    }

    public Integer getSeleccionado() {
        return (registro != null) ? registro.getIdTipoSala() : null;
    }

    public void seleccionado(Integer seleccionado) {
        this.registro = registros.stream()
                .filter(r -> r.getIdTipoSala().equals(seleccionado))
                .findFirst()
                .orElse(null);
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
        this.registro.setActivo(true);
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
    }

    public void btnGuardarHandler(ActionEvent ae) {
        tsBean.create(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos guardados exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnEliminarHandler(ActionEvent ae) {
        tsBean.delete(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }

    public void btnModificarHandler(ActionEvent ae) {
        tsBean.update(registro);
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado exitosamente", null);
        facesContext.addMessage(null, mensaje);
    }
}
