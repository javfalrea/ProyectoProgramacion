package com.daw.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

/**
 * Esta clase permite gestionar la información básica de un participante
 * incluyendo su id, nombre, nacionalidad y fecha de nacimiento.
 * 
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Participante {
	
	private Long id;
	private String nombre;
	private String nacionalidad;
	private Date fechaNacimiento;
	
	public Participante(Long id, String nombre, String nacionalidad, Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
	/**
	 * Método que develve la edad en años del participante a partir de su fecha de nacimiento.
	 * 
	 * @return un <code>Integer</code> con la edad en años del participante.
	 */
	public Integer edad() {
		LocalDate fecha = this.fechaNacimiento.toLocalDate();
		LocalDate fechaActual = LocalDate.now();
		return Period.between(fecha, fechaActual).getYears();
	}
}
