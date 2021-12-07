let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include' 
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }
})