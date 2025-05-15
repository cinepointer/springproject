function showEditForm(commentNum) {
    const form = document.getElementById('editForm-' + commentNum);
    if (form) {
        form.classList.toggle('show');
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const cancelButtons = document.querySelectorAll('.cancel-edit');
    cancelButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const formDiv = button.closest('.edit-form');
            formDiv.classList.remove('show');
        });
    });
});
