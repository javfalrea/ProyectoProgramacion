package com.daw.controllers;

/**
 * Esta clase permite gestionar la información básica de una valoración
 * incluyendo su identificador, nota, crítica y recomendación.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Valoracion {
	
	private Long id;
	private Float nota;
	private String critica;
	private Boolean recomendada;
	
	public Valoracion(Long id, Float nota, String critica, Boolean recomendada) {
		this.id = id;
		this.nota = nota;
		this.critica = critica;
		this.recomendada = recomendada;
	}
	
	/* Este segundo constructor será necesario pues podemos insertar valoraciones 
	 * que no tengan en principio una crítica asignada.
	 */
	public Valoracion(Long id, Float nota, Boolean recomendada) {
		this.id = id;
		this.nota = nota;
		this.recomendada = recomendada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
