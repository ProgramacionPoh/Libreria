document.addEventListener("DOMContentLoaded", function() {
  const animatedCards = document.querySelectorAll('.animated-card');

  const options = {
    root: null, 
    rootMargin: '0px', 
    threshold: 0.1 // 
  };

  function handleIntersection(entries) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
      } else {
        return
      }
    });
  }

  const observer = new IntersectionObserver(handleIntersection, options);

  animatedCards.forEach(card => {
    observer.observe(card);
  });
});