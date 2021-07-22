//import { getUser, getUserList, addUser } from './apiclient.js';
const api = require('./apiclient');
const prompt = require("prompt-sync")();

const API_ÜRL = "http://localhost:8080/minesweeper-api/";
const USER_GET = API_ÜRL + "user/";
const USER_GET_LIST = API_ÜRL + "user/list/";
const USER_ADD = API_ÜRL + "user/add/";
const GAME_ADD = API_ÜRL + "game/add/";
const GAME_MODIFY = API_ÜRL + "game/modify/";

async function main() {
  gameParams = readGameParams();

  let game = {
    rows: gameParams.rows, columns: gameParams.columns, mines: gameParams.mines, gameTurn: "ZERO",
    user: {
      //name: gameParams.name
      name: "anonymous"
    }
  }

  let data = await startGame(game)
    .catch(e => {
      console.log('There has been a problem starting the game: ' + e.message);
    });

  let cells = data.cells;
  //console.log(JSON.stringify(data.cells));

  let table = [];
  let index = 0;
  for (let i = 0; i < gameParams.rows; i++) {
    for (let j = 0; j < gameParams.columns; j++) {
      let cell = cells[index];
      item = {};
      if (cell.visible)
        item[j.toString()] = cell.state;
      else
        item[j.toString()] = 'empty';
      //item['flagged'] = cell.flagged;
      table.push(item);
      index++;
    }
  }

  console.table(table);

  /*let params = { id: 1 }
  api.callApiGet(USER_GET + "anonymous")
    .then((response) => console.log(response.data));*/


  /*let user = { name: "anonymous1" }
  response = api.callApiPost(USER_ADD, user);
  console.log(response)*/

}

function generateTable() {
  var viewData = {
    cells: []
  };

  onGeneratedRow({ "1":  });

  function onGeneratedRow(columns) {
    var jsonData = {};
    columns.forEach(function (column) {
      var columnName = column.metadata.colName;
      jsonData[columnName] = column.value;
    });
    viewData.cells.push(jsonData);
  }
}

function readGameParams() {
  const name = prompt("What is your name? ");
  console.log("Name: " + name);
  const rows = prompt("Indicate number of rows (greater than 3): ");
  console.log("Rows: " + rows);
  const columns = prompt("Indicate number of columns (greater than 3): ");
  console.log("Columns: " + columns);
  const mines = prompt("Indicate number of mines (greater than 2): ");
  console.log("Mines: " + mines);
  if (rows < 3 || columns < 3 || mines < 2) {
    console.error("Rows, columns or mines are not greater that indicated number");
    process.exit(1);
  }
  return { name: name, rows: rows, columns: columns, mines: mines };
}

async function startGame(game) {
  let response = await api.callApiPost(GAME_ADD, game)
  if (!response) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response.data;
}

main();