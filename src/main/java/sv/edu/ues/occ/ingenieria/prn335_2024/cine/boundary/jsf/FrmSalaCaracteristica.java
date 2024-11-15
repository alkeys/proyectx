package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmSalaCaracteristica extends AbstractPfFrm<SalaCaracteristica> implements Serializable {


    @Inject
    SalaCaracteristicaBean salCBean;

    @Inject
    TipoSalaBean tipoSalaBean;

    @Inject
    FacesContext facesContext;


    List<TipoSala> tipoSalaList;
    int idSala;


    @PostConstruct
    public void inicializar(){
        super.inicializar();
        try{
            this.tipoSalaList = tipoSalaBean.findRange(0,Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("FrmSalaC","El init", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public SalaCaracteristica createNewEntity() {
        SalaCaracteristica sc = new SalaCaracteristica();
        if (idSala >= 0) {
            sc.setIdSala(new Sala(idSala));
        }
        if (tipoSalaList != null && !tipoSalaList.isEmpty()) {
            sc.setIdTipoSala(tipoSalaList.getFirst());
        }
        return sc;
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public AbstractDataPersistence<SalaCaracteristica> getDataBean(){
        return salCBean;
    }



    @Override
    public Object getId(SalaCaracteristica o){
        return o.getIdSalaCaracteristica().toString();
    }

    @Override
    public String getTituloPag(){
        return "Sala Caracteristica";
    }




    public Integer getIdTipoSalaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoSala() != null) {
            return this.registro.getIdTipoSala().getIdTipoSala();
        }
        return null;
    }

    public void setIdTipoSalaSeleccionado(final Integer idTipoSala) {
        if (this.registro != null && this.tipoSalaList != null && !this.tipoSalaList.isEmpty()) {
            this.registro.setIdTipoSala(this.tipoSalaList.stream()
                    .filter(r -> r.getIdTipoSala().equals(idTipoSala))
                    .findFirst()
                    .orElse(null));
        }
    }

    @Override
    public int contar() {
        try {
            if (salCBean != null) {
                return salCBean.countSala(this.idSala);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public List<SalaCaracteristica> cargarDatos(int firstResult, int maxResult) {
        try {
            if (salCBean != null) {
                return salCBean.findByIdSala(this.idSala, firstResult, maxResult);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
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
    public String buscarIdPorRegistro(SalaCaracteristica dato) {
        if (dato != null && dato.getIdSalaCaracteristica() != null) {
            return dato.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    public SalaCaracteristicaBean getSalCBean() {
        return salCBean;
    }

    public void setSalCBean(SalaCaracteristicaBean salCBean) {
        this.salCBean = salCBean;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public TipoSalaBean getTipoSalaBean() {
        return tipoSalaBean;
    }

    public void setTipoSalaBean(TipoSalaBean tipoSalaBean) {
        this.tipoSalaBean = tipoSalaBean;
    }

    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }


}
