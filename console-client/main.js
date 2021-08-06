const api = require("./apiclient");
const util = require("./util");

const API_PORT = "8080";
const API_ÜRL = "http://localhost:" + API_PORT + "/minesweeper-api/";
const USER_GET = API_ÜRL + "user/";
const USER_GET_LIST = API_ÜRL + "user/list/";
const USER_ADD = API_ÜRL + "user/add/";
const GAME_ADD = API_ÜRL + "game/add/";
const GAME_START = API_ÜRL + "game/start/";
const GAME_CONTINUE = API_ÜRL + "game/continue/";
const GAME_FLAG = API_ÜRL + "game/flag/";

/**
 * Main function
 */
async function main() {
  gameParams = util.readGameParams();

  let gameData = await addGame(gameParams).catch((e) => {
    console.log("There has been a problem adding the game: " + e.message);
  });

  let gameId = gameData.gameId;
  let table = gameData.table;
  console.log("Game id: " + gameId);
  console.log("BOARD:");
  console.table(table);

  gameData = await startGame(gameParams, gameId).catch((e) => {
    console.log("There has been a problem starting the game: " + e.message);
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
    let option = util.readSelectedAction();
    if (option === 1)
      gameData = await continueGame(gameParams, gameId).catch((e) => {
        console.log(
          "There has been a problem continuing the game: " + e.message
        );
      });
    else if (option === 2) {
      gameData = await flagCellGame(gameParams, gameId).catch((e) => {
        console.log(
          "There has been a problem flagging a cell in the game: " + e.message
        );
      });
    }
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

/**
 * Adds a game by calling the minesweeper-api "game/add" endpoint
 * @param {*} gameParams Json object with game parameters like name, rows, columns, and mines
 * @returns Json object with game id and board based on specific rows and columns
 */
async function addGame(gameParams) {
  let game = {
    rows: gameParams.rows,
    columns: gameParams.columns,
    mines: gameParams.mines,
    gameTurn: "ZERO",
    user: {
      name: gameParams.name,
    },
  };

  let response = await api.callApiPost(GAME_ADD, game);
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let gameId = response.data.id;
  let table = util.transformJsonToTable(
    cells,
    gameParams.rows,
    gameParams.columns
  );
  return { gameId, table };
}

/**
 * Starts a game by calling the minesweeper-api "game/start" endpoint
 * @param {*} gameParams Json object with game parameters like name, rows, columns, and mines
 * @param {*} gameId Id of the game in progress
 * @returns
 */
async function startGame(gameParams, gameId) {
  let selectedCell = util.readSelectedCell(gameParams.rows, gameParams.columns);

  let game = {
    id: gameId,
    gameTurn: "FIRST",
    user: {
      name: gameParams.name,
    },
    selectedCell,
  };

  let response = await api.callApiPost(GAME_START, game);
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let dateStarted = response.data.dateStarted;
  let dateFinished = response.data.dateFinished;
  let elapsedTime = response.data.elapsedTime;
  let endMessage = response.data.endMessage;
  let table = util.transformJsonToTable(
    cells,
    gameParams.rows,
    gameParams.columns
  );
  return { dateStarted, dateFinished, elapsedTime, endMessage, table };
}

/**
 * Continues a game by calling the minesweeper-api "game/modify" endpoint
 * @param {*} gameParams Json object with game parameters like name, rows, columns, and mines
 * @param {*} gameId Id of the game in progress
 * @returns
 */
async function continueGame(gameParams, gameId) {
  let selectedCell = util.readSelectedCell(gameParams.rows, gameParams.columns);
  let game = {};
  game = {
    id: gameId,
    gameTurn: "LATER",
    user: {
      name: gameParams.name,
    },
    selectedCell,
  };

  let response = await api.callApiPut(GAME_CONTINUE, game);
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let dateStarted = response.data.dateStarted;
  let dateFinished = response.data.dateFinished;
  let elapsedTime = response.data.elapsedTime;
  let endMessage = response.data.endMessage;
  let table = util.transformJsonToTable(
    cells,
    gameParams.rows,
    gameParams.columns
  );
  return { dateStarted, dateFinished, elapsedTime, endMessage, table };
}

/**
 * Flags a cell in the game by calling the minesweeper-api "game/flag" endpoint
 * @param {*} gameParams Json object with game parameters like name, rows, columns, and mines
 * @param {*} gameId Id of the game in progress
 * @returns
 */
async function flagCellGame(gameParams, gameId) {
  let selectedCell = util.readSelectedCell(gameParams.rows, gameParams.columns);
  let game = {};
  flaggedCell = { row: selectedCell.row, column: selectedCell.column };
  game = {
    id: gameId,
    gameTurn: "LATER",
    user: {
      name: gameParams.name,
    },
    flaggedCell,
  };

  let response = await api.callApiPatch(GAME_FLAG, game);
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  let cells = response.data.cells;
  let endMessage = response.data.endMessage;
  let table = util.transformJsonToTable(
    cells,
    gameParams.rows,
    gameParams.columns
  );
  return { endMessage, table };
}

main();
