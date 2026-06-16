document.getElementById('signupForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const role = document.getElementById('signupRole').value;
    const errorDiv = document.getElementById('signupError');

    try {
        const response = await fetch(API_BASE + '/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password,
                role: role
            })
        });

        if (response.ok) {
            login(email, password);
            window.location.href = 'index.html';
        } else {
            const error = await response.json();
            errorDiv.textContent = error.message || 'Errore nella registrazione';
            errorDiv.style.display = 'block';
        }
    } catch (error) {
        errorDiv.textContent = 'Errore di connessione al server';
        errorDiv.style.display = 'block';
    }
});