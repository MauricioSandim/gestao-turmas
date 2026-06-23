import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // Porta onde o Java está rodando
});

// Injeta o token em todas as requisições (como na tela de Turmas)
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;