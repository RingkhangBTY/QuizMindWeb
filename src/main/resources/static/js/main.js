async function checkAuth() {
    const res = await fetch('/users', { credentials: 'include' });
    if (res.ok) {
        window.location.href = 'quiz.html';
    } else {
        document.getElementById('auth-options').classList.remove('hidden');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    checkAuth();

    document.getElementById('btn-login').addEventListener('click', () => {
        window.location.href = '/login'; // Spring Security login page
    });

    document.getElementById('btn-register').addEventListener('click', () => {
        window.location.href = 'register.html';
    });
});
