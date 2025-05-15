document.addEventListener('DOMContentLoaded', () => {
            const editModal = new bootstrap.Modal(document.getElementById('editModal'));
            const modalContent = document.getElementById('editModalContent');

            document.querySelectorAll('.btn-edit').forEach(button => {
                button.addEventListener('click', () => {
                    const userId = button.getAttribute('data-userid');

                    fetch(`/admin/editForm/${userId}`)
                        .then(response => {
                            if (!response.ok) throw new Error('네트워크 오류');
                            return response.text();
                        })
                        .then(html => {
                            modalContent.innerHTML = html;
                            editModal.show();
                        })
                        .catch(err => {
                            alert('사용자 정보를 불러오는데 실패했습니다.');
                            console.error(err);
                        });
                });
            });
        });