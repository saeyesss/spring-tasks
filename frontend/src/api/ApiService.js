import axios from 'axios';

export default class ApiService {
  static API_URL = 'http://localhost:3030';

  static saveToken(token) {
    localStorage.setItem('token', token);
  }
  static getToken() {
    return localStorage.getItem('token');
  }
  static isAuthenticated() {
    return !!localStorage.getItem('token');
  }
  static logout() {
    localStorage.removeItem('token');
  }
  static getHeader() {
    const token = ApiService.getToken();
    return {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    };
  }

  // register user
  static async register(data) {
    try {
      const response = await axios.post(
        `${ApiService.API_URL}/auth/register`,
        data
      );
      return response.data;
    } catch (error) {
      throw new Error(
        error.response.data.message || 'An error occurred during registration.'
      );
    }
  }

  // login user 
  static async login(data) {
    try {
      const response = await axios.post(`${ApiService.API_URL}/auth/login`, data);
      return response.data;
    } catch (error) {
      throw new Error(
        error.response.data.message || 'An error occurred during login.'
      );
    }
  }

  // create task
  static async createTask(body) {
    const resp = await axios.post(`${this.API_URL}/tasks`, body, {
      headers: this.getHeader()
    });
    return resp.data;
  }


  static async updateTask(body) {
    const resp = await axios.put(`${this.API_URL}/tasks`, body, {
      headers: this.getHeader()
    });
    return resp.data;
  }


  static async getAllMyTasks() {
    const resp = await axios.get(`${this.API_URL}/tasks`, {
      headers: this.getHeader(),
    });
    return resp.data;
  }


  static async getTaskById(taskId) {
    const resp = await axios.get(`${this.API_URL}/tasks/${taskId}`, {
      headers: this.getHeader()
    });
    return resp.data;
  }


  static async deleteTask(taskId) {
    const resp = await axios.delete(`${this.API_URL}/tasks/${taskId}`, {
      headers: this.getHeader()
    });
    return resp.data;
  }


  static async getMyTasksByCompletionStatus(completed) {
    const resp = await axios.get(`${this.API_URL}/tasks/status`, {
      headers: this.getHeader(),
      params: {
        completed: completed
      }
    });
    return resp.data;
  }

  static async getMyTasksByPriority(priority) {
    const resp = await axios.get(`${this.API_URL}/tasks/priority`, {
      headers: this.getHeader(),
      params: {
        priority: priority
      }
    });
    return resp.data;
  }
}
