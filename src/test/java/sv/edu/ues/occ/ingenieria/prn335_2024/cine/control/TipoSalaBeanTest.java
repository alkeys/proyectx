package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TipoSalaBeanTest {

    protected List<TipoSala> findResult;

    public TipoSalaBeanTest() {
        findResult = Arrays.asList(new TipoSala[]{new TipoSala(1), new TipoSala(2), new TipoSala(3)});
    }

    @Test
    void create() {
        System.out.println("TipoSalaBeanTest create");
        EntityManager mockEM = Mockito.mock(EntityManager.class);
        TipoSala nuevo = new TipoSala();
        TipoSalaBean cut = new TipoSalaBean();
        assertThrows(IllegalArgumentException.class, () -> {
            cut.create(null);
        });
        assertThrows(IllegalStateException.class, () -> {
            cut.create(nuevo);
        });
        cut.em = mockEM;
        cut.create(nuevo);
    }

    @Test
    void findById() {
        System.out.println("TipoSalaBeanTest.findById");
        final Integer idEsperado = 1;
        TipoSala esperado = new TipoSala(idEsperado);
        TipoSalaBean cut = new TipoSalaBean();
        assertThrows(IllegalStateException.class, () -> {
            cut.findById(idEsperado);
        });
        EntityManager mock = Mockito.mock(EntityManager.class);
        Mockito.when(mock.find(TipoSala.class, idEsperado)).thenReturn(esperado);
        cut.em = mock;
        TipoSala resultado = cut.findById(idEsperado);
        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        assertThrows(IllegalArgumentException.class, () -> {
            cut.findById(null);
        });
    }

    @Test
    void findRange() {
        System.out.println("TipoSalaBeanTest.findRange");
        int first = 0;
        int max = 1000;
        TipoSalaBean cut = new TipoSalaBean();
        assertThrows(IllegalArgumentException.class, () -> {
            cut.findRange(-1, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.findRange(10, -1);
        });
        assertThrows(IllegalStateException.class, () -> {
            cut.findRange(first, max);
        });
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<TipoSala> cqMock = Mockito.mock(CriteriaQuery.class);
        Root rootMock = Mockito.mock(Root.class);
        Mockito.when(cqMock.from(TipoSala.class)).thenReturn(rootMock);
        EntityManager emMock = Mockito.mock(EntityManager.class);
        TypedQuery tqMock = Mockito.mock(TypedQuery.class);
        Mockito.when(tqMock.getResultList()).thenReturn(findResult);
        Mockito.when(emMock.createQuery(cqMock)).thenReturn(tqMock);
        Mockito.when(cbMock.createQuery(TipoSala.class)).thenReturn(cqMock);
        Mockito.when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        cut.em = emMock;
        List<TipoSala> encontrados = cut.findRange(first, max);
        assertNotNull(encontrados);
        assertEquals(findResult.size(), encontrados.size());
    }

    @Test
    void delete() {
        System.out.println("TipoSalaBeanTest.delete");
        TipoSalaBean cut = new TipoSalaBean();
        TipoSala eliminado = new TipoSala(1);
        assertThrows(IllegalArgumentException.class, () -> {
            cut.delete(null);
        });
        EntityManager emMock = Mockito.mock(EntityManager.class);
        assertThrows(IllegalStateException.class, () -> {
            cut.delete(eliminado);
        });
        Mockito.when(emMock.contains(eliminado)).thenReturn(true);
        cut.em = emMock;
        cut.delete(eliminado);
        Mockito.verify(emMock, Mockito.times(1)).remove(eliminado);
        Mockito.when(emMock.contains(eliminado)).thenReturn(false);
        Mockito.when(emMock.merge(eliminado)).thenReturn(eliminado);
        cut.em = emMock;
        cut.delete(eliminado);
        Mockito.verify(emMock, Mockito.times(2)).remove(eliminado);
    }

    @Test
    void update() {
        System.out.println("TipoSalaBeanTest.update");
        TipoSalaBean cut = new TipoSalaBean();
        TipoSala modificado = new TipoSala(1);
        assertThrows(IllegalArgumentException.class, () -> {
            cut.update(null);
        });
        assertThrows(IllegalStateException.class, () -> {
            cut.update(modificado);
        });
        EntityManager emMock = Mockito.mock(EntityManager.class);
        Mockito.when(emMock.merge(modificado)).thenReturn(modificado);
        cut.em = emMock;
        TipoSala resultado = cut.update(modificado);
        assertNotNull(resultado);
        assertEquals(modificado, resultado);
    }

    @Test
    void count() {
        System.out.println("TipoSalaBeanTest.count");
        TipoSalaBean cut = new TipoSalaBean();
        assertThrows(IllegalStateException.class, () -> {
            cut.count();
        });
        EntityManager emMock = Mockito.mock(EntityManager.class);
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Long> cqMock = Mockito.mock(CriteriaQuery.class);
        Root rootMock = Mockito.mock(Root.class);
        Expression exMock = Mockito.mock(Expression.class);
        Mockito.when(cqMock.from(TipoSala.class)).thenReturn(rootMock);
        Mockito.when(cbMock.count(rootMock)).thenReturn(exMock);
        Mockito.when(cbMock.createQuery(Long.class)).thenReturn(cqMock);
        TypedQuery tqMock = Mockito.mock(TypedQuery.class);
        Mockito.when(tqMock.getSingleResult()).thenReturn(2L);
        Mockito.when(emMock.createQuery(cqMock)).thenReturn(tqMock);
        Mockito.when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        cut.em = emMock;
        cut.count();
    }
    @Test
    void carnet() {
        System.out.println("==============================");
        System.out.println("Examen para el alumno");
        TipoSalaBean cut = new TipoSalaBean();
        System.out.println(cut.imprimirCarnet());
    }
}
