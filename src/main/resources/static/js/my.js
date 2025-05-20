function loadContent(fragmentName) {
    fetch('/info/' + fragmentName)
        .then(res => res.text())
        .then(html => {
            document.getElementById('mainContent').innerHTML = html;
        })
        .catch(err => console.error('Fragment load error:', err));
}
