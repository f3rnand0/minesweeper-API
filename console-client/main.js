//import { getUser, getUserList, addUser } from './apiclient.js';
const api = require('./apiclient');
const util = require('./util')

const API_ÜRL = "http://localhost:8080/minesweeper-api/";
const USER_GET = API_ÜRL + "user/";
const USER_GET_LIST = API_ÜRL + "user/list/";
const USER_ADD = API_ÜRL + "user/add/";
const GAME_ADD = API_ÜRL + "game/add/";
const GAME_MODIFY = API_ÜRL + "game/modify/";

async function main() {
  gameParams = util.readGameParams();

  let gameData = await startGame(gameParams)
    .catch(e => {
      console.log('There has been a problem starting the game: ' + e.message);
    });

  let gameId = gameData.gameId;
  let table = gameData.table;
  console.log("Game id: " + gameId);
  console.log("BOARD:");
  console.table(table);

  gameData = await continueGame(gameParams, gameId, "FIRST")
    .catch(e => {
      console.log('There has been a problem continuing the game: ' + e.message);
    });
  let dateStarted = gameData.dateStarted;
  table = gameData.table;
  console.log("Date started: " + dateStarted);
  console.log("BOARD:");
  console.table(table);

  let dateFinished = "";
  let elapsedTime = "";
  let endMessage = "";
  while (endMessage === "") {
    gameData = await continueGame(gameParams, gameId, "LATER")
      .catch(e => {
        console.log('There has been a problem continuing the game: ' + e.message);
      });
    dateFinished = gameData.dateFinished;
    elapsedTime = gameData.elapsedTime;
    endMessage = gameData.endMessage;
    table = gameData.table;
    console.log("BOARD:");
    console.table(table);
  }

  console.log("GAME ENDED: " + endMessage);
  console.log("Date finished: " + dateFinished);
  console.log("Elapsed time: " + elapsedTime);
  process.exit();

}

async function startGame(gameParams) {
  let game = {
    rows: gameParams.rows, columns: gameParams.columns, mines: gameParams.mines, gameTurn: "ZERO",
    user: {
      name: gameParams.name
    }
  }

  let response = await api.callApiPost(GAME_ADD, game)
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let gameId = response.data.id;
  let table = util.transformJsonToTable(cells, gameParams.rows, gameParams.columns)
  return { gameId, table };
}

async function continueGame(gameParams, gameId, gameTurn) {
  let option = util.readSelectedAction();
  let selectedCell = util.readSelectedCell(gameParams.rows, gameParams.columns);


  let game = {}
  if (option === 1) {
    game = {
      id: gameId,
      gameTurn: gameTurn,
      user: {
        name: gameParams.name
      },
      selectedCell
    }
  }
  else if (option === 2) {
    flaggedCell = { row: selectedCell.row, column: selectedCell.column }
    game = {
      id: gameId,
      gameTurn: gameTurn,
      user: {
        name: gameParams.name
      },
      flaggedCell
    }
  }

  let response = await api.callApiPost(GAME_MODIFY, game)
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let dateStarted = response.data.dateStarted;
  let dateFinished = response.data.dateFinished;
  let elapsedTime = response.data.elapsedTime;
  let endMessage = response.data.endMessage;
  let table = util.transformJsonToTable(cells, gameParams.rows, gameParams.columns);
  return { dateStarted, dateFinished, elapsedTime, endMessage, table };
}

main();