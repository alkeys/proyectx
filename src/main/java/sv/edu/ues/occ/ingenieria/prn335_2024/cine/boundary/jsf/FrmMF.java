package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmMF extends  AbstractPfFrm<Programacion> implements Serializable {

    List<Programacion> programacions;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    int idSala;
    private String serverTimeZone = ZoneId.systemDefault().toString();

    @Inject
    ProgramacionBean programacionBean;

    @Inject
    FacesContext facesContext;



    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public String getTituloPag() {
        return "Programacion";
    }

    @Override
    public Object getId(Programacion object) {
        return  object.getIdProgramacion();
    }

    @Override
    public Programacion createNewEntity() {
        return new  Programacion();
    }

    @Override
    public AbstractDataPersistence<Programacion> getDataBean() {
        return programacionBean;
    }

    @Override
    public Programacion buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Programacion entity) {
        return "";
    }

    @PostConstruct
    public void init(){
        try{
            super.inicializar();
            eventModel = new DefaultScheduleModel();
            programacions = programacionBean.findRange(0, Integer.MAX_VALUE);
            programacions= cargarDatos();

            for (Programacion programacion : programacions) {
                LocalDateTime desde = programacion.getDesde().toLocalDateTime();
                LocalDateTime hasta = programacion.getHasta().toLocalDateTime();

                if (desde!=null && hasta!=null){
                   ScheduleEvent <?> event1 = DefaultScheduleEvent.builder()
                           .title("sala"+programacion.getIdSala().getIdSala()+"-Pelicula"+programacion.getIdPelicula().getIdPelicula())
                           .startDate(desde).endDate(hasta).description(programacion.getComentarios())
                           .build();
                   eventModel.addEvent(event1);
                }
            }

        }
        catch (Exception e){
            Logger.getLogger(FrmMF.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public List<Programacion> cargarDatos(){
        return programacionBean.findRange(0, Integer.MAX_VALUE);
    }
    public String getServerTimeZone() {
        return serverTimeZone;
    }
    public void setServerTimeZone(String serverTimeZone){
        this.serverTimeZone = serverTimeZone;
    }
    public void setProgramacions(List<Programacion> programacions) {
        this.programacions = programacions;
    }
    public  void setEventModel(ScheduleModel eventModel){
        this.eventModel = eventModel;
    }
    public ScheduleModel getEventModel(){
        return eventModel;
    }
    public ScheduleEvent getEvent(){
        return event;
    }
    public void setEvent(ScheduleEvent event){
        this.event = event;
    }
    public int getIdSala(){
        return idSala;
    }
    public void setIdSala(int idSala){
        this.idSala = idSala;
    }
    public void deleteEvent() {
        eventModel.deleteEvent(event);
        event = new DefaultScheduleEvent();
    }
    public void addEvent() {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);

        event = new DefaultScheduleEvent();
    }

    public void cargarEventosDelMes() {
        LocalDate inicioDelMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finDelMes = inicioDelMes.plusMonths(1).minusDays(1);

        for (LocalDate date = inicioDelMes; !date.isAfter(finDelMes); date = date.plusDays(1)) {
            ScheduleEvent<?> event =  DefaultScheduleEvent.builder()
                    .title("Evento del día " + date.getDayOfMonth())
                    .startDate(date.atStartOfDay())
                    .endDate(date.atStartOfDay().plusDays(1))
                    .build();
            eventModel.addEvent(event);
        }
    }
    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }
}
