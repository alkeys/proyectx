package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@Dependent
public class FrmAsiento extends AbstractPfFrm<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;

    @Inject
    FacesContext facesContext;

    @Inject
    SalaBean salaBean;

    @Inject
    FrmAsientoCaracteristica frmAsientoCaracteristica;


    List<Sala> listSala;
    int idSala;

    @PostConstruct
    public void inicializar(){
        super.inicializar();
        try{
            listSala= salaBean.findRange(0,Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("FrmSalaC","El init", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public AbstractDataPersistence<Asiento> getDataBean(){
        return asientoBean;
    }




    @Override
    public Asiento createNewEntity() {
        Asiento asi = new Asiento();
        if (idSala >= 0) {
            asi.setIdSala(new Sala(idSala));
        }
        return asi;
    }


    @Override
    public Object getId(Asiento o){
        return o.getIdAsiento();
    }

    @Override
    public String getTituloPag(){
        return "Asiento";
    }

    @Override
    public Asiento  buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Asiento entity) {
        return "";
    }

    @Override
    public int contar() {
        try {
            if (asientoBean != null) {
                return asientoBean.countSala(this.idSala);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public List<Asiento> cargarDatos(int firstResult, int maxResult) {
        try {
            if (asientoBean != null) {
                return asientoBean.findByIdSala(this.idSala, firstResult, maxResult);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public Integer getIdSalaSeleccionado() {
        if (this.registro != null && this.registro.getIdSala() != null) {
            return this.registro.getIdSala().getIdSala();
        }
        return null;
    }

    public void setIdSalaSeleccionado(final Integer idSalal) {
        if (this.registro != null && this.listSala != null && !this.listSala.isEmpty()) {
            this.registro.setIdSala(this.listSala.stream()
                    .filter(r -> r.getIdSala().equals(idSala))
                    .findFirst()
                    .orElse(null));
        }
    }


    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public AsientoBean getAsientoBean() {
        return asientoBean;
    }

    public void setAsientoBean(AsientoBean asientoBean) {
        this.asientoBean = asientoBean;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica frmAsientoCaracteristica) {
        this.frmAsientoCaracteristica = frmAsientoCaracteristica;
    }

}
