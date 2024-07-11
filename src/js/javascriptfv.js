document.addEventListener('DOMContentLoaded', loadFavoriteBooks);

function loadFavoriteBooks() {

    const apiUrl = `http://localhost:8080/libros/buscar`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const booksContainer = document.getElementById('booksContainer');
            booksContainer.innerHTML = ''; 

            data.forEach((book, index) => {
                const cardId = book.id;
                const card = document.createElement('div');
                card.id = cardId;
                card.classList.add('col-md-4'); 
                card.innerHTML = `
                    <div class="card">
                        <img src="${book.previewLink || 'https://via.placeholder.com/150'}" class="card-img-top" alt="${book.title}">
                        <div class="card-body">
                            <h5 class="card-title">${book.title}</h5>
                            <p class="card-text">${book.authors || 'Autor desconocido'}</p>
                            <p class="description"></p> <!-- Aquí se mostrará la descripción -->
                            <button class="btn btn-primary showDescription" data-target="${cardId}">Mostrar Descripción</button>
                        </div>
                    </div>
                `;
                
                const descriptionButton = card.querySelector('.showDescription');
                const descriptionElement = card.querySelector('.description');
                let descriptionVisible = false; 

                descriptionButton.addEventListener('click', () => {
                    if (descriptionVisible) {
                        descriptionElement.textContent = ''; 
                        descriptionButton.textContent = 'Mostrar Descripción';
                    } else {
                        descriptionElement.textContent = book.description || 'Descripción no disponible'; 
                        descriptionButton.textContent = 'Ocultar Descripción';
                    }
                    descriptionVisible = !descriptionVisible; 
                });

                booksContainer.appendChild(card);
            });
        })
        .catch(error => console.error('Error fetching books:', error));
}

