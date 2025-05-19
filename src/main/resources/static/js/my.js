// /js/my.js

function addToWishlist(btn, movieId) {
    fetch('/movies/' + movieId + '/wish', {
        method: 'POST',
        headers: { 'X-Requested-With': 'XMLHttpRequest' },
        credentials: 'include'
    })
    .then(response => response.text())
    .then(result => {
        if (result === "success") {
            // 버튼 텍스트 및 상태 변경
            btn.innerText = '💖 찜완료';
            btn.disabled = true; // 한 번만 가능하게 하고 싶으면 추가
            btn.classList.add('active'); // 스타일 적용 원할 때
        } else {
            alert('찜하기에 실패했습니다. (로그인 필요 또는 서버 오류)');
        }
    })
    .catch(error => {
        console.error('에러 발생:', error);
        alert('서버 오류가 발생했습니다.');
    });
}
