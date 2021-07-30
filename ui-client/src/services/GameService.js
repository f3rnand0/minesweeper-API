const axios = require('axios');

const API_PORT = "8080";
const API_URL = "http://localhost:"+ API_PORT + "/minesweeper-api/";
const GAME_ADD = API_URL + "game/add/";
const GAME_MODIFY = API_URL + "game/modify/";

export async function addGame(data) {
    return await axios.post(GAME_ADD, data);
}

export async function modifyGame(data) {
    return await axios.post(GAME_MODIFY, data);
}