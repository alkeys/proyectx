package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmPeliculaCaracteristica extends AbstractPfFrm<PeliculaCaracteristica> implements Serializable {

    @Inject
    PeliculaCaracteristicaBean pcBean;

    @Inject
    TipoPeliculaBean tpBean;

    @Inject
    FacesContext facesContext;

    List<TipoPelicula> tipoPeliculasList;

    Long idPelicula;


    @PostConstruct
    public void inicializar() {
        super.inicializar();

        try {
            this.tipoPeliculasList = tpBean.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("Error al cargar los tipos Peli", "Error al cargar", FacesMessage.SEVERITY_ERROR);
        }


    }

    @Override
    public List<PeliculaCaracteristica> cargarDatos(int firstResult, int maxResult) {
        try {
            if (this.idPelicula != null && pcBean != null) {
                return pcBean.findByIdPelicula(this.idPelicula, firstResult, maxResult);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contar() {
        try {
            if (this.idPelicula != null && pcBean != null) {
                return pcBean.countPelicula(this.idPelicula);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }


    @Override
    public String getTituloPag() {
        return "Tipo de Pelicula";
    }


    @Override
    public Object getId(PeliculaCaracteristica peliculaCaracteristica) {
        return peliculaCaracteristica.getIdPeliculaCaracteristica();
    }

    @Override
    public PeliculaCaracteristica createNewEntity() {
        PeliculaCaracteristica pc = new PeliculaCaracteristica();
        if (idPelicula != null) {
            pc.setIdPelicula(new Pelicula(idPelicula));
        }
        if (tipoPeliculasList != null && tipoPeliculasList.isEmpty()) {
            pc.setIdTipoPelicula(tipoPeliculasList.getFirst());
        }
        return pc;
    }

    @Override
    public AbstractDataPersistence<PeliculaCaracteristica> getDataBean() {
        return pcBean;
    }




    @Override
    public PeliculaCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdPeliculaCaracteristica().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(PeliculaCaracteristica dato) {
        if (dato != null && dato.getIdPeliculaCaracteristica() != null) {
            return dato.getIdPeliculaCaracteristica().toString();
        }
        return null;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public List<TipoPelicula> getTipoPeliculasList() {
        return tipoPeliculasList;
    }

    public Integer getIdTipoPeliculaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoPelicula() != null) {
            return this.registro.getIdTipoPelicula().getIdTipoPelicula();
        }
        return null;
    }

    public void setIdTipoPeliculaSeleccionado(final Integer idTipoPelicula) {
        if (this.registro != null && this.tipoPeliculasList != null && !this.tipoPeliculasList.isEmpty()) {
            this.registro.setIdTipoPelicula(this.tipoPeliculasList.stream()
                    .filter(r -> r.getIdTipoPelicula().equals(idTipoPelicula))
                    .findFirst()
                    .orElse(null));
        }
    }

    public void setTipoPeliculasList(List<TipoPelicula> tipoPeliculasList) {
        this.tipoPeliculasList = tipoPeliculasList;
    }

    public void validarValor(FacesContext fc, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;
        System.out.printf("Registro: %s", this.registro.getIdTipoPelicula().getIdTipoPelicula());
        System.out.printf("Registro.idTipoPelicula: %s%n", this.registro.getIdTipoPelicula());
        if (this.registro != null && this.registro.getIdTipoPelicula() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoPelicula().getExpresionRegular());
            Matcher validator = patron.matcher(nuevo);
            if (validator.find()) {
                input.setValid(true);
                input.setValue(nuevo); // Actualiza el valor del componente
                return;
            }
        }
        input.setValid(false);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Valor ingresado inválido."));
    }

    public PeliculaCaracteristicaBean getPcBean() {
        return pcBean;
    }

    public void setPcBean(PeliculaCaracteristicaBean pcBean) {
        this.pcBean = pcBean;
    }

    public TipoPeliculaBean getTpBean() {
        return tpBean;
    }

    public void setTpBean(TipoPeliculaBean tpBean) {
        this.tpBean = tpBean;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }
}
