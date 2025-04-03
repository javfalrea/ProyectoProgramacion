package com.daw.controllers;

import java.sql.Date;

/**
 * Clase que gestiona la información básica de un director
 * incluyendo su identificador, nombre, nacionalidad y fecha de nacimiento.
 * Además incluye una función para calcular la edad del mismo.
 * 
 * @see Participante
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Director extends Participante {
	
	public Director(Long id, String nombre, String nacionalidad, Date fechaNacimiento) {
		super(id, nombre, nacionalidad, fechaNacimiento);
	}

}
