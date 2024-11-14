package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.List;

@LocalBean
@Stateless
public class TipoReservaBean extends AbstractDataPersistence<TipoReserva> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public TipoReservaBean() {
        super(TipoReserva.class);
    }


    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // En el bean o servicio TipoReservaBean
    public List<TipoReserva> obtenerListaTiposReserva() {
        // Este es un ejemplo. Aquí puedes hacer la consulta a la base de datos
        // Usando un EntityManager o algún otro mecanismo para obtener los tipos de reserva.
        return em.createQuery("SELECT t FROM TipoReserva t", TipoReserva.class)
                .getResultList();
    }
}

