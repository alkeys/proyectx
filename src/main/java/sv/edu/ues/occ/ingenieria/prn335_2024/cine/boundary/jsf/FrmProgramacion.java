package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Named
@Dependent
public class FrmProgramacion extends AbstractFormulario<Programacion> implements Serializable {

    @EJB
    ProgramacionBean prBean;

    @Inject
    FacesContext facesContext;

    @Inject
     PeliculaBean pBean;

    @Inject
    CalendarioFunciones calendarSchedule;

    @Inject
    frmPelicula frmPelicula;

    


    @Override
    protected Object getId(Programacion Object) {
        return Object.getIdProgramacion();
    }

    @Override
    protected AbstractDataPersistence<Programacion> getDataBean() {
        return prBean;
    }

    @Override
    protected FacesContext facesContext() {
        return facesContext;
    }

    @Override
    protected Programacion createNewRegistro() {
        return new Programacion();
    }

    @Override
    public String buscarIdPorRegistro(Programacion entity) {
        if (entity != null && entity.getIdProgramacion() != null) {
            return entity.getIdProgramacion().toString();
        }
        return null;
    }

    @Override
    protected FacesContext getContext() {
        return null;
    }

    @Override
    public Programacion buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream()
                    .filter(r -> r.getIdProgramacion().toString().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String getTituloDePagina() {
        return "Programación";
    }


    @Override
    public List<Programacion> cargarDatos(int firstResult, int maxResults){
        try{
            if(prBean!=null){
                return prBean.findAll(firstResult,maxResults);
            }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public void validarValor(FacesContext fc, UIComponent componente, Object valor){
        UIInput input = (UIInput) componente;

        if(registro!= null && this.registro.getIdProgramacion()!=null && registro.getDesde().isBefore(registro.getHasta())){
            String nuevo= valor.toString();
            Pattern patron=Pattern.compile(this.registro.getIdProgramacion().toString());
            Matcher validador= patron.matcher(nuevo);
            if(validador.find()){
                input.setValid(true);
                return;
            }
        }
        input.setValid(false);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor incorrecto", "Valor ingresado inválidoS."));

    }



    public CalendarioFunciones getCalendarSchedule() {
        return calendarSchedule;
    }



}
