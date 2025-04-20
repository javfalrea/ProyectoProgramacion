package com.daw.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.datamodel.entities.Genero;
import com.daw.datamodel.entities.Pais;
import com.daw.datamodel.entities.Participante;
import com.daw.datamodel.entities.Pelicula;
import com.daw.datamodel.entities.PeliculaGenero;
import com.daw.datamodel.entities.PeliculaParticipante;
import com.daw.datamodel.entities.Valoracion;
import com.daw.datamodel.entities.Vista;

@Service
public class Servicio {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Pelicula crearPelicula(String titulo, Integer anioEstreno, Long idPais, Integer duracion, String sinopsis) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertPelicula = "INSERT INTO pelicula (titulo, anio_estreno, id_pais, duracion, sinopsis) VALUES(?,?,?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertPelicula, Statement.RETURN_GENERATED_KEYS); // Asegurarse de que obtenemos las claves generadas
	    ps.setString(1, titulo);
	    ps.setInt(2, anioEstreno);
	    ps.setLong(3, idPais);
	    ps.setInt(4, duracion);
	    ps.setString(5, sinopsis);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar la película.");
	    }

	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    Long idPelicula = null;
	    if (generatedKeys.next()) {
	        idPelicula = generatedKeys.getLong(1); // Obtener el ID generado
	    } else {
	        throw new SQLException("No se pudo obtener el ID de la película insertada.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPais);
	    ps2.setLong(1, idPais);
	    ResultSet rs = ps2.executeQuery();
	    String continente = null;
	    String nombre = null;
	    if (rs.next()) {
	        continente = rs.getString("continente");
	        nombre = rs.getString("nombre");
	    } else {
	        throw new SQLException("El país seleccionado no existe en el sistema.");
	    }

	    Pais pais = new Pais(idPais, continente, nombre);

	    rs.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Pelicula(idPelicula, titulo, anioEstreno, pais, duracion, sinopsis);
	}
	
	public Pelicula modificarPelicula(Long idPelicula, String titulo, Integer anioEstreno, Long idPais, Integer duracion, String sinopsis) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String modPelicula = "UPDATE pelicula SET titulo = ?, anio_estreno = ?, id_pais = ?, duracion = ?, sinopsis = ? WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(modPelicula); 
	    ps.setString(1, titulo);
	    ps.setInt(2, anioEstreno);
	    ps.setLong(3, idPais);
	    ps.setInt(4, duracion);
	    ps.setString(5, sinopsis);
	    ps.setLong(6, idPelicula);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido modificar la película.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPais);
	    ps2.setLong(1, idPais);
	    ResultSet rs = ps2.executeQuery();
	    String continente = null;
	    String nombre = null;
	    if (rs.next()) {
	        continente = rs.getString("continente");
	        nombre = rs.getString("nombre");
	    } else {
	        throw new SQLException("El país seleccionado no existe en el sistema.");
	    }

	    Pais pais = new Pais(idPais, continente, nombre);

	    rs.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Pelicula(idPelicula, titulo, anioEstreno, pais, duracion, sinopsis);
	}
	
	public void eliminarPelicula(Long id) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String deletePelicula = "DELETE FROM pelicula WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(deletePelicula);
	    ps.setLong(1, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        ps.close();
	        conn.close();
	        throw new SQLException("No se ha podido eliminar la película");
	    }

	    ps.close();
	    conn.close();
	}


	public Participante crearParticipante(String nombre, Long idPais, Date fechaNacimiento) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertParticipante = "INSERT INTO participante (nombre, id_pais, fecha_nacimiento) VALUES(?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertParticipante, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, nombre);
	    ps.setLong(2, idPais);
	    ps.setDate(3, fechaNacimiento);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el participante.");
	    }

	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    Long idParticipante = null;
	    if (generatedKeys.next()) {
	        idParticipante = generatedKeys.getLong(1);
	    } else {
	        throw new SQLException("No se pudo obtener el ID del participante insertado.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPais);
	    ps2.setLong(1, idPais);
	    ResultSet rs = ps2.executeQuery();
	    String continente = null;
	    String nombrePais = null;
	    if (rs.next()) {
	        continente = rs.getString("continente");
	        nombrePais = rs.getString("nombre");
	    } else {
	        throw new SQLException("El país seleccionado no existe en el sistema.");
	    }

	    Pais pais = new Pais(idPais, continente, nombrePais);

	    rs.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Participante(idParticipante, nombre, pais, fechaNacimiento);
	}
	
	public Participante modificarParticipante(Long id, String nombre, Long idPais, Date fechaNacimiento) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String modificarParticipante = "UPDATE participante SET nombre = ?, id_pais = ?, fecha_nacimiento = ? WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(modificarParticipante);
	    ps.setString(1, nombre);
	    ps.setLong(2, idPais);
	    ps.setDate(3, fechaNacimiento);
	    ps.setLong(4, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el participante.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPais);
	    ps2.setLong(1, idPais);
	    ResultSet rs = ps2.executeQuery();
	    String continente = null;
	    String nombrePais = null;
	    if (rs.next()) {
	        continente = rs.getString("continente");
	        nombrePais = rs.getString("nombre");
	    } else {
	        throw new SQLException("El país seleccionado no existe en el sistema.");
	    }

	    Pais pais = new Pais(idPais, continente, nombrePais);

	    rs.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Participante(id, nombre, pais, fechaNacimiento);
	}
	
	public void eliminarParticipante(Long id) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String deleteParticipante = "DELETE FROM participante WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(deleteParticipante);
	    ps.setLong(1, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        ps.close();
	        conn.close();
	        throw new SQLException("No se ha podido eliminar el participante");
	    }

	    ps.close();
	    conn.close();
	}

	public Valoracion crearValoracion(Long idPelicula, Float nota, Boolean recomendada, String critica) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertValoracion = "INSERT INTO valoracion (id_pelicula, nota, recomendada, critica) VALUES(?,?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertValoracion);
	    ps.setLong(1, idPelicula);
	    ps.setFloat(2, nota);
	    ps.setBoolean(3, recomendada);
	    ps.setString(4, critica);
	    
	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar la valoración.");
	    }

	    String selectPelicula = "SELECT titulo, anio_estreno, id_pais, duracion, sinopsis FROM pelicula WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPelicula);
	    ps2.setLong(1, idPelicula);
	    ResultSet rs = ps2.executeQuery();

	    String titulo;
	    Integer anioEstreno;
	    Long idPais;
	    Integer duracion;
	    String sinopsis;

	    if (rs.next()) {
	        titulo = rs.getString("titulo");
	        anioEstreno = rs.getInt("anio_estreno");
	        idPais = rs.getLong("id_pais");
	        duracion = rs.getInt("duracion");
	        sinopsis = rs.getString("sinopsis");
	    } else {
	        rs.close();
	        ps2.close();
	        ps.close();
	        conn.close();
	        throw new SQLException("La película seleccionada no existe.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps3 = conn.prepareStatement(selectPais);
	    ps3.setLong(1, idPais);
	    ResultSet rs2 = ps3.executeQuery();
	    rs2.next();

	    String continente = rs2.getString("continente");
	    String nombre = rs2.getString("nombre");

	    Pais pais = new Pais(idPais, continente, nombre);
	    Pelicula pelicula = new Pelicula(idPelicula, titulo, anioEstreno, pais, duracion, sinopsis);

	    rs2.close();
	    rs.close();
	    ps3.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Valoracion(pelicula, nota, recomendada, critica);
	}
	
	public Vista crearVista(Long idPelicula, Date fecha) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertVista = "INSERT INTO vista (id_pelicula, fecha) VALUES(?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertVista, Statement.RETURN_GENERATED_KEYS);
	    ps.setLong(1, idPelicula);
	    ps.setDate(2, fecha);
	    
	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar la vista.");
	    }
	    
	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    Long idVista = null;
	    if (generatedKeys.next()) {
	        idVista = generatedKeys.getLong(1);
	    } else {
	        throw new SQLException("No se pudo obtener el ID de la vista insertada.");
	    }

	    String selectPelicula = "SELECT titulo, anio_estreno, id_pais, duracion, sinopsis FROM pelicula WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPelicula);
	    ps2.setLong(1, idPelicula);
	    ResultSet rs = ps2.executeQuery();

	    String titulo;
	    Integer anioEstreno;
	    Long idPais;
	    Integer duracion;
	    String sinopsis;

	    if (rs.next()) {
	        titulo = rs.getString("titulo");
	        anioEstreno = rs.getInt("anio_estreno");
	        idPais = rs.getLong("id_pais");
	        duracion = rs.getInt("duracion");
	        sinopsis = rs.getString("sinopsis");
	    } else {
	        rs.close();
	        ps2.close();
	        ps.close();
	        conn.close();
	        throw new SQLException("La película seleccionada no existe.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps3 = conn.prepareStatement(selectPais);
	    ps3.setLong(1, idPais);
	    ResultSet rs2 = ps3.executeQuery();
	    rs2.next();

	    String continente = rs2.getString("continente");
	    String nombre = rs2.getString("nombre");

	    Pais pais = new Pais(idPais, continente, nombre);
	    Pelicula pelicula = new Pelicula(idPelicula, titulo, anioEstreno, pais, duracion, sinopsis);

	    rs2.close();
	    rs.close();
	    ps3.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Vista(idVista, pelicula, fecha);
	}
	
	public void eliminarVista(Long id) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String deleteVista = "DELETE FROM vista WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(deleteVista);
	    ps.setLong(1, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        ps.close();
	        conn.close();
	        throw new SQLException("No se ha podido eliminar la vista");
	    }

	    ps.close();
	    conn.close();
	}

	public Valoracion modificarValoracion(Long idPelicula, Float nota, Boolean recomendada, String critica) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String updateValoracion = "UPDATE valoracion SET nota = ?, recomendada = ?, critica = ? WHERE id_pelicula = ?";
	    PreparedStatement ps = conn.prepareStatement(updateValoracion);
	    ps.setFloat(1, nota);
	    ps.setBoolean(2, recomendada);
	    ps.setString(3, critica);
	    ps.setLong(4, idPelicula);
	    
	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido modificar la valoración.");
	    }

	    String selectPelicula = "SELECT titulo, anio_estreno, id_pais, duracion, sinopsis FROM pelicula WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPelicula);
	    ps2.setLong(1, idPelicula);
	    ResultSet rs = ps2.executeQuery();

	    String titulo;
	    Integer anioEstreno;
	    Long idPais;
	    Integer duracion;
	    String sinopsis;

	    if (rs.next()) {
	        titulo = rs.getString("titulo");
	        anioEstreno = rs.getInt("anio_estreno");
	        idPais = rs.getLong("id_pais");
	        duracion = rs.getInt("duracion");
	        sinopsis = rs.getString("sinopsis");
	    } else {
	        rs.close();
	        ps2.close();
	        ps.close();
	        conn.close();
	        throw new SQLException("La película seleccionada no existe.");
	    }

	    String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps3 = conn.prepareStatement(selectPais);
	    ps3.setLong(1, idPais);
	    ResultSet rs2 = ps3.executeQuery();
	    rs2.next();

	    String continente = rs2.getString("continente");
	    String nombre = rs2.getString("nombre");

	    Pais pais = new Pais(idPais, continente, nombre);
	    Pelicula pelicula = new Pelicula(idPelicula, titulo, anioEstreno, pais, duracion, sinopsis);

	    rs2.close();
	    rs.close();
	    ps3.close();
	    ps2.close();
	    ps.close();
	    conn.close();

	    return new Valoracion(pelicula, nota, recomendada, critica);
	}
	
	public PeliculaParticipante anadirParticipante(Long idPelicula, Long idParticipante, Boolean esActor, Boolean esDirector) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insert = "INSERT INTO pelicula_participante (id_pelicula, id_participante, es_actor, es_director) VALUES(?,?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(insert);
	    ps.setLong(1, idPelicula);
	    ps.setLong(2, idParticipante);
	    ps.setBoolean(3, esActor);
	    ps.setBoolean(4, esDirector);


	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el participante a la película.");
	    }

	    String selectPelicula = "SELECT titulo, anio_estreno, id_pais, duracion, sinopsis FROM pelicula WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPelicula);
	    ps2.setLong(1, idPelicula);
	    ResultSet rs = ps2.executeQuery();
	    String titulo = null;
	    Integer anioEstreno = null;
	    Long idPaisPeli = null;
	    Integer duracion = null;
	    String sinopsis = null;
	    if(rs.next()) {
	    	titulo = rs.getString("titulo");
	    	anioEstreno = rs.getInt("anio_estreno");
	    	idPaisPeli = rs.getLong("id_pais");
	    	duracion = rs.getInt("duracion");
	    	sinopsis = rs.getString("sinopsis");
	    } else {
	    	throw new SQLException("La película seleccionada no existe en el sistema");
	    }
	    
	    String selectPaisPeli = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps3 = conn.prepareStatement(selectPaisPeli);
	    ps3.setLong(1, idPaisPeli);
	    ResultSet rs2 = ps3.executeQuery();
	    String continentePeli = null;
	    String nombrePaisPeli = null;
	    if(rs2.next()) {
		    continentePeli = rs2.getString("continente");
	        nombrePaisPeli = rs2.getString("nombre");
	    }
	
	    Pais paisPeli = new Pais(idPaisPeli, continentePeli, nombrePaisPeli);
	    
	    Pelicula pelicula = new Pelicula(idPelicula, titulo, anioEstreno, paisPeli, duracion, sinopsis);
	    
	    String selectParticipante = "SELECT nombre, id_pais, fecha_nacimiento FROM participante WHERE id = ?";
	    PreparedStatement ps4 = conn.prepareStatement(selectParticipante);
	    ps4.setLong(1, idParticipante);
	    ResultSet rs3 = ps4.executeQuery();
	    String nombre = null;
	    Long idPaisParticipante = null;
	    Date fechaNacimiento = null;
	    if(rs3.next()) {
	    	nombre = rs3.getString("nombre");
	    	idPaisParticipante = rs3.getLong("id_pais");
	    	fechaNacimiento = rs3.getDate("fecha_nacimiento");
	    } else {
	    	throw new SQLException("El participante seleccionado no existe en el sistema.");
	    }
	    
	    String selectPaisParticipante = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps5 = conn.prepareStatement(selectPaisParticipante);
	    ps5.setLong(1, idPaisParticipante);
	    ResultSet rs4 = ps5.executeQuery();
	    String continenteParticipante = null;
	    String nombrePaisParticipante = null;
	    if(rs4.next()) {
		    continenteParticipante = rs4.getString("continente");
	        nombrePaisParticipante = rs4.getString("nombre");
	    }
	
	    Pais paisParticipante = new Pais(idPaisParticipante, continenteParticipante, nombrePaisParticipante);
	    
	    Participante participante = new Participante(idParticipante, nombre, paisParticipante, fechaNacimiento);
	    
	    rs4.close();
	    rs3.close();
	    rs2.close();
	    rs.close();
	    ps5.close();
	    ps4.close();
	    ps3.close();
	    ps2.close();
	    ps.close();
	    conn.close();
	    
	    return new PeliculaParticipante(pelicula, participante, esActor, esDirector);
	    
	}
	
	public PeliculaGenero anadirGenero(Long idPelicula, Long idGenero) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insert = "INSERT INTO pelicula_genero (id_pelicula, id_genero) VALUES(?,?)";
	    PreparedStatement ps = conn.prepareStatement(insert);
	    ps.setLong(1, idPelicula);
	    ps.setLong(2, idGenero);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el género a la película.");
	    }

	    String selectPelicula = "SELECT titulo, anio_estreno, id_pais, duracion, sinopsis FROM pelicula WHERE id = ?";
	    PreparedStatement ps2 = conn.prepareStatement(selectPelicula);
	    ps2.setLong(1, idPelicula);
	    ResultSet rs = ps2.executeQuery();
	    String titulo = null;
	    Integer anioEstreno = null;
	    Long idPaisPeli = null;
	    Integer duracion = null;
	    String sinopsis = null;
	    if(rs.next()) {
	    	titulo = rs.getString("titulo");
	    	anioEstreno = rs.getInt("anio_estreno");
	    	idPaisPeli = rs.getLong("id_pais");
	    	duracion = rs.getInt("duracion");
	    	sinopsis = rs.getString("sinopsis");
	    } else {
	    	throw new SQLException("La película seleccionada no existe en el sistema");
	    }
	    
	    String selectPaisPeli = "SELECT continente, nombre FROM pais WHERE id = ?";
	    PreparedStatement ps3 = conn.prepareStatement(selectPaisPeli);
	    ps3.setLong(1, idPaisPeli);
	    ResultSet rs2 = ps3.executeQuery();
	    String continentePeli = null;
	    String nombrePaisPeli = null;
	    if(rs2.next()) {
		    continentePeli = rs2.getString("continente");
	        nombrePaisPeli = rs2.getString("nombre");
	    }
	
	    Pais paisPeli = new Pais(idPaisPeli, continentePeli, nombrePaisPeli);
	    
	    Pelicula pelicula = new Pelicula(idPelicula, titulo, anioEstreno, paisPeli, duracion, sinopsis);
	    
	    String selectGenero = "SELECT nombre, codigo FROM genero WHERE id = ?";
	    PreparedStatement ps4 = conn.prepareStatement(selectGenero);
	    ps4.setLong(1, idGenero);
	    ResultSet rs3 = ps4.executeQuery();
	    String nombre = null;
	    Long codigo = null;
	    if(rs3.next()) {
	    	nombre = rs3.getString("nombre");
	    	codigo = rs3.getLong("codigo");
	    } else {
	    	throw new SQLException("El participante seleccionado no existe en el sistema.");
	    }
	    	 
	    Genero genero = new Genero(idGenero, codigo, nombre);
	    
	    rs3.close();
	    rs2.close();
	    rs.close();
	    ps4.close();
	    ps3.close();
	    ps2.close();
	    ps.close();
	    conn.close();
	    
	    return new PeliculaGenero(pelicula, genero);
	    
	}
	
	public List<Pelicula> buscarPeliculas(String tituloBq, String participanteBq, Long idPaisBq, Long idGeneroBq) throws SQLException {
	 	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
	 	String selectPelicula = "SELECT DISTINCT p.* FROM pelicula p LEFT JOIN pelicula_participante pp ON p.id = pp.id_pelicula LEFT JOIN participante pt ON pp.id_participante = pt.id LEFT JOIN pelicula_genero pg ON p.id = pg.id_pelicula LEFT JOIN genero g ON pg.id_genero = g.id WHERE (? IS NULL OR LOWER(p.titulo) LIKE LOWER(?)) AND (? IS NULL OR LOWER(pt.nombre) LIKE LOWER(?)) AND (? = 0 OR p.id_pais = ?) AND (? = 0 OR g.id = ?)";	
	 	PreparedStatement ps = conn.prepareStatement(selectPelicula);
	 	ps.setString(1, tituloBq);
	 	ps.setString(2, "%" + tituloBq + "%");
	 	ps.setString(3, participanteBq);
	 	ps.setString(4, "%" + participanteBq + "%");
	 	ps.setLong(5, idPaisBq);
	 	ps.setLong(6, idPaisBq);
	 	ps.setLong(7, idGeneroBq);
	 	ps.setLong(8, idGeneroBq);
	 	ResultSet rs = ps.executeQuery();
	 	List<Pelicula> peliculas = new ArrayList<Pelicula>();
	 	while (rs.next()) {
	 		Long id = rs.getLong("id");
	 		String titulo = rs.getString("titulo");
	 		Integer anioEstreno = rs.getInt("anio_estreno");
	 		Long idPais = rs.getLong("id_pais");
	 		Integer duracion = rs.getInt("duracion");
	 		String sinopsis = rs.getString("sinopsis");
	 
	 		String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
	 	    PreparedStatement ps2 = conn.prepareStatement(selectPais);
	 	    ps2.setLong(1, idPais);
	 	    ResultSet rs2 = ps2.executeQuery();
	 	    rs2.next();
	 
	 	    String continente = rs2.getString("continente");
	 	    String nombre = rs2.getString("nombre");
	 
	 
	 	    Pais pais = new Pais(idPais, continente, nombre);
	 
	 		Pelicula pelicula = new Pelicula(id, titulo, anioEstreno, pais, duracion, sinopsis);
	 		peliculas.add(pelicula);
	 
	 		ps2.close();
	 		rs2.close();
	 	}
	 
	 	ps.close();
	 	rs.close();
	 	conn.close();
	 	return peliculas;
	 }
	
	public List<Participante> buscarParticipante(String nombreBq) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
		String selectParticipante = "SELECT * FROM participante WHERE ? IS NULL OR LOWER(nombre) LIKE LOWER(?)";	
		PreparedStatement ps = conn.prepareStatement(selectParticipante);
		ps.setString(1, nombreBq);
		ps.setString(2, "%" + nombreBq + "%");
		ResultSet rs = ps.executeQuery();
		List<Participante> participantes = new ArrayList<Participante>();
		while (rs.next()) {
			Long id = rs.getLong("id");
			String nombre = rs.getString("nombre");
			Long idPais = rs.getLong("id_pais");
			Date fechaNacimiento = rs.getDate("fecha_nacimiento");
			
			String selectPais = "SELECT continente, nombre FROM pais WHERE id = ?";
		    PreparedStatement ps2 = conn.prepareStatement(selectPais);
		    ps2.setLong(1, idPais);
		    ResultSet rs2 = ps2.executeQuery();
		    rs2.next();
		    
		    String continente = rs2.getString("continente");
		    String nombrePais = rs2.getString("nombre");
		    
	
		    Pais pais = new Pais(idPais, continente, nombrePais);
		    		
			Participante participante = new Participante(id, nombre, pais, fechaNacimiento);
			participantes.add(participante);
			
			ps2.close();
			rs2.close();
		}
		
		ps.close();
		rs.close();
		conn.close();
		return participantes;
	}
	
	public Pais crearPais(String continente, String nombre) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertPais = "INSERT INTO pais (continente, nombre) VALUES(?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertPais, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, continente);
	    ps.setString(2, nombre);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el país.");
	    }

	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    Long idPais = null;
	    if (generatedKeys.next()) {
	        idPais = generatedKeys.getLong(1);
	    } else {
	        throw new SQLException("No se pudo obtener el ID del país insertado.");
	    }

	    Pais pais = new Pais(idPais, continente, nombre);

	    ps.close();
	    conn.close();

	    return pais;
	}
	
	public Pais modificarPais(Long idPais, String continente, String nombre) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String modPais = "UPDATE pais SET continente = ?, nombre = ? WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(modPais); 
	    ps.setString(1, continente);
	    ps.setString(2, nombre);
	    ps.setLong(3, idPais);
	    
	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido modificar el país.");
	    }

	    Pais pais = new Pais(idPais, continente, nombre);

	    ps.close();
	    conn.close();

	    return pais;
	}
	
	public void eliminarPais(Long id) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String deletePais = "DELETE FROM pais WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(deletePais);
	    ps.setLong(1, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        ps.close();
	        conn.close();
	        throw new SQLException("No se ha podido eliminar el país.");
	    }

	    ps.close();
	    conn.close();
	}
	
	public Genero crearGenero(Long codigo, String nombre) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String insertPais = "INSERT INTO genero (continente, nombre) VALUES(?,?)";
	    PreparedStatement ps = conn.prepareStatement(insertPais, Statement.RETURN_GENERATED_KEYS);
	    ps.setLong(1, codigo);
	    ps.setString(2, nombre);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar el género.");
	    }

	    ResultSet generatedKeys = ps.getGeneratedKeys();
	    Long idGenero = null;
	    if (generatedKeys.next()) {
	    	idGenero = generatedKeys.getLong(1);
	    } else {
	        throw new SQLException("No se pudo obtener el ID del género insertado.");
	    }

	    Genero genero = new Genero(idGenero, codigo, nombre);

	    ps.close();
	    conn.close();

	    return genero;
	}
	
	public Genero modificarGenero(Long idGenero, Long codigo, String nombre) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String modPais = "UPDATE genero SET codigo = ?, nombre = ? WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(modPais); 
	    ps.setLong(1, codigo);
	    ps.setString(2, nombre);
	    ps.setLong(3, idGenero);
	    
	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido modificar el género.");
	    }

	    Genero genero = new Genero(idGenero, codigo, nombre);

	    ps.close();
	    conn.close();

	    return genero;
	}
	
	public void eliminarGenero(Long id) throws SQLException {
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario", "usuario");

	    String deleteGenero = "DELETE FROM genero WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(deleteGenero);
	    ps.setLong(1, id);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        ps.close();
	        conn.close();
	        throw new SQLException("No se ha podido eliminar el género.");
	    }

	    ps.close();
	    conn.close();
	}
	
	public List<Pais> nombrePaises() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
		String selectPaises = "SELECT * FROM pais";
		PreparedStatement ps = conn.prepareStatement(selectPaises);
		ResultSet rs = ps.executeQuery();
		List<Pais> paises = new ArrayList<Pais>();
		while (rs.next()) {
			Long id = rs.getLong("id");
			String nombre = rs.getString("nombre");
			String continente = rs.getString("continente");
	
		    Pais pais = new Pais(id, continente, nombre);
		    
		    paises.add(pais);
		}
		
		ps.close();
		rs.close();
		conn.close();
		return paises;
	}
	
	public List<Genero> nombreGeneros() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "usuario","usuario");
		String selectGeneros = "SELECT * FROM genero";
		PreparedStatement ps = conn.prepareStatement(selectGeneros);
		ResultSet rs = ps.executeQuery();
		List<Genero> generos = new ArrayList<Genero>();
		while (rs.next()) {
			Long id = rs.getLong("id");
			Long codigo = rs.getLong("codigo");
			String nombre = rs.getString("nombre");
	
		    Genero genero = new Genero(id, codigo, nombre);
		    
		    generos.add(genero);
		}
		
		ps.close();
		rs.close();
		conn.close();
		return generos;
	}
	
	
	
}
