package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmAsiento extends AbstractFormulario<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;

    @Inject
    FacesContext facesContext;

    @Inject
    FrmAsientoCaracteristica frmAsientoCaracteristica;

    Integer idSala;




    @Override
    protected Object getId(Asiento Object) {
        return Object.getIdAsiento();
    }

    @Override
    protected AbstractDataPersistence<Asiento> getDataBean() {
        return asientoBean;
    }

    @Override
    protected FacesContext facesContext() {
        return facesContext;
    }

    @Override
    protected Asiento createNewRegistro() {
        Asiento asiento = new Asiento();
        if(idSala!=null){
            asiento.setIdSala(new Sala(idSala));
        }
        return asiento;
    }

    @Override
    public String buscarIdPorRegistro(Asiento entity) {
        return "";
    }

    @Override
    protected FacesContext getContext() {
        return null;
    }

    @Override
    public Asiento buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String getTituloDePagina() {
        return "Asiento";
    }

    @Override
    public List<Asiento> cargarDatos(int firstResult, int maxResults){
        try{
            if(this.idSala != null && asientoBean!=null){
                return asientoBean.findByIdSala(this.idSala,firstResult,maxResults);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica frmAsientoCaracteristica) {
        this.frmAsientoCaracteristica = frmAsientoCaracteristica;
    }
}
