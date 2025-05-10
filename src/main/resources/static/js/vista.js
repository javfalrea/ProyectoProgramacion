window.onload = function() {
	buscarVistas();
}

function getIdPeliculaDesdeUrl() {
	const params = new URLSearchParams(window.location.search);
	return params.get('id');
}

function buscarVistas() {
	const idPelicula = getIdPeliculaDesdeUrl();
		
	fetch("http://localhost:9999/vista_pelicula?id=" + idPelicula)
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let tabla = "";
		posts.forEach(fila => {
			tabla += "<tr>";
			tabla += "<td>" + fila.fecha + "</td>";
			tabla += "<td><button onclick=\"eliminarVista('" + fila.id + "')\">Eliminar</button></td>";
			tabla += "</tr>";
		});	
		
		var contenidoTabla = document.getElementById("contenidoTabla");		
		contenidoTabla.innerHTML = tabla;
		
	})
	.catch(e => {
		console.log('Error importando archivo: ' + e.message);
	});
}

function abrirCrearVista() {
	var modal = document.getElementById("modalVista");
	modal.style.display = "block";
}


function cerrarCrearVista() {
	document.getElementById("idVista").value = "";
	document.getElementById("fecha").value = "";
		
	var modal = document.getElementById("modalVista");
	modal.style.display = "none";	
}

function gestionarVista() {
	var idPelicula = getIdPeliculaDesdeUrl();
	
	var fecha = document.getElementById("fecha").value;
	var idVista = document.getElementById("idVista").value;
	
	var url = "http://localhost:9999/crear_vista?id=" + idVista + "&idPelicula=" + idPelicula + "&fecha=" + fecha;
	
	fetch(url)
	.then(res => res.text())
	.then(res => {
		buscarVistas();
		cerrarCrearVista();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	});
	
}



function eliminarVista(idVista) {
	if(!confirm("Confirma si quieres eliminarlo")) {
		return;
	}
	fetch("http://localhost:9999/eliminar_vista?id=" + idVista)
	.then(res => res.text())
	.then(res => {
		buscarVistas();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	})
}
