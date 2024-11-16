package sv.edu.ues.occ.ingenieria.prn335_2024.cine;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "¡Bienvenido al Sistema de Gestión del Cine!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Salida de HTML con estilo cinematográfico
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Página de Bienvenida - Cine</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css'>"); // Para íconos
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #2f4f4f; color: white; text-align: center; padding: 20px; }");
        out.println("h1 { color: #f39c12; font-size: 3em; margin-bottom: 20px; }");
        out.println("p { font-size: 1.2em; margin-bottom: 30px; }");
        out.println(".btn-iniciar { background-color: #e74c3c; color: white; border: none; padding: 15px 30px; font-size: 1.2em; cursor: pointer; border-radius: 5px; }");
        out.println(".btn-iniciar:hover { background-color: #c0392b; }");
        out.println("form { margin-top: 20px; }");
        out.println(".icon { font-size: 50px; color: #f39c12; margin: 10px; }");
        out.println("</style></head><body>");

        out.println("<h1>" + message + "</h1>");
        out.println("<p>¡La experiencia del cine, ahora a su alcance!</p>");
        out.println("<i class='fas fa-film icon'></i>");
        out.println("<i class='fas fa-ticket-alt icon'></i>");
        out.println("<i class='fas fa-popcorn icon'></i>");

        out.println("<form action='TipoSala.jsf' method='get'>");
        out.println("<button class='btn-iniciar'>Iniciar</button>");
        out.println("</form>");

        out.println("</body></html>");
    }

    public void destroy() {
    }
}
