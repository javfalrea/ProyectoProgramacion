package com.daw.controllers;

import java.sql.Date;

public class Actor extends Participante {

	public Actor(Long id, String nombre, String nacionalidad, Date fechaNacimiento) {
		super(id, nombre, nacionalidad, fechaNacimiento);
	}
	
}
