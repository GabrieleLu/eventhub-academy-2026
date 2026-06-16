document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    const errorDiv = document.getElementById('loginError');

    try {
        const response = await fetch(API_BASE + '/user/me', {
            headers: {
                'Authorization': 'Basic ' + btoa(email + ':' + password)
            }
        });

        if (response.ok) {
            login(email, password);
            window.location.href = 'index.html';
        } else {
            errorDiv.textContent = 'Email o password non validi';
            errorDiv.style.display = 'block';
        }
    } catch (error) {
        errorDiv.textContent = 'Errore di connessione al server';
        errorDiv.style.display = 'block';
    }
});