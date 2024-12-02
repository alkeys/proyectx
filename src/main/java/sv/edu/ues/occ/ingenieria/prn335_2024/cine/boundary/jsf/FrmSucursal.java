package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named("frmSucursal")
@ViewScoped
public class FrmSucursal extends AbstractFormulario<Sucursal> implements Serializable {

    @Inject
    SucursalBean sBean;

    @Inject
    FacesContext facesContext;

    List<Sucursal> sucursalList;


    @PostConstruct
    @Override
    public void inicializar(){
        super.inicializar();
        try{
            this.sucursalList = sBean.findRange(0, Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje("Error al cargar los tipos", "Error al cargar" , FacesMessage.SEVERITY_ERROR);
        }
    }



    @Override
    protected Object getId(Sucursal Object) {
        return Object.getIdSucursal();
    }

    @Override
    protected AbstractDataPersistence<Sucursal> getDataBean() {
        return sBean;
    }

    @Override
    protected FacesContext facesContext() {
        return facesContext;
    }

    @Override
    protected Sucursal createNewRegistro() {
      return new Sucursal(); }

    @Override
    public String buscarIdPorRegistro(Sucursal entity) {
        return "";
    }

    @Override
    protected FacesContext getContext() {
        return null;
    }

    @Override
    public Sucursal buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String getTituloDePagina() {
        return "Sucursal";
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }




}
