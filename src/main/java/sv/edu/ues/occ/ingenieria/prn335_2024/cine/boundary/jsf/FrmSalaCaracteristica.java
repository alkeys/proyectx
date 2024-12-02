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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Named
@Dependent
public class FrmSalaCaracteristica extends AbstractFormulario<SalaCaracteristica> implements Serializable {

    @Inject
    SalaCaracteristicaBean scBean;

    @Inject
    FacesContext facesContext;

    @Inject
    TipoSalaBean tsBean;

    List<TipoSala> tipoSalaList;

    Integer idSala;


    @PostConstruct
    @Override
    public void inicializar(){
        super.inicializar();
        try{
            this.tipoSalaList = tsBean.findRange(0, Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("Error al cargar los tipos", "Error al cargar" , FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public List<SalaCaracteristica> cargarDatos(int firstResult, int maxResults){
        try{
            if(this.idSala != null && scBean!=null){
                return scBean.findByIdSala(this.idSala,firstResult,maxResults);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    @Override
    public int contarRegistros() {
        try {
            if (idSala != null && scBean != null) {
                return scBean.countSala(this.idSala);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return 0;

    }


    @Override
    protected Object getId(SalaCaracteristica Object) {
        return Object.getIdSalaCaracteristica();
    }

    @Override
    protected AbstractDataPersistence<SalaCaracteristica> getDataBean() {
        return scBean;
    }

    @Override
    protected FacesContext facesContext() {
        return facesContext;
    }

    @Override
    protected SalaCaracteristica createNewRegistro() {
        SalaCaracteristica sc = new SalaCaracteristica();
        if(idSala!=null){
            sc.setIdSala(new Sala(idSala));
        }
        if(tipoSalaList!=null && tipoSalaList.isEmpty()){
            sc.setIdTipoSala(tipoSalaList.getFirst());
        }
        return sc;
    }

    @Override
    public String buscarIdPorRegistro(SalaCaracteristica entity) {
        if (entity != null && entity.getIdSalaCaracteristica() != null) {
            return entity.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    protected FacesContext getContext() {
        return null;
    }

    @Override
    public SalaCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdSalaCaracteristica().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String getTituloDePagina() {
        return "Sala Caracteristica";
    }


    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }


    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }


    public Integer getIdTipoSalaSeleccionado() {
        if(this.registro!=null && this.registro.getIdTipoSala()!=null){
            return this.registro.getIdTipoSala().getIdTipoSala();
        }
        return null;
    }


    public void setIdTipoSalaSeleccionado(final Integer idTipoSala) {
        if(this.registro!=null && this.tipoSalaList!=null && !this.tipoSalaList.isEmpty()){
            this.registro.setIdTipoSala(this.tipoSalaList.stream().filter(r->r.getIdTipoSala().equals(idTipoSala)).findFirst().orElse(null));
        }
    }


    public void validarValor(FacesContext fc, UIComponent componente, Object valor){
        UIInput input = (UIInput) componente;

        if(registro!= null && this.registro.getIdTipoSala()!=null){
            String nuevo= valor.toString();
            Pattern patron=Pattern.compile(this.registro.getIdTipoSala().getExpresionRegular());
            Matcher validador= patron.matcher(nuevo);
            if(validador.find()){
                input.setValid(true);
                return;
            }
        }
        input.setValid(false);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor incorrecto", "Valor ingresado inválidoS."));
    }
}
