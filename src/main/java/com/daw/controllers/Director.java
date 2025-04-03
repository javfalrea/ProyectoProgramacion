package com.daw.controllers;

import java.sql.Date;

public class Director extends Participante {
	
	public Director(Long id, String nombre, String nacionalidad, Date fechaNacimiento) {
		super(id, nombre, nacionalidad, fechaNacimiento);
	}

}
