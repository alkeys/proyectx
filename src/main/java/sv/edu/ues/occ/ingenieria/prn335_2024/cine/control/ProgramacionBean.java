package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@LocalBean
@Stateless
public class ProgramacionBean extends AbstractDataPersistence<Programacion> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public ProgramacionBean() {
        super(Programacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> buscarProgramaciones(String query) {
        String jpql = "SELECT p FROM Programacion p " +
                "WHERE LOWER(p.idPelicula.nombre) LIKE :query " +
                "OR LOWER(p.idSala.nombre) LIKE :query " +
                "OR LOWER(p.idSucursal.nombre) LIKE :query";

        // Ejecutar la consulta con el término de búsqueda
        return em.createQuery(jpql, Programacion.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();
    }

    public List<Programacion> obtenerProgramaciones(TipoReserva tipoReserva, String diaSeleccionado) {
        // Aquí interactúas con el EntityManager o JPA para obtener los datos
        // Este es solo un ejemplo simple, adapta según tus necesidades

        // Crear la consulta JPQL
        String jpql = "SELECT p FROM Programacion p WHERE p.idTipoReserva = :tipoReserva AND p.fecha = :diaSeleccionado";

        // Obtener la lista de programaciones
        List<Programacion> programaciones = em.createQuery(jpql, Programacion.class)
                .setParameter("tipoReserva", tipoReserva)
                .setParameter("diaSeleccionado", diaSeleccionado)
                .getResultList();

        return programaciones;
    }

    public List<Pelicula> obtenerTodasLasPeliculas(String fecha) {
        // Consultamos la base de datos para obtener todas las películas
        String jpql = "SELECT p FROM Pelicula p";
        return em.createQuery(jpql, Pelicula.class).getResultList();
    }

    public List<Programacion> obtenerProgramaciones(String fecha) {
        try {
            // Asegúrate de usar el formato adecuado de fecha o adaptarlo según tu campo en la entidad Programacion
            String query = "SELECT p FROM Programacion p WHERE FUNCTION('DATE', p.desde) = :fecha";

            return em.createQuery(query, Programacion.class)
                    .setParameter("fecha", fecha)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void remove(Programacion programacion) {
        if (programacion != null && !em.contains(programacion)) {
            programacion = em.merge(programacion);
        }
        em.remove(programacion);
    }
}