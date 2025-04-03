package com.daw.datamodel.entities;

/**
 * Clase que gestiona la información básica de una valoración
 * incluyendo su identificador, película a la que referencia, nota, crítica y recomendación.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Valoracion {
	
	private Pelicula pelicula;
	private Float nota;
	private String critica;
	private Boolean recomendada;
	
	public Valoracion(Pelicula pelicula, Float nota, String critica, Boolean recomendada) {
		this.setPelicula(pelicula);
		this.nota = nota;
		this.critica = critica;
		this.recomendada = recomendada;
	}
	
	/* Este segundo constructor será necesario pues podemos insertar valoraciones 
	 * que no tengan en principio una crítica asignada.
	 */
	public Valoracion(Pelicula pelicula, Float nota, Boolean recomendada) {
		this.pelicula = pelicula;
		this.nota = nota;
		this.recomendada = recomendada;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}

	public String getCritica() {
		return critica;
	}

	public void setCritica(String critica) {
		this.critica = critica;
	}

	public Boolean getRecomendada() {
		return recomendada;
	}

	public void setRecomendada(Boolean recomendada) {
		this.recomendada = recomendada;
	}

}
