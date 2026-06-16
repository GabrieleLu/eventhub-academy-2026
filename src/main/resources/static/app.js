// Funzioni comuni per tutte le pagine
document.addEventListener('DOMContentLoaded', () => {
    updateNavbar();
});

// Fetch wrapper con autenticazione
async function fetchAPI(endpoint, options = {}) {
    const headers = options.headers || {};
    
    if (isAuthenticated()) {
        headers['Authorization'] = getAuthHeader();
    }

    const response = await fetch(API_BASE + endpoint, {
        ...options,
        headers: headers
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Errore API');
    }

    return response.json();
}