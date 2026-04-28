import axios from 'axios';
import toast from 'react-hot-toast';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '',
});

// Request interceptor – attach JWT token and ensure /api prefix
api.interceptors.request.use(
  (config) => {
    // Prepend /api if it's missing from the URL and it's a relative path
    if (config.url && !config.url.startsWith('/api') && !config.url.startsWith('http')) {
      config.url = `/api${config.url.startsWith('/') ? '' : '/'}${config.url}`;
    }

    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor – handle auth failures gracefully
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const { status } = error.response;
      const data = error.response.data;
      const message = data.message || data.error || 'Unauthorized request';
      
      if (status === 401 || status === 403) {
        if (localStorage.getItem('token')) {
          toast.error('Session expired or access denied. Please login again.');
          localStorage.removeItem('token');
          localStorage.removeItem('user');
        }
      }
    }
    return Promise.reject(error);
  }
);

export default api;

