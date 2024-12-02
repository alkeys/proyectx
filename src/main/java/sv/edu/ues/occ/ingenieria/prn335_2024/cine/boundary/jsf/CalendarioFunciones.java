package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CalendarioFunciones implements Serializable {

    private  Programacion programacion;
    private  Pelicula pelicula;
    private String estado="NINGUNO";
    private ScheduleModel eventModel;
    private ScheduleEvent<?> event = new DefaultScheduleEvent();

    private String timeFormat;
    private String serverTimeZone = ZoneId.systemDefault().toString();
    private String view = "timeGridWeek";
    private String nombrePelicula;


    @Inject
    FrmProgramacion frmProgramacion;

    @Inject
    sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.frmSala frmSala;


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public void obtenerFechaFin(){
        List<Pelicula> peliculas = frmProgramacion.frmPelicula.cargarDatos(0, Integer.MAX_VALUE);
        for(Pelicula p: peliculas){
            if(p.getNombre().equals(nombrePelicula)){
                pelicula=p;
                for(PeliculaCaracteristica pc: p.getPeliculaCaracteristicaList()) {
                    if (pc.getIdTipoPelicula().getNombre().toUpperCase().equals("DURACION")) {
                        event.setEndDate( event.getStartDate().plusMinutes(Long.parseLong(pc.getValor())));
                    }
                }
            }

        }
    }

    public void crearProgramacion(){
        try {
            if(pelicula!=null && pelicula.getIdPelicula()!=null
        && event!=null){
            programacion=frmProgramacion.createNewRegistro();
            programacion.setIdSala(frmSala.getRegistro());
            programacion.setIdPelicula(pelicula);
            programacion.setDesde(event.getStartDate().atOffset(ZoneOffset.ofHours(-6)));
            programacion.setHasta(event.getEndDate().atOffset(ZoneOffset.ofHours(-6)));
            if(programacion.getDesde().isBefore(programacion.getHasta())) {
                frmProgramacion.prBean.create(programacion);
                agregarProgramacion(frmProgramacion.cargarDatos(0, Integer.MAX_VALUE), frmSala.getRegistro().getIdSala());
            }else{
                mensajeAdvertencia("La fecha Fin no puede ser menor a la fecha de Inicio");
            }
        }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void modificarProgramacion() {
        try {
            if (event != null && event.getId() != null) {
                programacion = frmProgramacion.prBean.findById(Long.parseLong(event.getId()));
                programacion.setDesde(event.getStartDate().atOffset(ZoneOffset.ofHours(-6)));
                programacion.setHasta(event.getEndDate().atOffset(ZoneOffset.ofHours(-6)));
                if(programacion.getDesde().isBefore(programacion.getHasta())) {
                    frmProgramacion.prBean.update(programacion);
                    agregarProgramacion(frmProgramacion.cargarDatos(0, Integer.MAX_VALUE), frmSala.getRegistro().getIdSala());
                }else{
                    mensajeAdvertencia("La fecha Fin no puede ser menor a la fecha de Inicio");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void eliminarProgramacion(){
            try {
                if(event!=null && event.getId()!=null ){
                    programacion=frmProgramacion.prBean.findById(Long.parseLong(event.getId()));
                    frmProgramacion.prBean.delete(programacion);
                    agregarProgramacion(frmProgramacion.cargarDatos(0,Integer.MAX_VALUE), frmSala.getRegistro().getIdSala());
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
    }


    public String getNombrePelicula() {
        return nombrePelicula = event.getTitle();
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> peliculaList = new ArrayList<>();
        List<Pelicula> pelicula = frmProgramacion.frmPelicula.cargarDatos(0, Integer.MAX_VALUE);
        for (Pelicula pelicula1 : pelicula) {
            peliculaList.add(pelicula1.getNombre());
        }
        return peliculaList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public ScheduleModel agregarProgramacion(List<Programacion> programaciones, Integer idSala) {
        eventModel = new DefaultScheduleModel();
        for(Programacion p:programaciones){
        if(p.getIdSala().getIdSala().equals(idSala)) {
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .id(p.getIdProgramacion().toString())
                    .title(p.getIdPelicula().getNombre())
                    .startDate(p.getDesde().toLocalDateTime())
                    .endDate(p.getHasta().toLocalDateTime())
                    .borderColor("blue")
                    .build();
            eventModel.addEvent(event);
        }
        }
        return eventModel;
    }

    public void mensajeAdvertencia(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", mensaje);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public ScheduleModel getEventModel() {
        agregarProgramacion(frmProgramacion.cargarDatos(0,100), frmSala.getRegistro().getIdSala());
        return eventModel;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
       setEstado("MODIFICAR");
        event = selectEvent.getObject();

    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
      setEstado("CREAR");
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(0))
                .build();
    }


    public String getView() {
        return view;
    }


    public void setView(String view) {
        this.view = view;
    }


    public String getTimeFormat() {
        return timeFormat;
    }


    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }


    public String getServerTimeZone() {
        return serverTimeZone;
    }


    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }
}