const axios = require('axios');

const API_PORT = "8080";
const API_URL = "http://localhost:"+ API_PORT + "/minesweeper-api/";
const USER_GET = API_URL + "user/";
const USER_GET_LIST = API_URL + "user/list/";
const USER_ADD = API_ÜRL + "user/add/";

export async function getAllUsers() {
    return await axios.get(USER_GET_LIST);
}

export async function addUser(data) {
    return await axios.post(USER_ADD, data);
}

export async function getUser(name) {
    return await axios.post(USER_GET + name);
}