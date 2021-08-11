const axios = require('axios');

module.exports = {
  /**
   * Calls a GET endpoint of minesweeper-api
   * @param {*} url - Url to execute
   * @returns 
   */
  async callApiGet(url) {
    return await axios.get(url);
  },

  /**
   * Calls a GET endpoint with params included of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} params - Request parameters
   * @returns 
   */
   async callApiGetWithParams(url, params) {
    return axios.get(url, { params })
  },

  /**
   * Calls a POST endpoint of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} json - Request json object 
   * @returns 
   */
  async callApiPost(url, json) {
    return axios.post(url, json);
  },

  /**
   * Calls a PUT endpoint of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} json - Request json object 
   * @returns 
   */
   async callApiPut(url, json) {
    return axios.put(url, json);
  },

  /**
   * Calls a PATCH endpoint of minesweeper-api
   * @param {*} url - Url to execute
   * @param {*} json - Request json object 
   * @returns 
   */
   async callApiPatch(url, json) {
    return axios.patch(url, json);
  },
}