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

	    String insertPelicula = "UPDATE pelicula SET titulo = ?, anio_estreno = ?, id_pais = ?, duracion = ?, sinopsis = ? WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(insertPelicula); 
	    ps.setString(1, titulo);
	    ps.setInt(2, anioEstreno);
	    ps.setLong(3, idPais);
	    ps.setInt(4, duracion);
	    ps.setString(5, sinopsis);
	    ps.setLong(6, idPelicula);

	    int respuesta = ps.executeUpdate();
	    if (respuesta != 1) {
	        throw new SQLException("No se ha podido insertar la película.");
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
	String selectPelicula = "SELECT DISTINCT p.* FROM pelicula p LEFT JOIN pelicula_participante pp ON p.id = pp.id_pelicula LEFT JOIN participante pt ON pp.id_participante = pt.id LEFT JOIN pelicula_genero pg ON p.id = pg.id_pelicula LEFT JOIN genero g ON pg.id_genero = g.id WHERE (? IS NULL OR LOWER(p.titulo) LIKE LOWER(?)) AND (? IS NULL OR LOWER(pt.nombre) LIKE LOWER(?)) AND (? IS NULL OR p.id_pais = ?) AND (? IS NULL OR g.id = ?)";	
	PreparedStatement ps = conn.prepareStatement(selectPelicula);
	ps.setString(1, tituloBq);
	ps.setString(2, "%" + tituloBq + "%");
	ps.setString(3, participanteBq);
	ps.setString(4, "%" + participanteBq + "%");
	ps.setObject(5, idPaisBq);
	ps.setObject(6, idPaisBq);
	ps.setObject(7, idGeneroBq);
	ps.setObject(8, idGeneroBq);
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
