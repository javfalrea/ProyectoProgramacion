package com.daw.datamodel.entities;

public class Pais {
	
	private Long id;
	private String continente;
	private String nombre;
	
	public Pais(Long id, String continente, String nombre) {
		this.id = id;
		this.continente = continente;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContinente() {
		return continente;
	}

	public void setContinente(String continente) {
		this.continente = continente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
