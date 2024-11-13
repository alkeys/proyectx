package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFrm<T> implements Serializable {

    public abstract AbstractDataPersistence<T> getDataAccess();
    LazyDataModel<T> modelo;
    Estado_crud estado = Estado_crud.NINGUNO;
    T registro = null;
    public abstract FacesContext getFacesContext();


    public int contar() {
        int resultado = 0;
        AbstractDataPersistence<T> tsBean = null;
        try {
            tsBean = getDataAccess();
            resultado = tsBean.count();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return resultado;
    }

    public List<T> cargarDatos(int primero, int tamanio) {
        List<T> resultado = null;
        try {
            AbstractDataPersistence<T> trBean = getDataAccess();
            resultado = trBean.findRange(primero, tamanio);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            if (resultado == null) {
                resultado = Collections.EMPTY_LIST;
            }
        }
        return resultado;
    }

    @PostConstruct
    public void inicializar() {
        inicializarRegistros();

    }

    public void inicializarRegistros() {

        this.modelo = new LazyDataModel<T>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return contar();
            }

            @Override
            public List<T> load(int desde, int hasta, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {

                return cargarDatos(desde, hasta);
            }

            @Override
            public String getRowKey(T object) {
                if (object != null) {
                    return getIdPorObjeto(object);
                }
                return null;
            }

            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null) {
                    return getObjetoPorId(rowKey);

                }
                return null;
            }

        };

    }

    public abstract String getIdPorObjeto(T object);

    public abstract T getObjetoPorId(String id);

    public abstract void instanciarRegistro();

    public void btnSeleccionarRegistroHandler() {
        this.estado = Estado_crud.MODIFICAR;
    }

    public void btnNuevoHandler(ActionEvent ae) {
        this.instanciarRegistro();
        this.estado = Estado_crud.CREAR;
    }

    public void btnCancelarHandler(ActionEvent ae) {
        this.registro = null;
        this.estado = Estado_crud.NINGUNO;
    }

    public void btnModificarHandler(ActionEvent ae) {
        FacesMessage mensaje = new FacesMessage();
        T modify = null;
        try {
            AbstractDataPersistence<T> trBean = getDataAccess();
            modify = trBean.update(registro);
            if (modify != null) {
                this.estado = Estado_crud.NINGUNO;
                this.registro = null;
                mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
                mensaje.setSummary("Registro Modificado con exito");
                getFacesContext().addMessage(null, mensaje);
            }

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo modificar el registro", "No se modificó el registro");
            getFacesContext().addMessage(null, mensaje);
        }
    }

    public void btnEliminarHandler(ActionEvent ae) {
        FacesMessage mensaje = null;
        try {
            AbstractDataPersistence<T> trBean = getDataAccess();
            trBean.delete(registro);
            this.estado = Estado_crud.NINGUNO;
            this.registro = null;
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado con éxito", "Se eliminó el registro");
            getFacesContext().addMessage(null, mensaje);

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar el registro", "No se eliminó el registro");
            getFacesContext().addMessage(null, mensaje);
        }
    }

    public void btnGuardarHandler(ActionEvent ae) {
        FacesMessage mensaje = null;
        try {
            AbstractDataPersistence<T> trBean = getDataAccess();
            trBean.create(registro);
            this.estado = Estado_crud.NINGUNO;
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro guardado con exito", "Se creo el registro");
            getFacesContext().addMessage(null, mensaje);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo guardar el registro", "no se creo el registro");
            getFacesContext().addMessage(null, mensaje);
        }
        this.registro = null;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public LazyDataModel<T> getModelo() {
        return this.modelo;
    }

    public Estado_crud getEstado() {
        return estado;
    }
}






