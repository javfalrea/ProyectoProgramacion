package com.daw.datamodel.entities;

/**
 * Clase que gestiona la información básica de una relación
 * entre una película y los géneros que le corresponden.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class PeliculaGenero {
	
	private Pelicula pelicula;
	private Genero genero;
	
	public PeliculaGenero (Pelicula pelicula, Genero genero) {
		this.pelicula = pelicula;
		this.genero = genero;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

}
