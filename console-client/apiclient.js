const axios = require('axios');

module.exports = {
  async callApiGet(url) {
    return await axios.get(url);
  },

  async callApiPost(url, json) {
    return axios.post(url, json);
  },

  async callApiGetWithParams(url, params) {
    return axios.get(url, {
      params
    })
  }
}
