/**
 * 영화 찜하기 버튼 클릭 시 서버에 요청을 보내는 예시 코드
 * 반드시 credentials: 'include'를 사용하여 세션 쿠키가 전달되게 합니다.
 */

function addToWishlist(movieId) {
	fetch('/wishlist/add/' + movieId, {
	    method: 'POST',
	    headers: { 'X-Requested-With': 'XMLHttpRequest' },
	    credentials: 'include' // << 이 줄 추가!
	})

    .then(response => {
        if (response.status === 401) {
            alert('로그인이 필요합니다!');
            // 로그인 페이지로 이동하거나, 로그인 모달을 띄울 수 있음
        } else if (response.ok) {
            alert('찜하기 완료!');
        } else {
            alert('찜하기에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('에러 발생:', error);
        alert('서버 오류가 발생했습니다.');
    });
}