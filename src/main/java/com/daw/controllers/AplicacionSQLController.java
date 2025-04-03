package com.daw.controllers;

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
	
	@GetMapping("/buscar_peliculas")
	public ResponseEntity<?> buscarPeliculas(@RequestParam String nombre, 
			@RequestParam String actor, 
			@RequestParam String director, 
			@RequestParam String pais){
		try {
			return ResponseEntity.ok().body(servicio.buscarPeliculas(nombre, actor, director, pais));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/crear_valoracion")
	public ResponseEntity<?> crearAutor(@RequestParam String nombre,
			@RequestParam String dni,
			@RequestParam String direccion,
			@RequestParam Date fechaNacimiento){
		try {
			return ResponseEntity.ok().body(servicio.crearAutor(nombre, dni, direccion, fechaNacimiento));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	@GetMapping("/crear_libro")
	public ResponseEntity<?> crearLibro(@RequestParam String titulo,
			@RequestParam String descripcion,			
			@RequestParam Boolean favorite,
			@RequestParam Long idCategoria,
			@RequestParam Long idEditorial,
			@RequestParam Date fechaPublicacion,
			@RequestParam String isbn,
			@RequestParam String idioma){
		try {
			return ResponseEntity.ok().body(servicio.crearLibro(titulo, descripcion, favorite, fechaPublicacion, isbn, idioma, idCategoria, idEditorial));
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}
	
	
	
//
//	
//	@GetMapping("/consulta_libros")
//	public ResponseEntity<?> buscarLibros(@RequestParam String titulo,
//			@RequestParam String descripcion,
//			@RequestParam Boolean favorite){
//		try {
//			return ResponseEntity.ok().body(servicio.buscarLibros(titulo, descripcion, favorite));
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/crear_autor")
//	public ResponseEntity<?> crearAutor(@RequestParam String nombre,
//			@RequestParam String dni,
//			@RequestParam String direccion,
//			@RequestParam Date fechaNacimiento){
//		try {
//			return ResponseEntity.ok().body(servicio.crearAutor(nombre, dni, direccion, fechaNacimiento));
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/crear_libro")
//	public ResponseEntity<?> crearLibro(@RequestParam String titulo,
//			@RequestParam String descripcion,			
//			@RequestParam Boolean favorite,
//			@RequestParam Long idCategoria,
//			@RequestParam Long idEditorial,
//			@RequestParam Date fechaPublicacion,
//			@RequestParam String isbn,
//			@RequestParam String idioma){
//		try {
//			return ResponseEntity.ok().body(servicio.crearLibro(titulo, descripcion, favorite, fechaPublicacion, isbn, idioma, idCategoria, idEditorial));
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/modificar_autor")
//	public ResponseEntity<?> modificarAutor(@RequestParam Long idAutor,
//			@RequestParam String nombre,
//			@RequestParam String dni,
//			@RequestParam String direccion,
//			@RequestParam Date fechaNacimiento){
//		try {
//			return ResponseEntity.ok().body(servicio.modificarAutor(idAutor, nombre, dni, direccion, fechaNacimiento));
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/eliminar_autor")
//	public ResponseEntity<?> eliminarAutor(@RequestParam Long idAutor){
//		try {
//			servicio.eliminarAutor(idAutor);
//			return ResponseEntity.ok().body("Eliminación correcta");
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/buscar_editoriales")
//	public ResponseEntity<?> buscarEditoriales(){
//		try {
//			return ResponseEntity.ok().body(servicio.buscarEditoriales());
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
//	
//	@GetMapping("/buscar_categorias")
//	public ResponseEntity<?> buscarCategorias(){
//		try {
//			return ResponseEntity.ok().body(servicio.buscarCategorias());
//		} catch (SQLException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
//		}
//	}
	
	
	
	
	

}
