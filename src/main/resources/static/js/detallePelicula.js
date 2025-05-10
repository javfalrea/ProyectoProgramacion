let valorada = false;

function getIdPeliculaDesdeUrl() {
	const params = new URLSearchParams(window.location.search);
	return params.get('id');
}

function cargarDetallePelicula() {
	const id = getIdPeliculaDesdeUrl();
	fetch(`http://localhost:9999/genero_pelicula?id=${id}`)
		.then(res => res.json())
		.then(gp => {
			document.getElementById("titulo").textContent = gp[0].pelicula.titulo;
			document.getElementById("anio").textContent = gp[0].pelicula.anio_estreno;
			document.getElementById("pais").textContent = gp[0].pelicula.pais.nombre;
			document.getElementById("duracion").textContent = gp[0].pelicula.duracion;
			document.getElementById("sinopsis").textContent = gp[0].pelicula.sinopsis;
			for (let i = 0; i < gp.length; i++) {
				const li = document.createElement("li");
				li.textContent = gp[i].genero.nombre;
				document.getElementById("genero").appendChild(li);
			}
		})
	.catch(e => console.log("Error: " + e.message));
		
	fetch(`http://localhost:9999/director_pelicula?id=${id}`)
		.then(res => res.json())
		.then(d => {
			for (let i = 0; i < d.length; i++) {
				const li = document.createElement("li");
				li.textContent = d[i].nombre;
				document.getElementById("director").appendChild(li);
			}
		})
		.catch(e => console.log("Error: " + e.message));

	fetch(`http://localhost:9999/actor_pelicula?id=${id}`)
		.then(res => res.json())
		.then(a => {
			for (let i = 0; i < a.length; i++) {
				const li = document.createElement("li");
				li.textContent = a[i].nombre;
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