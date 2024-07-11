document.addEventListener("DOMContentLoaded", function() {
    // Obtener el ID del libro de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');
  
    // Si no se proporciona un ID en la URL, muestra un mensaje de error
    if (!bookId) {
      console.error('No se proporcionó un ID de libro en la URL.');
      return;
    }
  
    // Llamar a la función para obtener la información del libro
    getBookInfo(bookId);
  });
  
  function getBookInfo(bookId) {
    // Utilizar el ID del libro para hacer una solicitud a la API de Google Books
    const apiUrl = `https://www.googleapis.com/books/v1/volumes/${bookId}`;
    
    fetch(apiUrl)
      .then(response =>  response.json())
      .then(data => {
        console.log("data......",data);
        const bookInfo = data.volumeInfo;
        // Actualizar los elementos HTML con la información del libro
        document.getElementById('bookImage').src = bookInfo.imageLinks?.medium || 'url_de_la_imagen_por_defecto.jpg';
        document.getElementById('title_descrip').textContent = bookInfo.title || 'Título del Libro';
        document.getElementById('publishedDate').textContent = bookInfo.publishedDate ? `Fecha de Publicación: ${bookInfo.publishedDate}` : 'Fecha de Publicación: Desconocida';
        document.getElementById('book_Description').textContent = bookInfo.description || 'Descripción del libro no disponible.';
        document.getElementById('book_Description').textContent = bookInfo.description || 'Descripción del libro no disponible.';
        
        puntuacion = bookInfo.averageRating

const stars = document.querySelectorAll('#starRating li');
console.log(stars);

stars.forEach((star, index) => {
    console.log(index < Math.floor(puntuacion));
//   itero las estrellas y le agrego la clase que corresponde
  if (index < Math.floor(puntuacion)) {

    star.classList.add('bi-star-fill');
  } else if (index === Math.floor(puntuacion) && puntuacion % 1 !== 0) {
    star.classList.add('bi-star-half');
  } else {
    star.classList.add('bi-star');
  }
});

      })
      .catch(error => console.error('Error fetching book information:', error));







  }

  
