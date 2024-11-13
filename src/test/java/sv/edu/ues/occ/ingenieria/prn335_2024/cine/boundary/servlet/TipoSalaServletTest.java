package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TipoSalaServletTest {

    //codigo de cbertura de request
    @Test
    void doPost() throws IOException {
        System.out.println("TipoSalaServelt.doPost");
        TipoSalaServlet cut = new TipoSalaServlet();
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        Mockito.when(mockReq.getParameter("nombre")).thenReturn("wolf");
        TipoSalaBean mockTSB = Mockito.mock(TipoSalaBean.class);

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        Mockito.when(mockResp.getWriter()).thenReturn(printWriter);
        cut.tsBean = mockTSB;


        try {
            cut.doPost(mockReq, mockResp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        //Fin de codigo de cobertura de request
    }

    @Test
    void doPostWithException() throws IOException {
        System.out.println("TipoSalaServlet.doPostWithException");
        TipoSalaServlet cut = new TipoSalaServlet();
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        Mockito.when(mockReq.getParameter("nombre")).thenReturn("wolf");
        TipoSalaBean mockTSB = Mockito.mock(TipoSalaBean.class);

        // Simula una excepción en el método create
        Mockito.doThrow(new RuntimeException("Test exception")).when(mockTSB).create(Mockito.any());

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        Mockito.when(mockResp.getWriter()).thenReturn(printWriter);
        cut.tsBean = mockTSB;

        try {
            cut.doPost(mockReq, mockResp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        // Verifica que se haya establecido el estado de respuesta a 500
        Mockito.verify(mockResp).setStatus(500);
    }
    @Test
    void doGet() throws IOException {
        System.out.println("TipoSalaServlet.doGet");
        TipoSalaServlet cut = new TipoSalaServlet();
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        List<TipoSala> mockList;
        mockList = new ArrayList<>();
        TipoSala nuevo = new TipoSala();
        nuevo.setIdTipoSala(1);
        nuevo.setNombre("Sala 1");
        nuevo.setActivo(true);
        nuevo.setComentarios("Comentario 1");
        nuevo.setExpresionRegular("Expresion 1");
        mockList.add(nuevo);

        TipoSalaBean mockTSB = Mockito.mock(TipoSalaBean.class);
        Mockito.when(mockTSB.findRange(0, 100000)).thenReturn(mockList);
        cut.tsBean = mockTSB;

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        Mockito.when(mockResp.getWriter()).thenReturn(printWriter);

        try {
            cut.doGet(mockReq, mockResp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        // Imprime el contenido de la respuesta
        String responseContent = sw.toString();
        System.out.println(responseContent);

        // Verifica el contenido esperado
        assertTrue(responseContent.contains("<td>1</td>"));
        assertTrue(responseContent.contains("<td>Sala 1</td>"));
        assertTrue(responseContent.contains("<td>Activo</td>"));
        assertTrue(responseContent.contains("<td>Comentario 1</td>"));
        assertTrue(responseContent.contains("<td>Expresion 1</td>"));
    }
}