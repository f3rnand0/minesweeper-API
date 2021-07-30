; (function () {
  
  let canvas, ctx
  let step = 50;

  // draws a grid
  function createGrid(step) {
    // draw a line every *step* pixels

    // our end points
    const width = canvas.width
    const height = canvas.height

    // set our styles
    ctx.save()
    ctx.strokeStyle = 'white' // line colors
    ctx.fillStyle = 'darkgrey' // text color
    ctx.font = '14px Monospace'
    ctx.lineWidth = 5

    // draw vertical from X to Height
    for (let x = 0; x < width + step; x += step) {
      // draw vertical line
      ctx.beginPath()
      ctx.moveTo(x, 0)
      ctx.lineTo(x, height)
      ctx.stroke()
      ctx.fillRect(x, 0, step, height)
      // draw text
      //ctx.fillText(x, x, 12)
    }

    // draw horizontal from Y to Width
    for (let y = 0; y < height + step; y += step) {
      // draw horizontal line
      ctx.beginPath()
      ctx.moveTo(0, y)
      ctx.lineTo(width, y)
      ctx.stroke()

      // draw text
      //ctx.fillText(y, 0, y)
    }

    // restore the styles from before this function was called
    ctx.restore()
  }

  function init() {
    // set our config variables
    canvas = document.getElementById('gameCanvas')
    ctx = canvas.getContext('2d')

    createGrid(step)

    canvas.addEventListener('click', function (evt) {
      var mousePos = getMousePos(canvas, evt);
      var gridLocation = getGridLocation(mousePos.x, mousePos.y, step);
      alert("Row: " + gridLocation.row + " Column: " + gridLocation.column);
    }, false);
  }

  function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();

    return {
      x: evt.clientX - rect.left,
      y: evt.clientY - rect.top
    };
  }

  function getGridLocation(posX, posY, gridsize) {
    var cellRow = Math.floor(posY / gridsize);
    var cellColumn = Math.floor(posX / gridsize);

    return {
      row: cellRow,
      column: cellColumn
    };
  }

  document.addEventListener('DOMContentLoaded', init)
})()