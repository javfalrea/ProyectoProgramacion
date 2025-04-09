function buscarAutores() {
    var nombre = document.getElementById("nombre").value;
    fetch("http://localhost:9999/consulta_autores?nombre=" + nombre)
    .then(res => res.text())
    .then(json => {
        const posts = JSON.parse(json);
        let tabla = "";
        posts.forEach(fila => {
            tabla += "<tr>";
            tabla += "<td>" + fila.id + "</td>";
            tabla += "<td>" + fila.nombre + "</td>";
            tabla += "<td>" + fila.fechaNacimiento + "</td>";
            tabla += "<td>" + fila.direccion + "</td>";
            tabla += "<td>" + fila.dni + "</td>";
            tabla += "<td><button onclick=\"abrirModificarAutor('" + fila.id + "','" + fila.nombre + "','" + fila.fechaNacimiento + "','" + fila.direccion + "','" + fila.dni + "')\">Modificar</button><button onclick=\"eliminarAutor('" + fila.id + "')\">Eliminar</button></td>";
            tabla += "</tr>";
        });

        var contenedor_tabla = document.getElementById("contenido_tabla");
        contenedor_tabla.innerHTML = tabla;
    })
    .catch(e => {
        console.log('Error importando archivo: ' + e.message);
    });
}

function abrirCrearAutor() {
    var modal = document.getElementById("ventanaModal");
    modal.style.display = "block";
}

function abrirCrearLibro() {
	var modal = document.getElementById("ventanaModal2");
	modal.style.display = "block";
}

function abrirModificarAutor(id, nombre, fechaNacimiento, direccion, dni) {
    document.getElementById("nombre_completo").value = nombre;
    document.getElementById("direccion").value = direccion;
    document.getElementById("dni").value = dni;
    document.getElementById("fechaNacimiento").value = fechaNacimiento;
    document.getElementById("idAutor").value = id;
    var modal = document.getElementById("ventanaModal");
    modal.style.display = "block";
}

function abrirModificarLibro(id, titulo, descripcion, fecha_publicacion, favorite, isbn, idioma, categoria, editorial) {
    document.getElementById("titulo").value = titulo;
    document.getElementById("descripcion").value = descripcion;
	document.getElementById("fecha_publicacion").value = fecha_publicacion;
    document.getElementById("favorite").value = favorite;
    document.getElementById("isbn").value = isbn;
	document.getElementById("idioma").value = idioma;
	document.getElementById("categoria").value = categoria;
	document.getElementById("editorial").value = editorial;
    document.getElementById("idLibro").value = id;
    var modal = document.getElementById("ventanaModal2");
    modal.style.display = "block";
}

function cerrarCrearAutor() {
    document.getElementById("nombre_completo").value = "";
    document.getElementById("direccion").value = "";
    document.getElementById("dni").value = "";
    document.getElementById("fechaNacimiento").value = "";
    document.getElementById("idAutor").value = "";
    var modal = document.getElementById("ventanaModal");
    modal.style.display = "none";
}

function cerrarCrearLibro() {
    document.getElementById("titulo").value = "";
    document.getElementById("descripcion").value = "";
    document.getElementById("fecha_publicacion").value = "";
    document.getElementById("favorite").checked = false;
    document.getElementById("isbn").value = "";
	document.getElementById("idioma").value = "";
	document.getElementById("categoria").selectedIndex = 0;
	document.getElementById("editorial").selectedIndex = 0;
    var modal = document.getElementById("ventanaModal2");
    modal.style.display = "none";
}

function gestionarAutor() {
    var nombre = document.getElementById("nombre_completo").value;
    var direccion = document.getElementById("direccion").value;
    var dni = document.getElementById("dni").value;
    var fechaNacimiento = document.getElementById("fechaNacimiento").value;
    var idAutor = document.getElementById("idAutor").value;

    var url = "";

    if (idAutor != "") {
        url = "http://localhost:9999/modificar_autor?idAutor=" + idAutor + "&nombre=" + nombre + "&dni=" + dni + "&direccion=" + direccion + "&fechaNacimiento=" + fechaNacimiento;
    } else {
        url = "http://localhost:9999/crear_autor?nombre=" + nombre + "&dni=" + dni + "&direccion=" + direccion + "&fechaNacimiento=" + fechaNacimiento;
    }

    fetch(url)
        .then(res => res.text())
        .then(res => {
            buscarAutores();
            cerrarCrearAutor();
        })
        .catch(e => {
            console.log('Error importando archivo: ' + e.message);
        });
}



// Corregir aquí: la función eliminarAutor debe estar fuera de gestionarAutor
function eliminarAutor(idAutor) {
    fetch("http://localhost:9999/eliminar_autor?idAutor=" + idAutor)
        .then(res => res.text())
        .then(res => {
            buscarAutores();
        })
        .catch(e => {
            console.log('Error importando archivo: ' + e.message);
        });
}

function cargaInicial() {
	let elementoSelect = document.getElementById("editorial");
	let elementoSelect2 = document.getElementById("categoria");
	
	fetch("buscar_peliculas")
	.then(res => res.text())
	.then(json => {
		const posts = JSON.parse(json);
		let opt = document.createElement("option");
		opt.value=0;
		opt.text="";
		elementoSelect.add(opt);
	    posts.forEach(editorial => {
	    	opt = document.createElement("option");
			opt.value=editorial.id;
			opt.text=editorial.nombre;
			elementoSelect.add(opt);
	    });
	})
	
	fetch("buscar_categorias")
		.then(res => res.text())
		.then(json => {
			const posts = JSON.parse(json);
			let opt = document.createElement("option");
			opt.value=0;
			opt.text="";
			elementoSelect2.add(opt);
		    posts.forEach(categoria => {
		    	opt = document.createElement("option");
				opt.value=categoria.id;
				opt.text=categoria.nombre;
				elementoSelect2.add(opt);
		    });
		})
	
	.catch(e => {
		alert('Error importando archivo: ' + e.message);
	});
}


function crearLibro() {
	    var titulo = document.getElementById("titulo").value;
	    var descripcion = document.getElementById("descripcion").value;
	    var fechaPublicacion = document.getElementById("fecha").value;
	    var favorite = document.getElementById("favorite").value;
	    var isbn = document.getElementById("isbn").value;
		var idioma = document.getElementById("idioma").value;
		var categoria = document.getElementById("editorial").value;
		var editorial = document.getElementById("categoria").value;


	    url = "http://localhost:9999/crear_libro?titulo=" + titulo + "&descripcion=" + descripcion + "&fecha_publicacion=" + fechaPublicacion + "&favorite=" + favorite + "&idioma" + idioma + "&isbn" + isbn + "&categoria_id" + categoria + "&editorial" + editorial;

	    fetch(url)
	        .then(res => res.text())
	        .then(res => {
	            buscarAutores();
	            cerrarCrearAutor();
	        })
	        .catch(e => {
	            console.log('Error importando archivo: ' + e.message);
	        });
}


