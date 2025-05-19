// 영화 정보 수집 함수 (페이지 내 모든 .movie-card, .splide__slide a)
function getAllMovies() {
  const movieCards = document.querySelectorAll('.movie-card, .splide__slide a');
  const movies = [];
  movieCards.forEach(card => {
    const titleEl = card.querySelector('.title');
    const descEl = card.querySelector('.desc');
    if (titleEl && descEl) {
      movies.push({
        title: titleEl.textContent.trim(),
        desc: descEl.textContent.trim(),
        link: card.getAttribute('href')
      });
    }
  });
  return movies;
}

document.addEventListener("DOMContentLoaded", function(){
  const input = document.getElementById('searchInput');
  const resultList = document.getElementById('search-result-list');
  const searchBtn = document.getElementById('searchBtn'); // 검색 버튼 가져오기
  let allMovies = getAllMovies();

  // 검색 및 결과 표시 함수 (input, 버튼에서 모두 사용)
  function searchAndDisplay() {
    const keyword = input.value.trim().toLowerCase();
    resultList.innerHTML = '';
    if (!keyword) {
      resultList.style.display = 'none';
      return;
    }
    const filtered = allMovies.filter(movie =>
      movie.title.toLowerCase().includes(keyword) ||
      movie.desc.toLowerCase().includes(keyword)
    );
    if (filtered.length > 0) {
      filtered.forEach(movie => {
        const li = document.createElement('li');
        li.innerHTML = `<a href="${movie.link}" style="display:block; padding:8px 12px; text-decoration:none; color:#222;">
                          <strong>${movie.title}</strong> <span style="color:#888;">${movie.desc}</span>
                        </a>`;
        resultList.appendChild(li);
      });
    } else {
      const li = document.createElement('li');
      li.textContent = '검색 결과가 없습니다.';
      li.style.padding = '8px 12px';
      resultList.appendChild(li);
    }
    resultList.style.display = 'block';
  }

  input.addEventListener('input', searchAndDisplay);

  // 검색 버튼 클릭 시에도 검색
  if (searchBtn) {
    searchBtn.addEventListener('click', function(e) {
      e.preventDefault();
      searchAndDisplay();
    });
  }

  // 엔터로 submit 시에도 버튼과 동일하게 동작
  const searchForm = document.getElementById('searchForm');
  if (searchForm) {
    searchForm.addEventListener('submit', function(e){
      e.preventDefault();
      searchAndDisplay();
    });
  }

  // 검색 결과 클릭 시 입력창 초기화 및 리스트 숨김
  resultList.addEventListener('click', function(e) {
    if (e.target.tagName === 'A') {
      input.value = '';
      resultList.style.display = 'none';
    }
  });

  // 입력창 외부 클릭 시 검색 결과 숨김
  document.addEventListener('click', function(e) {
    if (!input.contains(e.target) && !resultList.contains(e.target)) {
      resultList.style.display = 'none';
    }
  });
});
