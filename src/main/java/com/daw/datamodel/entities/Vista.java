package com.daw.datamodel.entities;

import java.sql.Date;

/**
 * Clase que gestiona la información básica de una vista
 * incluyendo su identificador, fecha de la vista y película a la que está asociada.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Vista {
	
	private Long id;
	private Date fecha;
	private Pelicula pelicula;
	
	public Vista(Long id, Date fecha, Pelicula pelicula) {
		this.id = id;
		this.fecha = fecha;
		this.pelicula = pelicula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

}
