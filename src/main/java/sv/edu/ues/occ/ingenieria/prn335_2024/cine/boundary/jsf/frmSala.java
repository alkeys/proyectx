package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;


import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@ViewScoped
public class frmSala extends AbstractFormulario<Sala> implements Serializable {

    @Inject
    SalaBean salaBean;

    @Inject
    SucursalBean sucursalBean;

    @Inject
    FacesContext facesContext;

    @Inject
    FrmSalaCaracteristica frmSalaCaracteristica;

    @Inject
    FrmAsiento frmAsiento;

    @Inject
    FrmProgramacion frmProgramacion;

    @Inject
    FrmSucursal frmSucursal;

    List<Sucursal> sucursalList;

    Integer idSucursal;


    @PostConstruct
    @Override
    public void inicializar(){
        super.inicializar();
        try{
            this.sucursalList = sucursalBean.findRange(0, Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("Error al cargar los tipos", "Error al cargar" , FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public List<Sala> cargarDatos(int firstResult, int maxResults){
        try{
            if(salaBean!=null){
                return salaBean.findAll(firstResult,maxResults);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }


    public void cambiarTab(TabChangeEvent tce){
        if(tce.getTab().getTitle().equals("Caracteristicas")){
            if(this.registro!=null && this.frmSalaCaracteristica!=null){
                this.frmSalaCaracteristica.setIdSala(this.registro.getIdSala());
            }
        }
        if(tce.getTab().getTitle().equals("Asientos")){
            if(this.registro!=null && this.frmAsiento!=null){
                this.frmAsiento.setIdSala(this.registro.getIdSala());
            }
        }
    }



    @Override
    protected Object getId(Sala Object) {
        return Object.getIdSala();
    }

    @Override
    protected AbstractDataPersistence<Sala> getDataBean() {
        return salaBean;
    }

    @Override
    protected FacesContext facesContext() {
        return facesContext;
    }

    @Override
    protected Sala createNewRegistro() {
        Sala pc = new Sala();
        if(idSucursal!=null){
            pc.setIdSucursal(new Sucursal(idSucursal));
        }
        return pc;
    }

    @Override
    public String buscarIdPorRegistro(Sala entity) {
        if (entity != null && entity.getIdSala() != null) {
            return entity.getIdSala().toString();
        }
        return null;
    }

    @Override
    protected FacesContext getContext() {
        return null;
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
    public String getTituloDePagina() {
        return "Sala";
    }


    public Integer getIdSucursalSeleccionado() {
        if(this.registro!=null && this.registro.getIdSucursal()!=null){
            return this.registro.getIdSucursal().getIdSucursal();
        }
        return null;
    }


    public void setIdSucursalSeleccionado(final Integer idSucursall) {
        if(this.registro!=null && this.sucursalList!=null && !this.sucursalList.isEmpty()){
            this.registro.setIdSucursal(this.sucursalList.stream().filter(r->r.getIdSucursal().equals(idSucursall)).findFirst().orElse(null));
        }
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public FrmSalaCaracteristica getFrmSalaCaracteristica() {return frmSalaCaracteristica;}


    public FrmAsiento getFrmAsiento() {return frmAsiento;}

    public FrmProgramacion getFrmProgramacion() {
        return frmProgramacion;
    }


    String tooltip;

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public void actualizarTooltip(AjaxBehaviorEvent event) {
        TipoAsiento tipoSeleccionado = frmAsiento.frmAsientoCaracteristica.tipoAsientoList.stream()
                .filter(tp -> tp.getIdTipoAsiento().equals(frmAsiento.frmAsientoCaracteristica.getIdTipoAsientoSeleccionado()))
                .findFirst()
                .orElse(null);

        if (tipoSeleccionado != null) {
            this.tooltip = tipoSeleccionado.getExpresionRegular();
        } else {
            this.tooltip = "No hay expresión regular";
        }
    }


    public FrmSucursal getFrmSucursal() {
        return frmSucursal;
    }

    public void onSelect(SelectEvent<?> event) {

    }
}
