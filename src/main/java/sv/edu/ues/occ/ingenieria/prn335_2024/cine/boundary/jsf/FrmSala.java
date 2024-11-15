package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmSala extends AbstractPfFrm<Sala> implements Serializable {

    @Inject
    FrmAsiento frmAsiento;

    @Inject
    SalaBean salBean;

    @Inject
    SucursalBean sucursalBean;

    @Inject
    FacesContext facesContext;

    @Inject
    FrmSalaCaracteristica frmSalaCaracteristica;

    @Inject
    AsientoBean asientoBean;

    List<Sucursal> listSucursal;

    Long idSucursal;

    public enum PROCESOS_SALA{
        SALA_TIPOS,SALA_ASIENTOS;
    }
    PROCESOS_SALA procesos_sala;

    @PostConstruct
    public void inicializar(){
        super.inicializar();
        try{
            listSucursal = sucursalBean.findRange(0,Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("InitFrmSala","Error init", FacesMessage.SEVERITY_ERROR);
        }
    }

    public Integer getIdSucursalSeleccionado() {
        if (this.registro != null && this.registro.getIdSucursal() != null) {
            return this.registro.getIdSucursal().getIdSucursal();
        }
        return null;
    }

    public void setIdSucursalSeleccionado(final Integer idSucursal) {
        if (this.registro != null && this.listSucursal != null && !this.listSucursal.isEmpty()) {
            this.registro.setIdSucursal(this.listSucursal.stream()
                    .filter(r -> r.getIdSucursal().equals(idSucursal))
                    .findFirst()
                    .orElse(null));
        }
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public AbstractDataPersistence<Sala> getDataBean(){
        return salBean;
    }


    @Override
    public Sala createNewEntity(){
        return new Sala();
    }

    @Override
    public Object getId(Sala o){
        return o.getIdSala().toString();
    }

    @Override
    public String getTituloPag(){
        return "Sala";
    }


    public void cambiarTab(TabChangeEvent tce){
        if(tce.getTab().getTitle().equals("Características")){
            if(this.registro != null && this.frmSalaCaracteristica != null){
                this.frmSalaCaracteristica.setIdSala(this.registro.getIdSala());
            }
        }
        if(tce.getTab().getTitle().equals("Asientos")){
            if(this.registro != null && this.frmAsiento != null){
                this.frmAsiento.setIdSala(this.registro.getIdSala());

            }
        }

    }

    @Override
    public Sala buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdSala().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Sala salaEspecifica) {
        if (salaEspecifica != null && salaEspecifica.getIdSala() != null) {
            return salaEspecifica.getIdSala().toString();
        }
        return null;
    }



    public AsientoBean getAsientoBean() {
        return asientoBean;
    }

    public void setAsientoBean(AsientoBean asientoBean) {
        this.asientoBean = asientoBean;
    }

    public List<Sucursal> getListSucursal() {
        return listSucursal;
    }

    public void setListSucursal(List<Sucursal> listSucursal) {
        this.listSucursal = listSucursal;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public SalaBean getSalBean() {
        return salBean;
    }

    public void setSalBean(SalaBean salBean) {
        this.salBean = salBean;
    }

    public SucursalBean getSucursalBean() {
        return sucursalBean;
    }

    public void setSucursalBean(SucursalBean sucursalBean) {
        this.sucursalBean = sucursalBean;
    }

    public FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(FrmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }

    public FrmAsiento getFrmAsiento() {
        return frmAsiento;
    }

    public void setFrmAsiento(FrmAsiento frmAsiento) {
        this.frmAsiento = frmAsiento;
    }

}

