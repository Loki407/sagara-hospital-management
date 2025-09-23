// Universal dark mode functionality
class ThemeManager {
    constructor() {
        this.init();
    }
    
    init() {
        this.addDarkModeStyles();
        this.addThemeToggle();
        this.loadSavedTheme();
    }
    
    addThemeToggle() {
        const toggle = document.createElement('button');
        toggle.id = 'themeToggle';
        toggle.className = 'theme-toggle';
        toggle.innerHTML = '🌙';
        toggle.onclick = () => this.toggleTheme();
        document.body.appendChild(toggle);
    }
    
    addDarkModeStyles() {
        const style = document.createElement('style');
        style.textContent = `
            .theme-toggle {
                position: fixed;
                bottom: 20px;
                right: 20px;
                background: rgba(255, 255, 255, 0.2);
                border: none;
                border-radius: 50%;
                width: 50px;
                height: 50px;
                cursor: pointer;
                font-size: 1.5rem;
                z-index: 1000;
                transition: all 0.3s ease;
            }
            .theme-toggle:hover {
                background: rgba(255, 255, 255, 0.3);
                transform: scale(1.1);
            }
            body.dark {
                background: #1a1a2e !important;
                color: #e0e0e0 !important;
            }
            body.dark .navbar {
                background: #16213e !important;
            }
            body.dark .card, body.dark .login-card, body.dark .doctor-card, body.dark .appointment-card {
                background: #2a2a2a !important;
                color: #fff !important;
            }
            body.dark .form-input, body.dark .form-select {
                background: #333 !important;
                border-color: #555 !important;
                color: #fff !important;
            }
            body.dark .doctor-item, body.dark .appointment-item {
                background: #333 !important;
                color: #fff !important;
            }
            body.dark h1, body.dark h2, body.dark h3, body.dark h4 {
                color: #fff !important;
            }
            body.dark .doctor-name {
                color: #fff !important;
            }
            body.dark .doctor-specialty {
                color: #64b5f6 !important;
            }
            body.dark .doctor-email {
                color: #b0b0b0 !important;
            }
            body.dark p {
                color: #d0d0d0 !important;
            }
            body.dark label {
                color: #d0d0d0 !important;
            }
            body.dark .navbar a {
                color: #e0e0e0 !important;
            }
            body.dark .action-card {
                background: #333 !important;
                color: #e0e0e0 !important;
            }
            body.dark .action-icon {
                filter: brightness(1.2);
            }
            body.dark .appointment-card {
                background: #2a2a2a !important;
                border-left-color: #64b5f6 !important;
            }
            body.dark .status-badge {
                background: #1e3a5f !important;
                color: #64b5f6 !important;
            }
            body.dark .welcome-title {
                color: #64b5f6 !important;
            }
            body.dark .welcome-subtitle {
                color: #b0b0b0 !important;
            }
            body.dark .card h4 {
                color: #e0e0e0 !important;
            }
            body.dark .card p {
                color: #d0d0d0 !important;
            }
            body.dark .card div[style*="background: #f8f9fa"] {
                background: #2a2a2a !important;
            }
            
            /* Light mode navbar text visibility */
            .navbar h2 {
                color: white !important;
                text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
            }
            .navbar a {
                color: white !important;
                text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
            }
            #navProfileName {
                color: white !important;
                text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
            }
            
            /* Book Appointment Page Dark Mode */
            body.dark .hero-title {
                color: #64b5f6 !important;
            }
            body.dark .hero-subtitle {
                color: #b0b0b0 !important;
            }
            body.dark .section-title {
                color: #e0e0e0 !important;
                border-bottom-color: #64b5f6 !important;
            }
            body.dark .form-group label {
                color: #e0e0e0 !important;
            }
            body.dark .form-group input,
            body.dark .form-group select {
                background: #2a2a2a !important;
                color: #e0e0e0 !important;
                border-color: #555 !important;
            }
            body.dark .form-group input:focus,
            body.dark .form-group select:focus {
                border-color: #64b5f6 !important;
                box-shadow: 0 0 0 3px rgba(100,181,246,0.1) !important;
            }
            body.dark .card, body.dark .doctor-card, body.dark .appointment-card {
                color: #e0e0e0 !important;
            }
        `;
        document.head.appendChild(style);
    }
    
    loadSavedTheme() {
        if (localStorage.getItem('darkMode') === 'true') {
            document.body.classList.add('dark');
            const toggle = document.getElementById('themeToggle');
            if (toggle) toggle.innerHTML = '☀️';
        }
    }
    
    toggleTheme() {
        const body = document.body;
        const toggle = document.getElementById('themeToggle');
        
        body.classList.toggle('dark');
        const isDark = body.classList.contains('dark');
        toggle.innerHTML = isDark ? '☀️' : '🌙';
        localStorage.setItem('darkMode', isDark);
    }
}

// Initialize theme manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ThemeManager();
});