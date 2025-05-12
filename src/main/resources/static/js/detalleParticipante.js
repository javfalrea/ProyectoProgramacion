function getIdParticipanteDesdeUrl() {
	const params = new URLSearchParams(window.location.search);
	return params.get('id');
}

function cargarDetalleParticipante() {
	const id = getIdParticipanteDesdeUrl();
	console.log(id);
	fetch(`http://localhost:9999/buscar_participanteid?id=${id}`)
		.then(res => res.json())
		.then(p => {
			document.getElementById("nombre").textContent = p.nombre;
			document.getElementById("fechaNacimiento").textContent = p.fechaNacimiento;
			document.getElementById("pais").textContent = p.pais.nombre;
		})
	.catch(e => console.log("Error: " + e.message));
		
	fetch(`http://localhost:9999/pelicula_actor?id=${id}`)
		.then(res => res.json())
		.then(p => {
			for (let i = 0; i < p.length; i++) {
				const li = document.createElement("li");
				li.innerHTML = `${p[i].titulo} <button onclick="verDetallesPelicula('${p[i].id}')" class="botonPart">Detalles</button>`;				
				document.getElementById("peliculaActor").appendChild(li);
			}
		})
		.catch(e => console.log("Error: " + e.message));

	fetch(`http://localhost:9999/pelicula_director?id=${id}`)
		.then(res => res.json())
		.then(p => {
			for (let i = 0; i < p.length; i++) {
				const li = document.createElement("li");
				li.innerHTML = `${p[i].titulo} <button onclick="verDetallesPelicula('${p[i].id}')" class="botonPart">Detalles</button>`;				
				document.getElementById("peliculaDirector").appendChild(li);
			}
		})
		.catch(e => console.log("Error: " + e.message));
	
}

function verDetallesPelicula(idPelicula) {
    window.location.href = `detallePelicula.html?id=${idPelicula}`;
}

