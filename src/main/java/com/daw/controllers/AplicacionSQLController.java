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
			@RequestParam Long idPais,
			@RequestParam Long idGenero
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarPeliculas(titulo, participante, idPais, idGenero));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/genero_pelicula")
	public ResponseEntity<?> generoPelicula(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.generoPelicula(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/valoracion_pelicula")
	public ResponseEntity<?> valoracionPelicula(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.valoracionPelicula(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/vista_pelicula")
	public ResponseEntity<?> vistaPelicula(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.vistaPelicula(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_participante")
	public ResponseEntity<?> buscarActor(@RequestParam(required = false) String nombre
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarParticipante(nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}

	
	@GetMapping("/crear_pais")
	public ResponseEntity<?> crearPais(@RequestParam String continente,
			@RequestParam String nombre			
			){
		try {
			return ResponseEntity.ok().body(servicio.crearPais(continente, nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/modificar_pais")
	public ResponseEntity<?> modificarPais(@RequestParam Long id,
			@RequestParam String continente,
			@RequestParam String nombre			
			){
		try {
			return ResponseEntity.ok().body(servicio.modificarPais(id, continente, nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	
	
	@GetMapping("/eliminar_pais")
	public ResponseEntity<?> eliminarPais(@RequestParam Long id){
		try {
			servicio.eliminarPais(id);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/nombre_paises")
	public ResponseEntity<?> nombrePaises(){
		try {
			return ResponseEntity.ok().body(servicio.nombrePaises());
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/crear_genero")
	public ResponseEntity<?> crearGenero(@RequestParam Long codigo,
			@RequestParam String nombre			
			){
		try {
			return ResponseEntity.ok().body(servicio.crearGenero(codigo, nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/modificar_genero")
	public ResponseEntity<?> modificarGenero(@RequestParam Long id,
			@RequestParam Long codigo,
			@RequestParam String nombre			
			){
		try {
			return ResponseEntity.ok().body(servicio.modificarGenero(id, codigo, nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/eliminar_genero")
	public ResponseEntity<?> eliminarGenero(@RequestParam Long id){
		try {
			servicio.eliminarGenero(id);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/nombre_generos")
	public ResponseEntity<?> nombreGeneros(){
		try {
			return ResponseEntity.ok().body(servicio.nombreGeneros());
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/director_pelicula")
	public ResponseEntity<?> directorPelicula(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.directorPelicula(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/actor_pelicula")
	public ResponseEntity<?> actorPelicula(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.actorPelicula(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/desagregar_participante")
	public ResponseEntity<?> desagregarParticipante(@RequestParam Long idPelicula,
			@RequestParam Long idParticipante){
		try {
			servicio.desagregarParticipante(idPelicula, idParticipante);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_participanteid")
	public ResponseEntity<?> buscarParticipanteId(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarParticipanteId(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_peliculaid")
	public ResponseEntity<?> buscarPeliculaId(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarPeliculaId(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/pelicula_actor")
	public ResponseEntity<?> peliculaActor(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.peliculaActor(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/pelicula_director")
	public ResponseEntity<?> peliculaDirector(@RequestParam Long id
			){
		try {
			return ResponseEntity.ok().body(servicio.peliculaDirector(id));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/desagregar_genero")
	public ResponseEntity<?> desagregarGenero(@RequestParam Long idPelicula,
			@RequestParam Long idGenero){
		try {
			servicio.desagregarGenero(idPelicula, idGenero);
			return ResponseEntity.ok().body("Eliminación correcta");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/buscar_genero")
	public ResponseEntity<?> buscarGenero(@RequestParam(required = false) String nombre
			){
		try {
			return ResponseEntity.ok().body(servicio.buscarGenero(nombre));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
}
