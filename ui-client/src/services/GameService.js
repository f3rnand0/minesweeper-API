import axios from 'axios';
import config from "../../static/config.json";

const apiInfo = config.apiInfo;
const addGameEndpoint = apiInfo.API_HOST + ":" + apiInfo.API_PORT + apiInfo.API_ROOT + apiInfo.GAME_ADD;
const modifyGameEndpoint = apiInfo.API_HOST + ":" + apiInfo.API_PORT + apiInfo.API_ROOT + apiInfo.GAME_MODIFY;

export async function addGame(data) {
    return await axios.post(addGameEndpoint, data);
}

export async function modifyGame(data) {
    return await axios.post(modifyGameEndpoint, data);
}