// Used by index.html
function startGame(name) {
  document.getElelementByID("name").value=name;
  document.index.submit();
}

// Used by game.html
function createBoard(){
  document.getElementById('name').value = getUrlVars().name;
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

function drawGrid(weight, height, canvas, ctx, spacing) {
  canvas.width  = weight;
  canvas.height = height;

  ctx.beginPath();

  //ctx.strokeStyle = 'rgb(0, 0, 0, 0.35)';
  ctx.strokeStyle = 'lightgrey';
  //ctx.lineWidth = 10;

  for (var x=0; x<=weight; x+=spacing) {
      ctx.moveTo(x, 0);
      ctx.lineTo(x, height);
   }

    for (var y=0;y<=height;y+=spacing) {
        ctx.moveTo(0, y);
        ctx.lineTo(weight, y);
    }
  ctx.stroke();
};

function getMousePos(canvas, evt){
  var rect = canvas.getBoundingClientRect();

  return {
    x: evt.clientX - rect.left,
    y: evt.clientY - rect.top
  };
}

function getGridLocation(posX, posY, gridsize)
{
  var cellRow = Math.floor(posY / gridsize);
  var cellColumn = Math.floor(posX / gridsize);

  return {
    row: cellRow,
    column: cellColumn
 };
}

