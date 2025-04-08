package com.daw.controllers;

import java.sql.Date;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.services.Servicio;

@RestController
public class AplicacionSQLController {
	
	@Autowired
	private Servicio servicio;
	
	@GetMapping("/crear_pelicula")
	public ResponseEntity<?> crearPelicula(@RequestParam String titulo,
			@RequestParam Integer anioEstreno,			
			@RequestParam Long idPais,
			@RequestParam Integer duracion,
			@RequestParam String sinopsis){
		try {
			return ResponseEntity.ok().body(servicio.crearPelicula(titulo, anioEstreno, idPais, duracion, sinopsis));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/modificar_pelicula")
	public ResponseEntity<?> modificarPelicula(@RequestParam Long idPelicula,
			@RequestParam String titulo,
			@RequestParam Integer anioEstreno,			
			@RequestParam Long idPais,
			@RequestParam Integer duracion,
			@RequestParam String sinopsis){
		try {
			return ResponseEntity.ok().body(servicio.modificarPelicula(idPelicula, titulo, anioEstreno, idPais, duracion, sinopsis));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/eliminar_pelicula")
	public ResponseEntity<?> eliminarPelicula(@RequestParam Long id){
		try {
			servicio.eliminarPelicula(id);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/crear_participante")
	public ResponseEntity<?> crearParticipante(@RequestParam String nombre,			
			@RequestParam Long idPais,
			@RequestParam Date fechaNacimiento){
		try {
			return ResponseEntity.ok().body(servicio.crearParticipante(nombre, idPais, fechaNacimiento));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/modificar_participante")
	public ResponseEntity<?> modificarParticipante(@RequestParam Long id,
			@RequestParam String nombre,			
			@RequestParam Long idPais,
			@RequestParam Date fechaNacimiento){
		try {
			return ResponseEntity.ok().body(servicio.modificarParticipante(id, nombre, idPais, fechaNacimiento));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/eliminar_participante")
	public ResponseEntity<?> eliminarParticipante(@RequestParam Long id){
		try {
			servicio.eliminarParticipante(id);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/crear_valoracion")
	public ResponseEntity<?> crearValoracion(@RequestParam Long idPelicula,			
			@RequestParam Float nota,
			@RequestParam Boolean recomendada,
			@RequestParam String critica){
		try {
			return ResponseEntity.ok().body(servicio.crearValoracion(idPelicula, nota, recomendada, critica));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/modificar_valoracion")
	public ResponseEntity<?> modificarValoracion(@RequestParam Long idPelicula,
			@RequestParam Float nota,
			@RequestParam Boolean recomendada,
			@RequestParam String critica){
		try {
			return ResponseEntity.ok().body(servicio.modificarValoracion(idPelicula, nota, recomendada, critica));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/crear_vista")
	public ResponseEntity<?> crearVista(@RequestParam Long idPelicula,
			@RequestParam Date fecha){
		try {
			return ResponseEntity.ok().body(servicio.crearVista(idPelicula, fecha));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/eliminar_vista")
	public ResponseEntity<?> eliminarVista(@RequestParam Long id){
		try {
			servicio.eliminarVista(id);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/anadir_participante")
	public ResponseEntity<?> anadirParticipante(@RequestParam Long idPelicula,
			@RequestParam Long idParticipante,
			@RequestParam Boolean esActor,
			@RequestParam Boolean esDirector){
		try {
			return ResponseEntity.ok().body(servicio.anadirParticipante(idPelicula, idParticipante, esActor, esDirector));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/anadir_genero")
	public ResponseEntity<?> anadirGenero(@RequestParam Long idPelicula,
			@RequestParam Long idGenero){
		try {
			return ResponseEntity.ok().body(servicio.anadirGenero(idPelicula, idGenero));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_peliculas")
	public ResponseEntity<?> buscarPeliculas(@RequestParam(required = false) String titulo,
			@RequestParam(required = false) String participante,
			@RequestParam(required = false) Long idPais,
			@RequestParam(required = false) Long idGenero
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarPeliculas(titulo, participante, idPais, idGenero));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_actor")
	public ResponseEntity<?> buscarActor(@RequestParam(required = false) String nombre
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarActor(nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_director")
	public ResponseEntity<?> buscarDirector(@RequestParam(required = false) String nombre
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarDirector(nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
}
