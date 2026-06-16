const API_BASE = 'http://localhost:8080';

// Salva credenziali in sessionStorage
function login(email, password) {
    const credentials = email + ':' + password;
    const encoded = btoa(credentials); // Base64 encoding
    sessionStorage.setItem('authHeader', 'Basic ' + encoded);
    sessionStorage.setItem('userEmail', email);
    updateNavbar();
}

// Logout
function logout() {
    sessionStorage.removeItem('authHeader');
    sessionStorage.removeItem('userEmail');
    sessionStorage.removeItem('userRole');
    updateNavbar();
    window.location.href = 'index.html';
}

// Ottieni header Authorization
function getAuthHeader() {
    return sessionStorage.getItem('authHeader');
}

// Controlla se autenticato
function isAuthenticated() {
    return sessionStorage.getItem('authHeader') !== null;
}

// Aggiorna navbar in base allo stato di autenticazione
function updateNavbar() {
    const loginLink = document.getElementById('loginLink');
    const logoutBtn = document.getElementById('logoutBtn');
    const userEmail = document.getElementById('userEmail');
    const navOrganizer = document.getElementById('navOrganizer');
    const navAdmin = document.getElementById('navAdmin');

    if (isAuthenticated()) {
        const email = sessionStorage.getItem('userEmail');
        if (loginLink) loginLink.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'block';
        if (userEmail) userEmail.textContent = email;

        // Fetch user role da API
        fetch(API_BASE + '/user/me', {
            headers: {
                'Authorization': getAuthHeader()
            }
        })
        .then(r => r.text())
        .then(text => {
            // Estrai il ruolo dalla risposta
            if (text.includes('ORGANIZER')) {
                if (navOrganizer) navOrganizer.style.display = 'block';
                sessionStorage.setItem('userRole', 'ORGANIZER');
            }
            if (text.includes('ADMIN')) {
                if (navAdmin) navAdmin.style.display = 'block';
                sessionStorage.setItem('userRole', 'ADMIN');
            }
        })
        .catch(err => console.error('Errore:', err));

        if (logoutBtn) {
            logoutBtn.addEventListener('click', logout);
        }
    } else {
        if (loginLink) loginLink.style.display = 'block';
        if (logoutBtn) logoutBtn.style.display = 'none';
        if (userEmail) userEmail.textContent = '';
        if (navOrganizer) navOrganizer.style.display = 'none';
        if (navAdmin) navAdmin.style.display = 'none';
    }
}

// Chiama updateNavbar al caricamento
document.addEventListener('DOMContentLoaded', updateNavbar);