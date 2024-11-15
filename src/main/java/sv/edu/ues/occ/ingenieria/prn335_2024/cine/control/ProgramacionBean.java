package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Pelicula> obtenerTodasLasPeliculas(String fecha) {
        // Consultamos la base de datos para obtener todas las películas
        String jpql = "SELECT p FROM Pelicula p";
        return em.createQuery(jpql, sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula.class).getResultList();
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
    // Método para obtener las programaciones filtradas por película y fecha
    public List<Programacion> obtenerProgramacionesPorPeliculaYFecha(String Pelicula, Date fecha) {
        String jpql="SELECT p FROM Programacion p WHERE p.idPelicula = :pelicula AND p.desde <= :fecha AND p.hasta >= :fecha";
        return em.createQuery(jpql,Programacion.class)
                .setParameter("pelicula",Pelicula)
                .setParameter("fecha",fecha)
                .getResultList();
    }

    public List<Programacion> buscarProgramaciones(String query, OffsetDateTime fecha) {
        String jpql = "SELECT p FROM Programacion p " +
                "WHERE p.idPelicula.nombre LIKE :query";

        if (fecha != null) {
            jpql += " AND p.desde >= :fechaInicio AND p.desde < :fechaFin";
        }

        // Imprime la consulta generada para depuración
        System.out.println("Consulta JPQL: " + jpql);

        // Realizamos la consulta
        TypedQuery<Programacion> queryResult = em.createQuery(jpql, Programacion.class)
                .setParameter("query", "%" + query + "%");

        if (fecha != null) {
            queryResult.setParameter("fechaInicio", fecha.toLocalDate().atStartOfDay(fecha.getOffset()))
                    .setParameter("fechaFin", fecha.toLocalDate().plusDays(1).atStartOfDay(fecha.getOffset()));
        }

        return queryResult.getResultList();
    }



}