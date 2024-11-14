package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Named("frmReserva")
@ViewScoped
public class FrmReserva extends AbstractfrmImplementacion<Reserva> implements Serializable{

    @PersistenceContext
    private EntityManager em;

    private FacesContext facesContext=FacesContext.getCurrentInstance();
    private ReservaBean reservaBean;
    private ProgramacionBean programacionBean;
    private int pasoActual=1;
    private Integer paso=0;
    private TipoReserva tipoReservaSelecionado;

    private String diaSleccionado;
    private Pelicula peliculaSeleccionada;
    private Programacion programacionSelecionada;
    private String detallesPelicula;
    private List<Asiento> asientosDisponibles;
    private List<Asiento> asientosRerservados;
    private List<TipoReserva> listaTipoReserva;
    private List<Programacion> listaProgramaciones;

    private List<Programacion> programacionesDisponible;

    @Inject
    private TipoReservaBean tipoReservaBean;

    @Override
    public FacesContext getFacesContext(){
        return facesContext;
    }
    @Override
    public String getIdPorObjeto(Reserva reserva){
        return  reserva !=null ? reserva.getIdReserva().toString():null;
    }

    @Override
    public Reserva getObjetoPorId(String id){
        ReservaBean reservaBean=getDataAccess();
        return reservaBean.findReservaById(Long.parseLong(id));
    }

    @Override
    public ReservaBean getDataAccess(){
        return this.reservaBean;
    }

    @Override
    public void instanciarRegistro(){
        this.setRegistro(new Reserva());
    }

    public FrmReserva() {
        this.reservaBean=new ReservaBean();
    }

    // Método para obtener la lista de tipos de reserva
    public List<TipoReserva> getListaTipoReserva(){
        if (listaTipoReserva==null){
                listaTipoReserva=tipoReservaBean.obtenerListaTiposReserva();
        }
        return listaTipoReserva;
    }

    // En el bean o servicio TipoReservaBean
    public List<TipoReserva> obtenerListaTiposReserva() {
        return em.createQuery("SELECT t FROM TipoReserva t", TipoReserva.class)
                .getResultList();
    }


    public List<Pelicula> autocompleteFunciones(String query) {
        List<Pelicula> resultados = new ArrayList<>();

        if (diaSleccionado != null) {
            List<Programacion> programaciones = obtenerProgramacionPorFecha(diaSleccionado);

            // Filtramos las películas que coincidan con la consulta
            for (Programacion programacion : programaciones) {
                Pelicula pelicula = programacion.getIdPelicula();  // Suponiendo que Programacion tiene una relación ManyToOne con Pelicula
                String salaYHorario = programacion.getIdSala().getNombre() + " ("
                        + programacion.getDesde() + " - " + programacion.getHasta() + ")";
                if (pelicula.getNombre().toLowerCase().contains(query.toLowerCase())) {
                    pelicula.setAutocompletado(salaYHorario);  // Suponiendo que tienes un método para agregar el texto de la sala y horario
                    resultados.add(pelicula);
                }
            }
        }
        return resultados;
    }



    // Método para obtener programaciones en función de la fecha seleccionada
    private List<Programacion> obtenerProgramacionPorFecha(String fecha) {
        // Aquí deberías implementar la lógica de consulta a la base de datos
        // para obtener todas las programaciones en la fecha seleccionada
        return programacionBean.obtenerProgramaciones(fecha); // Ejemplo
    }
    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    // Método para avanzar al siguiente paso
    public void avanzarPaso() {
        if (paso< 1) {
            paso ++;
        }
    }


    public void retrocederPaso(){
        pasoActual--;
    }

    private void cargarDatosPaso(){
        switch (paso){
            case 2:
                cargarFunciones();
                break;
            case 3:
                cargarAsientosDisponibles();
                break;
            case 4:
                generarResumen();
                break;
            default:
                break;
        }
    }

    private void cargarFunciones(){
        if (tipoReservaSelecionado !=null && diaSleccionado!=null){
            try {
                List<Programacion> programacions=reservaBean.findProgramacionPorDia(tipoReservaSelecionado,diaSleccionado);
                if (!programacions.isEmpty()){
                    programacionSelecionada=programacions.get(0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public List<Asiento> obtenerAsientosDisponibles(Programacion programacionSeleccionada) {
        if (programacionSeleccionada != null) {
            // Accedemos a la sala de la programación seleccionada
            Sala sala = programacionSeleccionada.getIdSala();

            // Obtenemos los asientos disponibles de la sala
            String query = "SELECT a FROM Asiento a WHERE a.sala = :sala AND a.reservado = false AND a.activo = true";
            return em.createQuery(query, Asiento.class)
                    .setParameter("sala", sala)
                    .getResultList();
        }
        return new ArrayList<>();
    }

    private void cargarAsientosDisponibles(){
        try {
            if (programacionSelecionada !=null){
                asientosDisponibles=obtenerAsientosDisponibles(programacionSelecionada);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void reservarAsiento(Asiento asiento){
        if (asiento !=null){
            asientosDisponibles.remove(asiento);
            asientosRerservados.add(asiento);
        }
    }

    public void  eliminarAsientosReservados(Asiento asiento){
        if (asiento !=null){
            asientosRerservados.remove(asiento);
            asientosDisponibles.add(asiento);
        }
    }
    public void confirmarReserva(){
        try {
            crearReserva(getRegistro(), asientosRerservados);
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO,"Reserva confirmada",
                    "La reserva ha sido creada exitosamente.");
            facesContext.addMessage(null,msg);
            reinicarAsistente();
        }catch (Exception e){
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al confirmar la reserva",e.getMessage());
            facesContext.addMessage(null,msg);
        }
    }

    private void crearReserva(Reserva reserva, List<Asiento> asientos){
        getDataAccess().create(reserva);
    }

    private void reinicarAsistente(){
        pasoActual=1;
        setRegistro(null);
        tipoReservaSelecionado=null;
        diaSleccionado=null;
        peliculaSeleccionada=null;
        programacionSelecionada=null;
        asientosDisponibles=null;
        asientosRerservados=null;
        instanciarRegistro();
    }

    public void generarResumen() {
        // Crear un resumen de la reserva, con los detalles de la función, película, asientos reservados, etc.
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen de la Reserva:\n")
                .append("Tipo de Reserva: ").append(getRegistro().getIdTipoReserva().getNombre()).append("\n")
                .append("Fecha: ").append(getRegistro().getFechaReserva()).append("\n")
                .append("Película: ").append(programacionSelecionada.getIdPelicula().getNombre()).append("\n")
                .append("Sala: ").append(programacionSelecionada.getIdSala().getNombre()).append("\n")
                .append("Horario: ").append(programacionSelecionada.getDesde()).append(" - ")
                .append(programacionSelecionada.getHasta()).append("\n")
                .append("Asientos Reservados:\n");

        // Listar los asientos reservados en el resumen
        for (Asiento asiento : asientosRerservados) {
            resumen.append(" - Asiento: ").append(asiento.getNombre()).append("\n");
        }

        // Mostrar el resumen en la interfaz de usuario, por ejemplo, como un mensaje de información en la pantalla
        FacesMessage mensajeResumen = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resumen de la Reserva", resumen.toString());
        facesContext.addMessage(null, mensajeResumen);
    }

    @PostConstruct
    public void init() {
        listaTipoReserva =obtenerListaTiposReserva(); // Método para obtener los tipos de reserva
        listaProgramaciones = obtenerProgramaciones(); // Método para obtener las programaciones
    }


    /*
    GETTER Y SETTER PARA PASOACTUAL .ETC
     */
    public int getPasoActual() {
        return pasoActual;
    }

    public void setPasoActual(int pasoActual) {
        this.pasoActual = pasoActual;
    }



    public String getDiaSleccionado() {
        return diaSleccionado;
    }

    public void setDiaSleccionado(String diaSleccionado) {
        this.diaSleccionado = diaSleccionado;
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    public void setPeliculaSeleccionada(Pelicula peliculaSeleccionada) {
        this.peliculaSeleccionada = peliculaSeleccionada;
    }

    public Programacion getProgramacionSelecionada() {
        return programacionSelecionada;
    }

    public void setProgramacionSelecionada(Programacion programacionSelecionada) {
        this.programacionSelecionada = programacionSelecionada;
    }

    public List<Asiento> getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(List<Asiento> asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public List<Asiento> getAsientosRerservados() {
        return asientosRerservados;
    }

    public void setAsientosRerservados(List<Asiento> asientosRerservados) {
        this.asientosRerservados = asientosRerservados;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;

    }

    public TipoReserva getTipoReservaSelecionado() {
        return tipoReservaSelecionado;
    }

    public void setTipoReservaSelecionado(TipoReserva tipoReservaSelecionado) {
        this.tipoReservaSelecionado = tipoReservaSelecionado;
    }

    public void mostrarDetallesPelicula() {
        if (peliculaSeleccionada != null) {
            // Mostrar detalles de la película seleccionada
            System.out.println("Detalles de la película seleccionada:");
            System.out.println("Nombre: " + peliculaSeleccionada.getNombre());
            System.out.println("Sinopsis: " + peliculaSeleccionada.getSinopsis());

            // Si tienes otros detalles, puedes agregarlos aquí
            // Ejemplo: Mostrar la lista de características de la película
            if (peliculaSeleccionada.getPeliculaCaracteristicaList() != null) {
                System.out.println("Características de la película:");
                for (PeliculaCaracteristica caracteristica : peliculaSeleccionada.getPeliculaCaracteristicaList()) {
                    System.out.println(" - " + caracteristica.getIdPeliculaCaracteristica());
                }
            }
        } else {
            System.out.println("No se ha seleccionado ninguna película.");
        }
    }


    // Método que obtiene las programaciones de la base de datos
    public void listaProgramaciones() {
        try {
            if (tipoReservaSelecionado != null && diaSleccionado != null) {
                listaProgramaciones = programacionBean.obtenerProgramaciones(tipoReservaSelecionado,diaSleccionado);
            }
        } catch (Exception e) {
            // Manejo de errores, si es necesario
            e.printStackTrace();
        }
    }

    public List<Programacion> obtenerProgramacionPorFecha(LocalDate diaSeleccionado) {
        // Asegúrate de que entityManager esté configurado correctamente
        String query = "SELECT p FROM Programacion p WHERE p.desde >= :diaSeleccionado AND p.hasta <= :diaSeleccionado";
        return em.createQuery(query, Programacion.class)
                .setParameter("diaSeleccionado", diaSeleccionado)
                .getResultList();
    }

    private List<Programacion> obtenerProgramaciones() {
        // Realizar una consulta JPQL para obtener las programaciones
        try {
            // Suponiendo que las programaciones se ordenan por fecha (por ejemplo 'desde')
            return em.createQuery("SELECT p FROM Programacion p WHERE p.hasta >= CURRENT_TIMESTAMP ORDER BY p.desde ASC", Programacion.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de excepciones en caso de que ocurra algún error
            return Collections.emptyList();  // Retornar lista vacía si hay un error
        }
    }


}
