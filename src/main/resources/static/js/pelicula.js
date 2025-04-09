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

