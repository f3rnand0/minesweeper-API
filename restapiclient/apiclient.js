const axios = require('axios');

module.exports = {
  /**
   * Calls a GET method of minesweeper-api
   * @param {*} url - Url to execute
   * @returns 
   */
  async callApiGet(url) {
    return await axios.get(url);
  },

  /**
   * Calls a POST method of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} json - Request json object 
   * @returns 
   */
  async callApiPost(url, json) {
    return axios.post(url, json);
  },

  /**
   * Calls a POST method of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} params - Request parameters
   * @returns 
   */
  async callApiGetWithParams(url, params) {
    return axios.get(url, {
      params
    })
  }
}