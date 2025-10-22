document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('register-form');
    const msg = document.getElementById('msg');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const payload = {
            username: document.getElementById('username').value,
            pass: document.getElementById('password').value,
            email: document.getElementById('email').value
        };

        const res = await fetch('/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            msg.textContent = 'Registered successfully! You can now login.';
            msg.style.color = 'green';
            form.reset();
        } else {
            msg.textContent = 'Registration failed. Try again.';
            msg.style.color = 'red';
        }
    });
});
