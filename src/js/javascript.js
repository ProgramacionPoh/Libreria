const apiKey = window.env.apiKey;


document.getElementById('searchButton').addEventListener('click', () => {
  const searchInput = document.getElementById('searchInput').value.trim();
  if (searchInput !== '') {
    searchBooks(searchInput);
  }
});



function searchBooks(searchQuery) {





  const apiUrl = `https://www.googleapis.com/books/v1/volumes?q=${searchQuery}&maxResults=6&key=${apiKey}`;

  fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      const booksContainer = document.getElementById('booksContainer');
      booksContainer.innerHTML = ''; // Limpiar el contenedor antes de agregar nuevos libros

      data.items.forEach((book, index) => {
        const bookInfo = book.volumeInfo;
        const cardId = book.id;
        const card = document.createElement('div');
        card.id = cardId;
        card.classList.add('col-md-4', 'mb-4');
        card.innerHTML = `
          <div class="card">
          
            <img src="${bookInfo.imageLinks?.smallThumbnail || 'https://via.placeholder.com/150'}" class="card-img-top" alt="${bookInfo.title}">
           
            <div class="card-body">

              <div class="titleStar"> 
              <h5 class="card-title">${bookInfo.title}</h5>
              <button class="btn btn-primary addToFavorites">
                 <i class="far fa-star"></i>
            </button>
            </div>
            

            <p class="card-text">${bookInfo.authors?.[0] || 'Autor desconocido'}</p>
            <h6 class="card-date">${bookInfo.publishedDate || ''}</h6>
              <button class="btn btn-primary showDescription" data-target="${cardId}">
              <a href="./src/models/descripcion.html?id=${cardId}" class="btn btn-primary">Ver Descripción</a>
              </button>
            </div>
          </div>
        `;

        const addToFavoritesBtn = card.querySelector('.addToFavorites');
        addToFavoritesBtn.addEventListener('click', () => addToFavorites(book, addToFavoritesBtn));
        booksContainer.appendChild(card);
      });
    })
    .catch(error => console.error('Error fetching books:', error));
}

function addToFavorites(book, button) {
  const filledStarIcon = document.createElement('i');
  filledStarIcon.classList.add('fas', 'fa-star');
  const apiUrl = 'http://localhost:8080/libros';
  const postData = {
    title: book.volumeInfo.title,
    authors: book.volumeInfo.authors?.[0] || 'Autor desconocido',
    previewLink: book.volumeInfo.imageLinks?.thumbnail || 'https://via.placeholder.com/150',
    description: book.volumeInfo.description || ''
  };

  fetch(apiUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(postData)
  })
  .then(response => {
    if (response.ok) {
      console.log('Libro agregado a favoritos:', postData.title);
      // Cambiar el ícono de estrella a lleno
      button.querySelector('i').classList.remove('far');
      button.querySelector('i').classList.add('fas');
      button.querySelector('i').classList.add('text-warning'); // opcional: cambiar el color a amarillo si lo deseas
    } else {
      console.error('Error al agregar el libro a favoritos:', response.statusText);
    }
  })
  .catch(error => console.error('Error al agregar el libro a favoritos:', error));
}
