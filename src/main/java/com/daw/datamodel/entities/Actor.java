package com.daw.datamodel.entities;

import java.sql.Date;

/**
 * Clase que gestiona la información básica de un actor
 * incluyendo su identificador, nombre, nacionalidad y fecha de nacimiento.
 * Además incluye una función para calcular la edad del mismo.
 * 
 * @see Participante
 * @author Javier Falcón Real
 * @version 1.0
 */

public class Actor extends Participante {

	public Actor(Long id, String nombre, String nacionalidad, Date fechaNacimiento) {
		super(id, nombre, nacionalidad, fechaNacimiento);
	}
	
}
