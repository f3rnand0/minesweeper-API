//import { callApiGet } from './apiclient.js'

const API_ÜRL = "http://localhost:8080/minesweeper-api/";
const USER_GET = API_ÜRL + "user/get";
const USER_GET_LIST = API_ÜRL + "user/list";
const USER_ADD = API_ÜRL + "user/add";
const GAME_ADD = API_ÜRL + "game/add";
const GAME_MODIFY = API_ÜRL + "game/modify";

// Used by index.html
function startGame(name, rows, columns, mines) {
  document.getElelementByID("name").value = name;
  document.getElelementByID("rows").value = rows;
  document.getElelementByID("columns").value = columns;
  document.getElelementByID("mines").value = mines;
  document.index.submit();
}

function setupGame() {
  loadBoardVariables();
  let user = { name: info.name }
  //let response = callApiPost(USER_ADD, user);
  //console.log(response)
}

// Used by game.html
function loadBoardVariables(){
  // Load variables to generate board
  let info = getUrlVars();
  document.getElementById('name').value = info.name;
  document.getElementById('rows').value = info.rows;
  document.getElementById('columns').value = info.columns;
  document.getElementById('mines').value = info.mines;
  
  /*let canvas = document.getElementById('gameCanvas')
  let ctx = canvas.getContext('2d')
  document.addEventListener('DOMContentLoaded', init)
  drawGrid(800, 600, canvas, ctx, "40px");
  //drawGrid(canvas);
  canvas.addEventListener('click', function(evt) {
    var mousePos = getMousePos(canvas, evt);
    var gridLocation = getGridLocation(mousePos.x, mousePos.y, 16);
    alert("Row: " + gridLocation.row + " Column: " + gridLocation.column);
  }, false);*/
}

function getUrlVars() {
  var vars = {};
  var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
      vars[key] = value;
  });
  return vars;
}
