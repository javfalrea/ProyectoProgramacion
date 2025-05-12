function buscarParticipantes() {
	var nombre = document.getElementById("nombreFiltro").value.trim();
		
	fetch("http://localhost:9999/buscar_participante?nombre=" + nombre)
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let tabla = "";
		posts.forEach(fila => {
			tabla += "<tr>";
			tabla += "<td>" + fila.nombre + "</td>";
			tabla += "<td>" + fila.pais.nombre + "</td>";
			tabla += "<td>" + fila.fechaNacimiento + "</td>";
			tabla += "<td><button onclick=\"abrirModificarParticipante('" + fila.id + "','" + fila.nombre + "','" + fila.pais.id + "','" + fila.fechaNacimiento + "')\">Modificar</button><button onclick=\"eliminarParticipante('" + fila.id + "')\">Eliminar</button><button onclick=\"verDetallesParticipante('" + fila.id + "')\">Detalles</button></td>";
			tabla += "<td><button onclick=\"abrirAgregarParticipante('" + fila.id + "')\">Agregar participante</button></td>";
			tabla += "</tr>";
		});	
		
		var contenidoTabla = document.getElementById("contenidoTabla");		
		contenidoTabla.innerHTML = tabla;
		
	})
	.catch(e => {
		console.log('Error importando archivo: ' + e.message);
	});
}

function abrirCrearParticipante() {
	var modal = document.getElementById("modalParticipante");
	modal.style.display = "block";
}

function abrirModificarParticipante(id, nombre, pais, fechaNacimiento) {
	document.getElementById("idParticipante").value = id;
	document.getElementById("nombre").value = nombre;
	document.getElementById("pais").value = pais;
	document.getElementById("fechaNacimiento").value = fechaNacimiento;

	var modal = document.getElementById("modalParticipante");
	modal.style.display = "block";	
}

function cerrarCrearParticipante() {
	document.getElementById("idParticipante").value = "";
	document.getElementById("nombre").value = "";
	document.getElementById("pais").selectedIndex = 0;
	document.getElementById("fechaNacimiento").value = "";
		
	var modal = document.getElementById("modalParticipante");
	modal.style.display = "none";	
}

function gestionarParticipante() {
	var idParticipante = document.getElementById("idParticipante").value;
	var nombre = document.getElementById("nombre").value;
	var fechaNacimiento = document.getElementById("fechaNacimiento").value;
	var pais = document.getElementById("pais").value;
	
	var url="";
	
	if(idParticipante != "") {
		url = "http://localhost:9999/modificar_participante?id=" + idParticipante + "&nombre=" + nombre + "&idPais=" + pais + "&fechaNacimiento=" + fechaNacimiento;
	} else {
		url = "http://localhost:9999/crear_participante?nombre=" + nombre + "&idPais=" + pais + "&fechaNacimiento=" + fechaNacimiento;
	}
	
	fetch(url)
	.then(res => res.text())
	.then(res => {
		buscarParticipantes();
		cerrarCrearParticipante();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	});
	
}

function abrirAgregarParticipante(id) {
	document.getElementById("idAgregarParticipante").value = id;
	var modal = document.getElementById("modalAgregarParticipante");
	modal.style.display = "block";
}

function cerrarAgregarParticipante() {
	document.getElementById("idAgregarParticipante").value = "";
	document.getElementById("pelicula").selectedIndex = 0;
	document.getElementById("esActor").checked = false;
	document.getElementById("esDirector").checked = false;
		
	var modal = document.getElementById("modalAgregarParticipante");
	modal.style.display = "none";	
}

function gestionarAgregarParticipante() {
	var idParticipante = document.getElementById("idAgregarParticipante").value;
	var pelicula = document.getElementById("pelicula").value;
	var esActor = document.getElementById("esActor").checked;
	var esDirector = document.getElementById("esDirector").checked;
	
	
	fetch("http://localhost:9999/anadir_participante?idPelicula=" + pelicula + "&idParticipante=" + idParticipante + "&esActor=" + esActor + "&esDirector=" + esDirector)
	.then(res => res.text())
	.then(res => {
		buscarParticipantes();
		cerrarAgregarParticipante();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	});
	
}

function eliminarParticipante(idParticipante) {
	if(!confirm("Confirma si quieres eliminarlo")) {
		return;
	}
	fetch("http://localhost:9999/eliminar_participante?id=" + idParticipante)
	.then(res => res.text())
	.then(res => {
		buscarParticipantes();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	})
}

function cargaInicial() {
	let selectPais = document.getElementById("pais");
	let selectPelicula = document.getElementById("pelicula")
	
	fetch("nombre_paises")
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let opt = document.createElement("option");
		opt.value = 0;
		opt.text = "";
		selectPais.add(opt);
		posts.forEach(pais => {
			opt = document.createElement("option");
			opt.value = pais.id;
			opt.text = pais.nombre;
			selectPais.add(opt);
		});
	})
	
	fetch("buscar_peliculas?titulo=&participante=&idPais=0&idGenero=0")
		.then(res => res.text())
		.then(json => {
			const posts = JSON.parse(json);
			let opt = document.createElement("option");
			opt.value = 0;
			opt.text = "";
			selectPelicula.add(opt);
			posts.forEach(pelicula => {
				opt = document.createElement("option");
				opt.value = pelicula.id;
				opt.text = pelicula.titulo;
				selectPelicula.add(opt);
			});
		})
	
}

function verDetallesParticipante(idParticipante) {
    window.location.href = `detalleParticipante.html?id=${idParticipante}`;
}
