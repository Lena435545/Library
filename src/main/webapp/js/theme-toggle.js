document.addEventListener("DOMContentLoaded", () => {
    const themeToggle = document.getElementById('theme-toggle');
    const themeLink = document.getElementById('theme-link');

    const savedTheme = localStorage.getItem('theme') || 'dark';
    applyTheme(savedTheme);

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            const currentTheme = document.body.classList.contains('dark-theme') ? 'dark' : 'light';
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            applyTheme(newTheme);
            localStorage.setItem('theme', newTheme);
        });
    }

    function applyTheme(theme) {
        document.body.classList.remove('light-theme', 'dark-theme');
        document.body.classList.add(`${theme}-theme`);

        themeLink.href = `/css/theme-${theme}.css`;

        document.querySelectorAll('img.default-placeholder').forEach(img => {
            img.src = theme === 'dark'
                ? '/images/default-dark.png'
                : '/images/default-light.png';
        });
    }
});
