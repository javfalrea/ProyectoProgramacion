package com.daw.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.datamodel.entities.Pelicula;
import com.daw.datamodel.entities.Valoracion;

@Service
public class Servicio {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Pelicula> buscarPeliculas(String nombreBq, String actorBq, String directorBq, String paisBq) throws SQLException {
		//Hay que implementar.
		return null;
	}
	
	public Valoracion crearModificarValoracion(Float nota, String critica, Boolean recomendada, Long idPelicula) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
		
		int contador = 0;
		String yaValorada = "SELECT COUNT(*) INTO " + contador + " FROM pelicula WHERE id = " + idPelicula;
		Statement stmt = conn.createStatement();
		stmt.execute(yaValorada);
		
		if(contador == 1) {
			Long idValoracion = null;
			String guardarIdValoracion = "SELECT id_valoracion INTO " + idValoracion + " FROM pelicula WHERE id = " + idPelicula;
			Statement stmt2 = conn.createStatement();
			stmt2.execute(guardarIdValoracion);
			String sql = "UPDATE valoracion SET nota = ?, critica = ?, recomendada = ? WHERE id_pelicula = ?";
			// Tendré que cambiar la base de datos para que valoración también tenga la id de película.
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, nota);
			ps.setString(2,critica);
			ps.setBoolean(3, recomendada);
			ps.setLong(4, idPelicula);	
			ps.executeUpdate();
			
			int respuesta = ps.executeUpdate();
			if(respuesta==1) {
				System.out.println("Modificación correcta.");
			} else {
				throw new SQLException("No se ha podido modificar la valoración.");
			}
			ps.close();
			conn.close();
		} else {
			String sql = "INSERT INTO valoracion (nota, critica, recomendada, id_pelicula) VALUES(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, nota);
			ps.setString(2, critica);
			ps.setBoolean(3, recomendada);
			ps.setLong(4, idPelicula);
			
			int respuesta = ps.executeUpdate();
			if(respuesta==1) {
				System.out.println("Insercion correcta.");
			} else {
				throw new SQLException("No se ha podido crear la valoración.");
			}
			ps.close();
			conn.close();
			return new Valoracion(null, nota, recomendada);
		}
	 // Este método está inacabado.
	}
	
	public Pelicula crearPelicula(String titulo, Integer anioEstreno, String pais, Integer duracion, String sinopsis) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
		
		String sql = "INSERT INTO pelicula (titulo, anio_estreno, pais, duracion, sinopsis) VALUES(?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, titulo);
		ps.setInt(2, anioEstreno);
		ps.setString(3, pais);
		ps.setInt(4, duracion);
		ps.setString(5, sinopsis);
	
		int respuesta = ps.executeUpdate();
		if(respuesta==1) {
			System.out.println("Insercion correcta.");
		} else {
			throw new SQLException("No se ha podido insertar la película.");
		}
		ps.close();
		conn.close();
		return new Pelicula(null, titulo, anioEstreno, pais, duracion, sinopsis);
	}


//	public List<Autor> buscarAutores(String nombreBusqueda) throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM autor WHERE nombre LIKE '%" + nombreBusqueda + "%'");
//		List<Autor> autores = new ArrayList<Autor>();
//		while (rs.next()) {
//			Long id = rs.getLong("id");
//			String nombre = rs.getString("nombre");
//			String direccion = rs.getString("direccion");
//			String dni = rs.getString("dni");
//			Date fecha = rs.getDate("fecha_nacimiento");
//			Autor autor = new Autor(id, nombre, direccion, dni, fecha);
//			autores.add(autor);
//		}
//		rs.close();
//		stmt.close();
//		conn.close();
//		return autores;
//	}
//	
//	public List<Libro> buscarLibros(String titulo, String descripcion, Boolean favorite) throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM libro l JOIN info_adicional ia ON l.id = ia.libro WHERE l.titulo LIKE '%" + titulo + "%' AND l.descripcion LIKE '%" + descripcion + "%' AND l.favorite = " + favorite );
//		List<Libro> libros = new ArrayList<Libro>();
//		while (rs.next()) {
//			Long id = rs.getLong("id");
//			String tit = rs.getString("titulo");
//			String desc = rs.getString("descripcion");
//			Boolean fav = rs.getBoolean("favorite");
//			String isbn = rs.getString("isbn");
//			String idioma = rs.getString("idioma");
//			Date fechaPublicacion = rs.getDate("fecha_publicacion");
//			Libro libro = new Libro(id, desc, tit, fav, fechaPublicacion, isbn, idioma);
//			libros.add(libro);
//		}
//		rs.close();
//		stmt.close();
//		conn.close();
//		return libros;
//	}
//	
//	public Autor crearAutor(String nombre, String dni, String direccion, Date fechaNacimiento) throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		
//		String sql = "INSERT INTO autor (nombre, dni, direccion, fecha_nacimiento) VALUES(?,?,?,?)";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		ps.setString(1, nombre);
//		ps.setString(2, dni);
//		ps.setString(3, direccion);
//		ps.setDate(4, fechaNacimiento);
//
//		int respuesta = ps.executeUpdate();
//		if(respuesta==1) {
//			System.out.println("Insercion correcta.");
//		} else {
//			throw new SQLException("No se ha podido insertar el nuevo autor.");
//		}
//		ps.close();
//		conn.close();
//		return new Autor(null, nombre, dni, direccion, fechaNacimiento);
//	}
//	
//	public Libro crearLibro(String titulo,
//			String descripcion,			
//			Boolean favorite,
//			Date fechaPublicacion,
//			String isbn,
//			String idioma,
//			Long idCategoria,
//			Long idEditorial) throws SQLException {
//
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		conn.setAutoCommit(false);
//		
//		try {
//			String sql = "INSERT INTO libro (titulo, descripcion, favorite, categoria_id, editorial) VALUES(?,?,?,?,?)";
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, titulo);
//			ps.setString(2, descripcion);
//			ps.setBoolean(3, favorite);
//			ps.setLong(4, idCategoria);
//			ps.setLong(5, idEditorial);
//	
//			int respuesta = ps.executeUpdate();
//			if(respuesta==1) {
//				System.out.println("Insercion correcta.");
//			} else {
//				throw new SQLException("No se ha podido insertar el nuevo autor.");
//			}
//			
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM libro WHERE titulo = '"+titulo+"' AND descripcion='"+descripcion+"' AND favorite = " + favorite);
//			Long id_libro = null;
//			while (rs.next()) {
//				id_libro = rs.getLong("id");
//				break;
//			}
//			rs.close();
//			stmt.close();
//			
//			sql = "INSERT INTO info_adicional (fecha_publicacion, idioma, isbn, libro) VALUES(?,?,?,?)";
//			ps = conn.prepareStatement(sql);
//			ps.setDate(1, fechaPublicacion);
//			ps.setString(2, idioma);
//			ps.setString(3, isbn);
//			ps.setLong(4, id_libro);
//	
//			respuesta = ps.executeUpdate();
//			if(respuesta==1) {
//				System.out.println("Insercion correcta.");
//			} else {
//				throw new SQLException("No se ha podido insertar la nueva información adicional.");
//			}
//			
//			conn.commit();
//			
//			ps.close();
//			conn.close();
//			
//			return new Libro(id_libro, descripcion, titulo, favorite, fechaPublicacion, isbn, idioma);
//			
//		} catch(SQLException ex) {
//			conn.rollback();
//			conn.close();
//			throw ex;
//		}
//		
//	}	
//	
//	public Autor modificarAutor(Long idAutor, String nombre, String dni, String direccion, Date fechaNacimiento) throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		
//		String sql = "UPDATE autor SET nombre=?, dni=?, direccion=?, fecha_nacimiento=? WHERE id=?";
//		PreparedStatement ps = conn.prepareStatement(sql);
//		ps.setString(1, nombre);
//		ps.setString(2, dni);
//		ps.setString(3, direccion);
//		ps.setDate(4, fechaNacimiento);
//		ps.setLong(5, idAutor);
//
//		int respuesta = ps.executeUpdate();
//		if(respuesta==1) {
//			System.out.println("Actualización correcta.");
//		} else {
//			throw new SQLException("No se ha podido actualizar el autor.");
//		}
//		ps.close();
//		conn.close();
//		return new Autor(idAutor, nombre, dni, direccion, fechaNacimiento);
//	}
//	
//	public void eliminarAutor(Long idAutor) throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		Statement stmt = conn.createStatement();
//		
//		String sql = "SELECT libro_id FROM autor_libro WHERE autor_id = " + idAutor;
//		ResultSet rs = stmt.executeQuery(sql);
//		Integer contApareceAutor = 0;
//		while(rs.next()) {
//			contApareceAutor ++;		
//			if(contApareceAutor > 1) {
//				Long libroId = rs.getLong("libro_id");
//				String sql2 = "SELECT autor_id FROM autor_libro WHERE libro_id = " + libroId;
//				ResultSet rs2 = stmt.executeQuery(sql2);
//				Integer numAutores = 0;
//				String borrarRelacion = "DELETE FROM autor_libro WHERE autor_id = " + idAutor;
//				String borrarAutor = "DELETE FROM autor WHERE id = " + idAutor;
//				String borrarLibro = "DELETE FROM libro WHERE id = " + libroId;
//				while(rs2.next()) {
//					numAutores++;
//						if(numAutores > 1) {
//							stmt.executeUpdate(borrarRelacion);
//							stmt.executeUpdate(borrarAutor);
//						}
//				}
//				if(numAutores == 1) {
//					stmt.executeUpdate(borrarRelacion);
//					stmt.executeUpdate(borrarAutor);
//					stmt.executeUpdate(borrarLibro);
//				}
//			}
//			
//		
//		}
//		if(contApareceAutor == 0) {
//			String sql3 = "DELETE FROM autor WHERE id = " + idAutor;
//			stmt.executeUpdate(sql3);
//		} else if(contApareceAutor == 1) {
//			Long libroId = rs.getLong("libro_id");
//			String sql2 = "SELECT autor_id FROM autor_libro WHERE libro_id = " + libroId;
//			ResultSet rs2 = stmt.executeQuery(sql2);
//			Integer numAutores = 0;
//			String borrarRelacion = "DELETE FROM autor_libro WHERE autor_id = " + idAutor;
//			String borrarAutor = "DELETE FROM autor WHERE id = " + idAutor;
//			String borrarLibro = "DELETE FROM libro WHERE id = " + libroId;
//			while(rs2.next()) {
//				numAutores++;
//					if(numAutores > 1) {
//						stmt.executeUpdate(borrarRelacion);
//						stmt.executeUpdate(borrarAutor);
//					}
//			}
//			if(numAutores == 1) {
//				stmt.executeUpdate(borrarRelacion);
//				stmt.executeUpdate(borrarAutor);
//				stmt.executeUpdate(borrarLibro);
//			}
//		}
//
//		stmt.close();
//		conn.close();
//	}
//
//	public List<Editorial> buscarEditoriales() throws SQLException {
//		
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		conn.setAutoCommit(false);
//		
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM editorial");
//		List<Editorial> editoriales = new ArrayList<Editorial>();
//		while (rs.next()) {
//			Long id = rs.getLong("id");
//			String nombre = rs.getString("nombre");
//			Editorial editorial = new Editorial(id, nombre);
//			editoriales.add(editorial);
//		}
//		rs.close();
//		stmt.close();
//		conn.close();
//		
//		return editoriales;
//	}
//
//	public List<Categoria> buscarCategorias() throws SQLException {
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_datos_biblioteca", "usuario","usuario");
//		conn.setAutoCommit(false);
//		
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");
//		List<Categoria> categorias = new ArrayList<Categoria>();
//		while (rs.next()) {
//			Long id = rs.getLong("id");
//			String nombre = rs.getString("nombre");
//			Categoria categoria = new Categoria(id, nombre);
//			categorias.add(categoria);
//		}
//		rs.close();
//		stmt.close();
//		conn.close();
//		
//		return categorias;
//	}
	

}
