package com.daw.datamodel.entities;

/**
 * Clase que gestiona la información básica de una relación
 * entre una película y las personas que participan en ella, 
 * especificando también el rol o roles que llevan a cabo.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class PeliculaParticipante {
	
	private Pelicula pelicula;
	private Participante participante;
	private Boolean esActor = false;
	private Boolean esDirector = false;
	
	public PeliculaParticipante(Pelicula pelicula, Participante participante, Boolean esActor, Boolean esDirector) {
		this.pelicula = pelicula;
		this.participante = participante;
		this.esActor = esActor;
		this.esDirector = esDirector;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Boolean getEsActor() {
		return esActor;
	}

	public void setEsActor(Boolean esActor) {
		this.esActor = esActor;
	}

	public Boolean getEsDirector() {
		return esDirector;
	}

	public void setEsDirector(Boolean esDirector) {
		this.esDirector = esDirector;
	}

}
