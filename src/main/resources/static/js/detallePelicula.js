let valorada = false;

function getIdPeliculaDesdeUrl() {
	const params = new URLSearchParams(window.location.search);
	return params.get('id');
}

function cargarDetallePelicula() {
	const id = getIdPeliculaDesdeUrl();
	
	fetch(`http://localhost:9999/buscar_peliculaid?id=${id}`)
		.then(res => res.json())
		.then(p => {
			document.getElementById("titulo").textContent = p.titulo;
			document.getElementById("anio").textContent = p.anio_estreno;
			document.getElementById("pais").textContent = p.pais.nombre;
			document.getElementById("duracion").textContent = p.duracion;
			document.getElementById("sinopsis").textContent = p.sinopsis;
		})
		.catch(e => console.log("Error: " + e.message));
	
	fetch(`http://localhost:9999/genero_pelicula?id=${id}`)
		.then(res => res.json())
		.then(gp => {
			for (let i = 0; i < gp.length; i++) {
				const li = document.createElement("li");
				li.innerHTML = `
					${gp[i].genero.nombre} 
					<button onclick="eliminarGenero('${gp[i].genero.id}')" class="botonPart">Eliminar</button>
				`; 
				document.getElementById("genero").appendChild(li);
			}
		})
	.catch(e => console.log("Error: " + e.message));

	fetch(`http://localhost:9999/director_pelicula?id=${id}`)
		.then(res => res.json())
		.then(d => {
			for (let i = 0; i < d.length; i++) {
				const li = document.createElement("li");
				li.innerHTML = `
					${d[i].nombre} 
					<button onclick="verDetallesParticipante('${d[i].id}')" class="botonPart">Ver detalles</button>
					<button onclick="eliminarParticipante('${d[i].id}')" class="botonPart">Eliminar participación</button>
				`; 
					document.getElementById("director").appendChild(li);
			}
		})
		.catch(e => console.log("Error: " + e.message));

	fetch(`http://localhost:9999/actor_pelicula?id=${id}`)
		.then(res => res.json())
		.then(a => {
			for (let i = 0; i < a.length; i++) {
				const li = document.createElement("li");
				li.innerHTML = `
					${a[i].nombre} 
				  	<button onclick="verDetallesParticipante('${a[i].id}')" class="botonPart">Ver detalles</button>
				  	<button onclick="eliminarParticipante('${a[i].id}')" class="botonPart">Eliminar participación</button>
				`;
				document.getElementById("actor").appendChild(li);
			}
		})
		.catch(e => console.log("Error: " + e.message));
		
	fetch(`http://localhost:9999/vista_pelicula?id=${id}`)
		.then(res => res.json())
		.then(a => {
			let contador = a.length;
			document.getElementById("contadorVistas").textContent = contador;			
		})
		.catch(e => console.log("Error: " + e.message));

	fetch(`http://localhost:9999/valoracion_pelicula?id=${id}`)
		.then(res => res.json())
		.then(vp => {
			valorada = true;
			
			document.getElementById("botonCrearValoracion").style.display = "none";
			const modificar = document.createElement("button");
			modificar.textContent = "Modificar Valoración";
			modificar.onclick = () => abrirModificarValoracion(vp.nota, vp.recomendada, vp.critica);
			
			document.getElementById("detallePelicula").appendChild(modificar);
			
			document.getElementById("nota").textContent = vp.nota;
			document.getElementById("recomendada").textContent = vp.recomendada;
			document.getElementById("critica").textContent = vp.critica;
		})
		.catch(e => console.log("Error: " + e.message));
}

function abrirCrearValoracion() {
	var modal = document.getElementById("modalValoracion");
	modal.style.display = "block";
}

function cerrarCrearValoracion() {
	document.getElementById("notaM").value = "";
	document.getElementById("recomendadaM").checked = false;
	document.getElementById("criticaM").value = "";

	var modal = document.getElementById("modalValoracion");
	modal.style.display = "none";
}

function gestionarValoracion() {
	const id = getIdPeliculaDesdeUrl();
	var nota = document.getElementById("notaM").value;
	var recomendada = document.getElementById("recomendadaM").checked;
	var critica = document.getElementById("criticaM").value;

	var url = "";

	if (valorada) {
		url = "http://localhost:9999/modificar_valoracion?idPelicula=" + id + "&nota=" + nota + "&recomendada=" + recomendada + "&critica=" + critica;
	} else {
		url = "http://localhost:9999/crear_valoracion?idPelicula=" + id + "&nota=" + nota + "&recomendada=" + recomendada + "&critica=" + critica;
	}

	fetch(url)
		.then(res => res.text())
		.then(res => {
			window.location.reload();
			cargarDetallePelicula();
			cerrarCrearValoracion();
		})
		.catch(e => {
			console.log('Error importando archivo' + e.message);
		});

}

function abrirModificarValoracion(nota, recomendada, critica) {
	document.getElementById("idPelicula").value = getIdPeliculaDesdeUrl();
	document.getElementById("notaM").value = nota;
	document.getElementById("recomendadaM").checked = recomendada;
	document.getElementById("criticaM").value = critica;

	var modal = document.getElementById("modalValoracion");
	modal.style.display = "block";	
}

function verVistas() {
	const idPelicula = getIdPeliculaDesdeUrl();
    window.location.href = `vista.html?id=${idPelicula}`;
}

function verDetallesParticipante(idParticipante) {
    window.location.href = `detalleParticipante.html?id=${idParticipante}`;
}

function eliminarParticipante(idParticipante) {
	const idPelicula = getIdPeliculaDesdeUrl();
	if(!confirm("Confirma si quieres suprimir esta participación")) {
		return;
	}
	fetch("http://localhost:9999/desagregar_participante?idPelicula=" + idPelicula + "&idParticipante=" + idParticipante)
	.then(res => res.text())
	.then(res => {
		window.location.reload();
		cargarDetallePelicula();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	})
}

function eliminarGenero(idGenero) {
	const idPelicula = getIdPeliculaDesdeUrl();
	if(!confirm("Confirma si quieres suprimir este género")) {
		return;
	}
	fetch("http://localhost:9999/desagregar_genero?idPelicula=" + idPelicula + "&idGenero=" + idGenero)
	.then(res => res.text())
	.then(res => {
		window.location.reload();
		cargarDetallePelicula();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	})
}