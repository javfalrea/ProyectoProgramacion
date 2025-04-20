function buscarParticipantes() {
	var nombre = document.getElementById("nombre").value.trim();
		
	fetch("http://localhost:9999/buscar_participante?nombre=" + nombre)
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let tabla = "";
		posts.forEach(fila => {
			tabla += "<tr>";
			tabla += "<td>" + fila.nombre + "</td>";
			tabla += "<td>" + fila.fechaNacimiento + "</td>";
			tabla += "<td>" + fila.pais.nombre + "</td>";
			tabla += "<td><button onclick=\"abrirModificarParticipante('" + fila.id + "','" + fila.nombre + "','" + fila.fechaNacimiento + "','" + fila.pais.id + "')\">Modificar</button><button onclick=\"eliminarParticipante('" + fila.id + "')\">Eliminar</button></td>";
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

function abrirModificarParticipante(id, nombre, fechaNacimiento, pais) {
	document.getElementById("idParticipante").value = id;
	document.getElementById("nombre").value = nombre;
	document.getElementById("fechaNacimiento").value = fechaNacimiento;
	document.getElementById("pais").value = pais;

	var modal = document.getElementById("modalParticipante");
	modal.style.display = "block";	
}

function cerrarCrearParticipante() {
	document.getElementById("idParticipante").value = "";
	document.getElementById("nombre").value = "";
	document.getElementById("fechaNacimiento").value = "";
	document.getElementById("pais").selectedIndex = 0;
		
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
		url = "http://localhost:9999/modificar_participante?idParticipante=" + idParticipante + "&nombre=" + nombre + "&idPais=" + pais + "&fechaNacimiento=" + fechaNacimiento;
	} else {
		url = "http://localhost:9999/crear_participante?nombre=" + nombre +  + "&idPais=" + pais + "&fechaNacimiento=" + fechaNacimiento;
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

function cargaInicial() {
	let selectPais = document.getElementById("pais");
	
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
	
}
