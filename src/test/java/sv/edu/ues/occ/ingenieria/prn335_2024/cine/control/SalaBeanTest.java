package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SalaBeanTest {
    @Test
    public void findByIdRange() {
        System.out.println("SalaBeanTest.findbyId");
        List<Sala> buscados = Arrays.asList(new Sala[]{new Sala(1), new Sala(2), new Sala(3), new Sala(4), new Sala(5)});
       Query mockQuery = Mockito.mock(Query.class);
       Mockito.when(mockQuery.getResultList()).thenReturn(buscados);

       EntityManager mockEM = Mockito.mock(EntityManager.class);
       Mockito.when(mockEM.createNamedQuery("Sala.findByIdTipoSala")).thenReturn(mockQuery);

        SalaBean cut = new SalaBean();
        cut.em=mockEM;

        List<TipoSala> encontrados = cut.findByIdTipoSala(1, 1, 1);

        assertEquals(buscados.size(),encontrados.size());

        //fail("Prueba superada con exito");
    }
}
