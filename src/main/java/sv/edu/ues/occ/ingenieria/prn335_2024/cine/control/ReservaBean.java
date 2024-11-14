package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
public class ReservaBean extends AbstractDataPersistence<Reserva> implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public ReservaBean() {
        super(Reserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Reserva findReservaById(Long id){
        try {
            return getEntityManager().find(Reserva.class,id);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
            return null;
        }
    }

    public List<Programacion> findProgramacionPorDia(TipoReserva tipoReserva, String dia){
        String jpql="SELECT p FROM Programacion f WHERE f.tipoReserva = :tipoReserva AND f.dia = :dia";
        return getEntityManager().createQuery(jpql,Programacion.class).setParameter("tipoReserva",tipoReserva)
                .setParameter("dia",dia)
                .getResultList();
    }
}
