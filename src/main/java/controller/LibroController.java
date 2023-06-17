package controller;


import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import beans.Libro;

import connection.DBConnection;

public class LibroController implements ILibroController{

	@Override
	public String listar(boolean ordenar, String orden) {
		
		Gson gson = new Gson();
		
		DBConnection con = new DBConnection();
		String sql = "Select * from libros";
		
		if (ordenar == true) {
			sql += " order by genero " + orden;  
		}
		
		List<String> libros = new ArrayList<String>();
		
		try {
			
			Statement st = con.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				int id = rs.getInt("id");
				String titulo = rs.getString("titulo");
				String genero = rs.getString("genero");
				String autor = rs.getString("autor");
				int copias = rs.getInt("copias");
				boolean novedad = rs.getBoolean("novedad");
				
				Libro libro = new Libro(id,titulo,genero,autor,copias,novedad);
				
				libros.add(gson.toJson(libro));
				
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			con.desconectar();
		}
		
		return gson.toJson(libros);
		
	}

	@Override
	public String alquilar(int id, String username) {
		
		Timestamp fecha = new Timestamp(new Date().getTime());
		
		DBConnection con = new DBConnection();
		String sql = "Insert into alquiler values ('" + id + "', '" + username + "', '" + fecha + "')";
		
		try {
			Statement st = con.getConnection().createStatement();
			st.executeQuery(sql);
			
			String modificar = modificar(id);
			
			if (modificar.equals("true")) {
				return "true";
			}
			
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			con.desconectar();
		}
		
		return "false";
	}

	@Override
	public String modificar(int id) {
		
		DBConnection con = new DBConnection();
		String sql = "Update libros set copias = (copias - 1) where id = " + id;
		
		try {
			Statement st = con.getConnection().createStatement();
			st.executeQuery(sql);
			
			return "true";
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			con.desconectar();
		}
				
		return "false";
		
	}

	@Override
	public String devolver(int id, String username) {
		
		
		DBConnection con = new DBConnection();
		String sql = "Delete from alquiler where id= " + id + " and username = '" + username + "' limit 1";
		
		try {
			Statement st = con.getConnection().createStatement();
			st.executeQuery(sql);
			
			this.sumarCantidad(id);
			
			return "true";
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			con.desconectar();
		}
		
		return "false";
	}

	@Override
	public String sumarCantidad(int id) {
		
		DBConnection con = new DBConnection();
		
		String sql = "Update libros set copias = (Select copias from libros where id = " + id + ") + 1 where id = " + id;
		
		try {
			Statement st = con.getConnection().createStatement();
			st.executeQuery(sql);
			
			return "true";
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
		finally {
			con.desconectar();
		}
		
		return "false";
		
	}
	
	@Override
	public String insertar(String titulo, String genero, String autor, int copias, boolean novedad) {
	    DBConnection con = new DBConnection();
	    
	    try {
	        // Obtener el último ID registrado
	        Statement stId = con.getConnection().createStatement();
	        String sqlId = "SELECT MAX(id) AS max_id FROM libros";
	        ResultSet rsId = stId.executeQuery(sqlId);
	        
	        int nuevoId = 1; // Valor predeterminado si no hay registros previos
	        
	        if (rsId.next()) {
	            nuevoId = rsId.getInt("max_id") + 1;
	        }
	        
	        // Insertar el nuevo libro en la base de datos
	        Statement stInsert = con.getConnection().createStatement();
	        String sqlInsert = "INSERT INTO libros (id, titulo, genero, autor, copias, novedad) VALUES " +
	                "(" + nuevoId + ", '" + titulo + "', '" + genero + "', '" + autor + "', " +
	                copias + ", " + (novedad ? 1 : 0) + ")";
	        
	        stInsert.executeUpdate(sqlInsert);
	        
	        return "true";
	    } catch (Exception ex) {
	        System.out.println(ex.toString());
	    } finally {
	        con.desconectar();
	    }
	    
	    return "false";
	}
	
	@Override
	public String editar(Libro libro) {
	    DBConnection con = new DBConnection();
	    
	    try {
	        Statement st = con.getConnection().createStatement();
	        
	        String sql = "UPDATE libros SET titulo = '" + libro.getTitulo() + "', genero = '" + libro.getGenero() + "', autor = '" + libro.getAutor() + "', copias = " + libro.getCopias() + ", novedad = " + libro.isNovedad() + " WHERE id = " + libro.getId();
	        
	        int rowCount = st.executeUpdate(sql);
	        
	        if (rowCount > 0) {
	            return "true";
	        }
	    }
	    catch (Exception ex) {
	        System.out.println(ex.toString());
	    }
	    finally {
	        con.desconectar();
	    }
	    
	    return "false";
	}

}
