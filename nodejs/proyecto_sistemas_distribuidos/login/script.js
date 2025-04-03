document.getElementById("urlBtn").addEventListener("click", function() {
    Swal.fire({
        title: "Ingrese la URL a convertir",
        input: "text",
        inputPlaceholder: "https://ejemplo.com",
        showCancelButton: true,
        confirmButtonText: "Guardar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed && result.value) {
            let nuevaURL = result.value.trim();

            if (nuevaURL) {
                let urlsGuardadas = JSON.parse(localStorage.getItem("urls")) || [];
                if (!urlsGuardadas.includes(nuevaURL)) {
                    urlsGuardadas.push(nuevaURL);
                    localStorage.setItem("urls", JSON.stringify(urlsGuardadas));
                    mostrarURLs();
                } else {
                    Swal.fire("Duplicado", "Esta URL ya está en la lista.", "warning");
                }
            }
        }
    });
});

function mostrarURLs() {
    let urlsGuardadas = JSON.parse(localStorage.getItem("urls")) || [];
    let urlList = document.getElementById("urlList");
    urlList.innerHTML = "";

    urlsGuardadas.forEach((url, index) => {
        let li = document.createElement("li");
        li.innerHTML = `
            <a href="${url}" target="_blank">${url}</a>
            <button class="delete-btn" onclick="eliminarURL(${index})">X</button>
        `;
        urlList.appendChild(li);
    });
}

function eliminarURL(index) {
    let urlsGuardadas = JSON.parse(localStorage.getItem("urls")) || [];
    urlsGuardadas.splice(index, 1);
    localStorage.setItem("urls", JSON.stringify(urlsGuardadas));
    mostrarURLs();
}

document.getElementById("convertAllBtn").addEventListener("click", function() {
    let urlsGuardadas = JSON.parse(localStorage.getItem("urls")) || [];

    if (urlsGuardadas.length === 0) {
        Swal.fire("No hay URLs", "Agrega al menos una URL para convertir.", "warning");
        return;
    }

    Swal.fire({
        title: "Convirtiendo URLs...",
        text: `Se están procesando ${urlsGuardadas.length} URLs.`,
        icon: "info",
        showConfirmButton: false,
        timer: 3000
    });

    // Simulación de conversión con un fetch (reemplaza con tu API real)
    /*
    fetch('/api/convertir-urls', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ urls: urlsGuardadas })
    })
    .then(response => response.json())
    .then(data => {
        Swal.fire("Conversión completa", `Descarga tus archivos en: ${data.download_link}`, "success");
    })
    .catch(error => Swal.fire("Error", "No se pudieron convertir las URLs", "error"));
    */
});

// Mostrar URLs guardadas al cargar la página
document.addEventListener("DOMContentLoaded", mostrarURLs);