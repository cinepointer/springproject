function loadContent(fragmentName) {
    fetch('/info/' + fragmentName)
        .then(res => res.text())
        .then(html => {
            document.getElementById('mainContent').innerHTML = html;
        })
        .catch(err => console.error('Fragment load error:', err));
}

document.addEventListener('DOMContentLoaded', function() {
    const btn = document.getElementById('btnTogglePassword');
    const pwdFields = document.getElementById('passwordFields');

    btn.addEventListener('click', function() {
      if (pwdFields.style.display === 'none' || pwdFields.style.display === '') {
        pwdFields.style.display = 'block';
        btn.textContent = '비밀번호 변경 취소';
      } else {
        pwdFields.style.display = 'none';
        btn.textContent = '비밀번호 변경하기';
        pwdFields.querySelectorAll('input').forEach(input => input.value = '');
      }
    });
  });
