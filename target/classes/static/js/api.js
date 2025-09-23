// API utility functions
class API {
    static BASE_URL = '';
    
    static async request(endpoint, options = {}) {
        const url = `${this.BASE_URL}${endpoint}`;
        const config = {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        };
        
        try {
            const response = await fetch(url, config);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }
    
    static async get(endpoint) {
        return this.request(endpoint);
    }
    
    static async post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }
    
    static async put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }
    
    static async delete(endpoint) {
        return this.request(endpoint, {
            method: 'DELETE'
        });
    }
}

// Doctor API
class DoctorAPI {
    static async getAll() {
        return API.get('/api/doctors');
    }
    
    static async getById(id) {
        return API.get(`/api/doctors/${id}`);
    }
    
    static async create(doctor) {
        return API.post('/api/doctors', doctor);
    }
    
    static async update(id, doctor) {
        return API.put(`/api/doctors/${id}`, doctor);
    }
    
    static async delete(id) {
        return API.delete(`/api/doctors/${id}`);
    }
}

// Appointment API
class AppointmentAPI {
    static async getAll() {
        return API.get('/api/appointments');
    }
    
    static async create(appointment) {
        return API.post('/api/appointments', appointment);
    }
    
    static async getByPatient(patientId) {
        return API.get(`/api/patients/${patientId}/appointments`);
    }
}

// Utility functions
class Utils {
    static showMessage(message, type = 'success') {
        const messageDiv = document.createElement('div');
        messageDiv.className = `message message-${type}`;
        messageDiv.textContent = message;
        
        const container = document.querySelector('.dashboard') || document.body;
        container.insertBefore(messageDiv, container.firstChild);
        
        setTimeout(() => {
            messageDiv.remove();
        }, 5000);
    }
    
    static formatDate(dateString) {
        return new Date(dateString).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    }
    
    static formatTime(timeString) {
        return new Date(`2000-01-01T${timeString}`).toLocaleTimeString('en-US', {
            hour: 'numeric',
            minute: '2-digit',
            hour12: true
        });
    }
    
    static setLoading(elementId, isLoading = true) {
        const element = document.getElementById(elementId);
        if (element) {
            element.innerHTML = isLoading ? '<div class="loading">Loading...</div>' : '';
        }
    }
}