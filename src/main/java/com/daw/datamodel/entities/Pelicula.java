package com.daw.datamodel.entities;

/**
 * Clase que gestiona la información básica de una película
 * incluyendo su identificador, título, año de estreno, país, duración
 * sinopsis y el identificador de la valoración asociada.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Pelicula {
	
	private Long id;
	private String titulo;
	private Integer anio_estreno;
	private String pais;
	private Integer duracion;
	private String sinopsis;
	private Long id_valoracion;
	
	public Pelicula(Long id, String titulo, Integer anio_estreno, String pais, Integer duracion, String sinopsis, Long id_valoracion) {
		this.id = id;
		this.titulo = titulo;
		this.anio_estreno = anio_estreno;
		this.pais = pais;
		this.duracion = duracion;
		this.sinopsis = sinopsis;
		this.id_valoracion = id_valoracion;
	}
		
	/* Este segundo constructor será necesario pues podemos insertar películas 
	 * que no tengan en principio una valoración asignada.
	 */
	public Pelicula(Long id, String titulo, Integer anio_estreno, String pais, Integer duracion, String sinopsis) {
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
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

	public Long getId_valoracion() {
		return id_valoracion;
	}

	public void setId_valoracion(Long id_valoracion) {
		this.id_valoracion = id_valoracion;
	}

}
