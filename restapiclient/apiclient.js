const axios = require('axios');

module.exports = {
  async getUser(name) {
    let response = await axios.get('http://localhost:8080/minesweeper-api/user/' + name)
    return response
  },

  async getUserList() {
    let response = await axios.get('http://localhost:8080/minesweeper-api/user/list')
    return response
  },

  async addUser(user) {
    let response = await axios.post('http://localhost:8080/minesweeper-api/user/add', user)
    return response
  }
}
