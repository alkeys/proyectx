package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class AbstractPfFrm<T> implements Serializable {

     LazyDataModel<T> modelo;
     T registro;
     ESTADO_CRUD estado;

    public abstract String getTituloPag();

    public abstract Object getId(T object);

    public abstract T createNewEntity();

    public abstract AbstractDataPersistence<T> getDataBean();

    public abstract FacesContext getFacesContext();

    public abstract T buscarRegistroPorId(String id);

    public abstract String buscarIdPorRegistro(T entity);

    int registrosPorPagina = 10; // Valor por defecto



    @PostConstruct
    public void inicializar() {
        estado = ESTADO_CRUD.NINGUNO;
        modelo = new LazyDataModel<T>() {
            @Override
            public String getRowKey(T object) {
                Object id = getId(object);
                if (object != null && id != null) {
                    return id.toString();
                }
                return null;
            }

            @Override
            public T getRowData(String rowKey) {
                if (rowKey != null && getWrappedData() != null) {
                    T objetoSeleccionado = getWrappedData().stream()
                            .filter(r -> rowKey.equals(getId(r).toString()))
                            .findFirst()
                            .orElse(null);

                    if (objetoSeleccionado != null) {
                        registro = objetoSeleccionado;
                        estado = ESTADO_CRUD.MODIFICAR;
                    }

                    return objetoSeleccionado;
                }
                return null;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    int contando = contar();
                    return contando;
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo contar los registros.");
                    getFacesContext().addMessage(null, message);
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
                try {
                    List<T> datos = cargarDatos(first,pageSize);
                    return datos;
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los datos.");
                    getFacesContext().addMessage(null, message);
                    e.printStackTrace();
                }
                return List.of();
            }
        };
        modelo.setPageSize(registrosPorPagina);
    }
    public T getRegistro() {
        return registro;
    }

    public String getEstado() {
        return estado != null ? estado.name() : "NINGUNO";
    }

    public LazyDataModel<T> getModelo() {
        return modelo;
    }
    public void btnCrearHandler(ActionEvent actionEvent) {
        try {
            getDataBean().create(registro);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro creado con éxito.");
            getFacesContext().addMessage(null, message);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el registro.");
            getFacesContext().addMessage(null, message);
            e.printStackTrace();
        }
    }

    public void btnModificarHandler(ActionEvent actionEvent) {
        FacesMessage mensaje = new FacesMessage();
        try {
            T actualizado = getDataBean().update(registro);
            if (actualizado != null) {
                mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
                mensaje.setSummary("Actualizado exitosamente");
                getFacesContext().addMessage(null, mensaje);
                this.registro = null;
                this.estado = ESTADO_CRUD.NINGUNO;
            } else {
                mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
                mensaje.setSummary("No se pudo modificar el registro.");
                getFacesContext().addMessage(null, mensaje);
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al modificar el registro.");
            getFacesContext().addMessage(null, message);
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler(ActionEvent actionEvent) {
        try {
            getDataBean().delete(registro);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro eliminado con éxito.");
            getFacesContext().addMessage(null, message);
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro no");
            getFacesContext().addMessage(null, message);
            e.printStackTrace();
        }
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
    }

    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro = createNewEntity();
        this.estado = ESTADO_CRUD.CREAR;
    }

    public void cambiarSeleccion(SelectEvent<T> event) {
        if (event != null && event.getObject() != null) {
            this.registro = event.getObject();
            this.estado = ESTADO_CRUD.MODIFICAR;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se ha seleccionado ningún registro."));
        }
    }

    public void enviarMensaje(String titulo, String detalle, FacesMessage.Severity severidad) {
        FacesMessage mensaje = new FacesMessage(severidad, titulo, detalle);
        FacesContext contexto = FacesContext.getCurrentInstance();
        if (contexto != null) {
            contexto.addMessage(null, mensaje);
        }
    }


    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public int getRegistrosPorPagina() {
        return registrosPorPagina;
    }

    public void setRegistrosPorPagina(int registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    public List<T> cargarDatos(int firstResult, int maxResult) {
        try {
            return getDataBean().findRange(firstResult, maxResult);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los datos.");
            getFacesContext().addMessage(null, message);
            e.printStackTrace();
        }
        return List.of();
    }
    public int contar() {
        try {
            return getDataBean().count();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo contar los registros.");
            getFacesContext().addMessage(null, message);
            e.printStackTrace();
        }
        return 0;
    }

    public void setModelo(LazyDataModel<T> modelo) {
        this.modelo = modelo;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }
}




