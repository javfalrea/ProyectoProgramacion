// Función para cargar los países dinámicamente
function cargarPaises() {
  fetch('/nombre_paises') // Ajusta esta URL según tu API
    .then(response => response.json())
    .then(paises => {
      const selectPais = document.getElementById('pais');
      // Mantener la opción por defecto
      selectPais.innerHTML = '<option value="">Seleccione un país</option>';
      
      // Agregar cada país como una opción
      paises.forEach(pais => {
        const option = document.createElement('option');
        option.value = pais.id; // El ID que se enviará al backend
        option.textContent = pais.nombre; // El nombre que verá el usuario
        selectPais.appendChild(option);
      });
    })
    .catch(error => console.error('Error al cargar países:', error));
}

// Función para abrir el modal y cargar los países
function abrirCrearPelicula() {
  document.getElementById('ventanaModalPelicula').style.display = 'block';
  cargarPaises(); // Llamar a la función de carga de países
}

// Función para cerrar el modal
function cerrarCrearPelicula() {
  document.getElementById('ventanaModalPelicula').style.display = 'none';
}

function gestionarPelicula() {
    var titulo = document.getElementById("titulo").value;
    var anioEstreno = document.getElementById("anio_estreno").value;
    var paisId = document.getElementById("pais").value;
    var duracion = document.getElementById("duracion").value;
    var sinopsis = document.getElementById("sinopsis").value;

    // La URL siempre será la de crear, sin importar si existe un idPelicula
    var url = "http://localhost:9999/crear_pelicula?titulo=" + titulo + "&anioEstreno=" + anioEstreno + "&paisId=" + paisId + "&duracion=" + duracion + "&sinopsis=" + sinopsis;

    // Realizar el POST para insertar una nueva película
    fetch(url, {
        method: 'POST',  // Usar siempre POST para la creación
    })
        .then(res => res.text())
        .then(res => {
            buscarPeliculas();  // Actualizar la lista de películas
            cerrarCrearPelicula();  // Cerrar el formulario de creación
        })
        .catch(e => {
            console.log('Error importando archivo: ' + e.message);
        });
}


// Cargar países al cargar la página (opcional)
document.addEventListener('DOMContentLoaded', () => {
  // Puedes cargar los países desde aquí si deseas tenerlos precargados
});
