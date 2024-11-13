package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle;
import java.io.Serializable;

@LocalBean
@Stateless
public class ReservaDetalleBean extends AbstractDataPersistence<ReservaDetalle> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public ReservaDetalleBean() {
        super(ReservaDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}