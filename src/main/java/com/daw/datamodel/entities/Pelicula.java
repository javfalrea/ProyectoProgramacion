package com.daw.datamodel.entities;

/**
 * Clase que gestiona la información básica de una película
 * incluyendo su identificador, título, año de estreno, país, duración y sinopsis.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Pelicula {
	
	private Long id;
	private String titulo;
	private Integer anio_estreno;
	private Pais pais;
	private Integer duracion;
	private String sinopsis;
	
	public Pelicula(Long id, String titulo, Integer anio_estreno, Pais pais, Integer duracion, String sinopsis) {
		this.id = id;
		this.titulo = titulo;
		this.anio_estreno = anio_estreno;
		this.pais = pais;
		this.duracion = duracion;
		this.sinopsis = sinopsis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAnio_estreno() {
		return anio_estreno;
	}

	public void setAnio_estreno(Integer anio_estreno) {
		this.anio_estreno = anio_estreno;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

}
