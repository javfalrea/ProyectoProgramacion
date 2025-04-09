document.addEventListener("DOMContentLoaded", function() {
    // Cargar los países
    fetch("/nombre_paises")
        .then(response => response.json())
        .then(paises => {
            const paisFiltro = document.getElementById("paisFiltro");
            paises.forEach(pais => {
                const option = document.createElement("option");
                option.value = pais.id;
                option.textContent = pais.nombre;
                paisFiltro.appendChild(option);
            });
        })
        .catch(error => console.error("Error al cargar los países:", error));

    // Cargar los géneros
    fetch("/nombre_generos")
        .then(response => response.json())
        .then(generos => {
            const generoFiltro = document.getElementById("generoFiltro");
            generos.forEach(genero => {
                const option = document.createElement("option");
                option.value = genero.id;
                option.textContent = genero.nombre;
                generoFiltro.appendChild(option);
            });
        })
        .catch(error => console.error("Error al cargar los géneros:", error));
});


document.getElementById("buscarPelicula").addEventListener("click", function() {
    // Recoger los valores de los filtros
    const titulo = document.getElementById("tituloFiltro").value.trim() || "";
    const participante = document.getElementById("participanteFiltro").value.trim() || "";
    const idPais = document.getElementById("paisFiltro").value || null;
    const idGenero = document.getElementById("generoFiltro").value || null;

    // Crear la URL con los filtros
    let url = "/buscar_peliculas?tituloBq=" + titulo + "&participanteBq=" + participante + "&idPaisBq=" + idPais + "&idGeneroBq=" + idGenero;

    // Hacer la solicitud GET al backend
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Limpiar la tabla antes de mostrar nuevos resultados
            const tabla = document.getElementById("tablaPeliculas").getElementsByTagName('tbody')[0];
            tabla.innerHTML = "";

            // Rellenar la tabla con los datos recibidos
            data.forEach(pelicula => {
                const row = tabla.insertRow();

                row.innerHTML = `
                    <td>${pelicula.titulo}</td>
                    <td>${pelicula.anio_estreno}</td>
                    <td>${pelicula.pais.nombre}</td>
                    <td>${pelicula.duracion}</td>
                `;
            });
        })
        .catch(error => {
            console.error("Error al cargar las películas:", error);
        });
});


