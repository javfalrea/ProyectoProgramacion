function buscarPeliculas() {
	var titulo = document.getElementById("tituloFiltro").value.trim();
	var participante = document.getElementById("participanteFiltro").value.trim();
	var pais = document.getElementById("paisFiltro").value;
	var genero = document.getElementById("generoFiltro").value;
	
	pais = pais === "0" ? "" : pais;
	genero = genero === "0" ? "" : genero;
	
	fetch("http://localhost:9999/buscar_peliculas?titulo=" + titulo + "&participante=" + participante + "&idPais=" + pais + "&idGenero=" + genero)
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let tabla = "";
		posts.forEach(fila => {
			tabla += "<tr>";
			tabla += "<td>" + fila.titulo + "</td>";
			tabla += "<td>" + fila.anioEstreno + "</td>";
			tabla += "<td>" + fila.pais.nombre + "</td>";
			tabla += "<td>" + fila.duracion + "</td>";
			tabla += "<td><button onclick=\"abrirModificarPelicula('" + fila.id + "','" + fila.titulo + "','" + fila.anioEstreno + "','" + fila.pais.id + "','" + fila.duracion + "','" + fila.sinopsis + "')\">Modificar</button><button onclick=\"eliminarPelicula('" + fila.id + "')\">Elimminar</button></td>";
			tabla += "</tr>";
		});
		
		
		var contenidoTabla = document.getElementById("contenidoTabla");
		contenidoTabla.innerHTML = tabla;
	})
	.catch(e => {
		console.log('Error importando archivo: ' + e.message);
	});
}

function abrirCrearPelicula() {
	var modal = document.getElementById("modalPelicula");
	modal.style.display = "block";
}

function abrirModificarPelicula(id, titulo, anioEstreno, pais, duracion, sinopsis) {
	document.getElementById("idPelicula").value = id;
	document.getElementById("titulo").value = titulo;
	document.getElementById("anioEstreno").value = anioEstreno;
	document.getElementById("pais").value = pais;
	document.getElementById("duracion").value = duracion;
	document.getElementById("sinopsis").value = sinopsis;

	var modal = document.getElementById("modalPelicula");
	modal.style.display = "block";	
}

function cerrarCrearPelicula() {
	document.getElementById("idPelicula").value = "";
	document.getElementById("titulo").value = "";
	document.getElementById("anioEstreno").value = "";
	document.getElementById("pais").selectedIndex = 0;
	document.getElementById("duracion").value = "";
	document.getElementById("sinopsis").value = "";
		
	var modal = document.getElementById("modalPelicula");
	modal.style.display = "none";	
}

function gestionarPelicula() {
	var idPelicula = document.getElementById("idPelicula").value;
	var titulo = document.getElementById("titulo").value;
	var anioEstreno = document.getElementById("anioEstreno").value;
	var pais = document.getElementById("pais").value;
	var duracion = document.getElementById("duracion").value;
	var sinopsis = document.getElementById("sinopsis").value;
	
	var url="";
	
	if(idPelicula != "") {
		url = "http://localhost:9999/modificar_pelicula?idPelicula=" + idPelicula + "&titulo=" + titulo + "&anioEstreno=" + anioEstreno + "&pais=" + pais.id + "&duracion=" + duracion + "&sinopsis=" + sinopsis;
	} else {
		url = "http://localhost:9999/crear_pelicula?titulo=" + titulo + "&anioEstreno=" + anioEstreno + "&pais=" + pais.id + "&duracion=" + duracion + "&sinopsis=" + sinopsis;
	}
	
	fetch(url)
	.then(res => res.text())
	.then(res => {
		buscarPeliculas();
		cerrarCrearPelicula();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	});
	
}

function cargaInicial() {
	let selectPais = document.getElementById("pais");
	let selectPaisFiltro = document.getElementById("paisFiltro");
	let selectGeneroFiltro = document.getElementById("generoFiltro");
	
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
	
	fetch("nombre_paises")
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let opt = document.createElement("option");
		opt.value = 0;
		opt.text = "";
		selectPaisFiltro.add(opt);
		posts.forEach(pais => {
			opt = document.createElement("option");
			opt.value = pais.id;
			opt.text = pais.nombre;
			selectPaisFiltro.add(opt);
		});
	})
	
	fetch("nombre_generos")
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let opt = document.createElement("option");
		opt.value = 0;
		opt.text = "";
		selectGeneroFiltro.add(opt);
		posts.forEach(genero => {
			opt = document.createElement("option");
			opt.value = genero.id;
			opt.text = genero.nombre;
			selectGeneroFiltro.add(opt);
		});
	})
}
