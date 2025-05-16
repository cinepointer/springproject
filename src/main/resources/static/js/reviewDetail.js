function toggleEditForm(commentId) {
    var form = document.getElementById('edit-form-' + commentId);
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

function deleteReview(reviewNum, movieNum) {
    if (confirm("정말 삭제하시겠습니까?")) {
        window.location.href = "/review/delete?reviewNum=" + reviewNum + "&movieNum=" + movieNum;
    }
}
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.cancel-edit').forEach(btn => {
        btn.addEventListener('click', function () {
            const form = this.closest('form');
            form.style.display = 'none';
        });
    });
});