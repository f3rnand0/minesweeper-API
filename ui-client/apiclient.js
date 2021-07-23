import axios from 'axios';

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
  },

  async addGame(game) {
    let response = await axios.post('http://localhost:8080/minesweeper-api/game/add', game)
    return response
  },

  async modifyGame(game) {
    let response = await axios.post('http://localhost:8080/minesweeper-api/game/modify', game)
    return response
  },

}

export function callApiGet(url, value) {
  setResponse({ data: null, error: null, loading: true });
  axios.get(url)
    .then((response) => {
      setResponse({
        data: response.data,
        loading: false,
        error: null,
      });
      /*if (onSuccessOverride) {
        onSuccessOverride(response);
      } else {
        onSuccess(response);
      }*/
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error;
    });
}

export function callApiGetWithParams(url, params) {
  setResponse({ data: null, error: null, loading: true });
  axios.get(url, {
    params
  })
    .then((response) => {
      setResponse({
        data: response.data,
        loading: false,
        error: null,
      });
      /*if (onSuccessOverride) {
        onSuccessOverride(response);
      } else {
        onSuccess(response);
      }*/
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error;
    });
}

export function callApiPost(url, value) {
  setResponse({ data: null, error: null, loading: true });
  axios.post(url, json)
    .then((response) => {
      setResponse({
        data: response.data,
        loading: false,
        error: null,
      });
      /*if (onSuccessOverride) {
        onSuccessOverride(response);
      } else {
        onSuccess(response);
      }*/
      return response;
    })
    .catch((error) => {
      console.log(error);
      return error;
    });
}
