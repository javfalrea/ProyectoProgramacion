function buscarPaises() {	
	fetch("http://localhost:9999/nombre_paises")
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let tabla = "";
		posts.forEach(fila => {
			tabla += "<tr>";
			tabla += "<td>" + fila.nombre + "</td>";
			tabla += "<td>" + fila.continente + "</td>";
			tabla += "<td><button onclick=\"abrirModificarPais('" + fila.id + "','" + fila.continente + "','" + fila.nombre + "')\">Modificar</button><button onclick=\"eliminarPais('" + fila.id + "')\">Eliminar</button></td>";
			tabla += "</tr>";
		});	
		
		var contenidoTabla = document.getElementById("contenidoTabla");		
		contenidoTabla.innerHTML = tabla;
		
	})
	.catch(e => {
		console.log('Error importando archivo: ' + e.message);
	});
}

function abrirCrearPais() {
	var modal = document.getElementById("modalPais");
	modal.style.display = "block";
}

function abrirModificarPais(id, continente, nombre) {
	document.getElementById("idPais").value = id;
	document.getElementById("continente").value = continente;
	document.getElementById("nombre").value = nombre;

	var modal = document.getElementById("modalPais");
	modal.style.display = "block";	
}

function cerrarCrearPais() {
	document.getElementById("idPais").value = "";
	document.getElementById("continente").value = "";
	document.getElementById("nombre").value = "";
		
	var modal = document.getElementById("modalPais");
	modal.style.display = "none";	
}

function gestionarPais() {
	var idPais = document.getElementById("idPais").value;
	var nombre = document.getElementById("nombre").value;
	var continente = document.getElementById("continente").value;
	
	var url="";
	
	if(idPais != "") {
		url = "http://localhost:9999/modificar_pais?id=" + idPais + "&continente=" + continente + "&nombre=" + nombre;
	} else {
		url = "http://localhost:9999/crear_pais?continente=" + continente + "&nombre=" + nombre;
	}
	
	fetch(url)
	.then(res => res.text())
	.then(res => {
		buscarPaises();
		cerrarCrearPais();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	});
	
}

function eliminarPais(idPais) {
	if(!confirm("Confirma si quieres eliminarlo")) {
		return;
	}
	fetch("http://localhost:9999/eliminar_pais?id=" + idPais)
	.then(res => res.text())
	.then(res => {
		buscarPaises();
	})
	.catch(e => {
		console.log('Error importando archivo' + e.message);
	})
}

function cargaInicial() {
	buscarPaises();
}
