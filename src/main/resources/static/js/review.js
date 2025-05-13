function toggleEditForm(commentId) {
    var form = document.getElementById('edit-form-' + commentId);
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

function deleteReview(reviewNum, movieNum) {
    if (!reviewNum || !movieNum) {
        alert('리뷰 정보를 불러오지 못했습니다.');
        return;
    }
    if (confirm('정말 삭제하시겠습니까?')) {
        console.log('Deleting review: reviewNum=' + reviewNum + ', movieNum=' + movieNum);
        window.location.href = '/review/delete?reviewNum=' + reviewNum + '&movieNum=' + movieNum;
    }
}