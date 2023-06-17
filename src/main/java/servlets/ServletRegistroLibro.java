package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LibroController;

/**
 * Servlet implementation class ServletRegistroLibro
 */
@WebServlet("/ServletRegistroLibro")
public class ServletRegistroLibro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegistroLibro() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String titulo = request.getParameter("titulo");
        String genero = request.getParameter("genero");
        String autor = request.getParameter("autor");
        int copias = Integer.parseInt(request.getParameter("copias"));
        boolean novedad = Boolean.parseBoolean(request.getParameter("novedad"));

        // Llamar al controlador para insertar el libro en la base de datos
        LibroController libroController = new LibroController();
        String resultado = libroController.insertar(titulo, genero, autor, copias, novedad);

        // Enviar la respuesta al cliente
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(resultado);
        out.flush();
        out.close();
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}


