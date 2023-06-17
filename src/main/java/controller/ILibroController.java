package controller;

import beans.Libro;

public interface ILibroController {
	
	
	public String listar(boolean ordenar, String orden);
	
	public String alquilar(int id, String username);
	
	public String modificar(int id);
	
	public String devolver(int id, String username);
	
	public String sumarCantidad(int id);
	
	public String insertar(String titulo, String genero, String autor, int copias, boolean novedad);
	
	public String editar(Libro libro);

}
