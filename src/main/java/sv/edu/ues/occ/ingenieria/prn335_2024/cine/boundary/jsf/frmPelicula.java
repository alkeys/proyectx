package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;

@Named
@ViewScoped
public class frmPelicula extends AbstractPfFrm<Pelicula> implements Serializable {

    @Inject
    PeliculaBean pBean;

    @Inject
    FacesContext facesContext;

    @Inject
    FrmPeliculaCaracteristica frmPeliculaCaracteristica;

    public void cambiarTab(TabChangeEvent tce){
        if(tce.getTab().getTitle().equals("Caracteristicas")){
            if(this.registro != null && this.frmPeliculaCaracteristica != null){
                this.frmPeliculaCaracteristica.setIdPelicula(this.registro.getIdPelicula());
            }
        }
    }


    @Override
    public String getTituloPag() {
        return "Pelicula";
    }

    @Override
    public Object getId(Pelicula object) {
        return object.getIdPelicula();
    }

    @Override
    public Pelicula createNewEntity() {
        return new Pelicula();
    }

    @Override
    public AbstractDataPersistence<Pelicula> getDataBean() {
        return pBean;
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public Pelicula buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdPelicula().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Pelicula dato) {
        if (dato != null && dato.getIdPelicula() != null) {
            return dato.getIdPelicula().toString();
        }
        return null;
    }

    public FrmPeliculaCaracteristica getFrmPeliculaCaracteristica() {
        return frmPeliculaCaracteristica;
    }

    public PeliculaBean getpBean() {
        return pBean;
    }

    public void setpBean(PeliculaBean pBean) {
        this.pBean = pBean;
    }


    public void setFrmPeliculaCaracteristica(FrmPeliculaCaracteristica frmPeliculaCaracteristica) {
        this.frmPeliculaCaracteristica = frmPeliculaCaracteristica;
    }
}