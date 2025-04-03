package com.daw.datamodel.entities;

public class Genero {
	
	/**
	 * Clase que gestionaa la información básica de un género
	 * incluyendo su identificador, código y nombre.
	 * 
	 * @author Javier Falcón Real
	 * @version 1.0
	 */
	
	private Long id;
	private Long codigo;
	private String nombre;
	
	public Genero(Long id, Long codigo, String nombre) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
