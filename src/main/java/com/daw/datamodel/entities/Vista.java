package com.daw.datamodel.entities;

import java.sql.Date;

/**
 * Clase que gestiona la información básica de una vista
 * incluyendo su identificador, fecha de la vista e identificador de la película asociada.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Vista {
	
	private Long id;
	private Date fecha;
	private Long id_pelicula;
	
	public Vista(Long id, Date fecha, Long id_pelicula) {
		this.id = id;
		this.fecha = fecha;
		this.id_pelicula = id_pelicula;
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

	public Long getId_pelicula() {
		return id_pelicula;
	}

	public void setId_pelicula(Long id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

}
