const prompt = require("prompt-sync")();

module.exports = {
  readGameParams() {
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
  },

  readSelectedAction() {
    let option = "";
    while (!Number.isInteger(option) || (option < 1 || option > 2)) {
      console.log("Please select the following options:");
      console.log("1. Select a cell to uncover it");
      console.log("2. Flag a cell");
      const optionRead = prompt("Indicate your selection: ");
      option = Number.parseInt(optionRead);
      console.log("Option: " + option);
    }
    return option;
  },

  readSelectedCell(rows, columns) {
    let row = "row";
    let column = "column";
    while (!Number.isInteger(row) || !Number.isInteger(column) || (row < 0 || row >= rows) || (column < 0 || column >= columns)) {
      console.log("To select a cell please indicate a row and column separated by comma (i.e. 0,1)");
      const cellText = prompt("Beginning from 0, what cell do you select? ");
      let cell = cellText.split(",");
      row = parseInt(cell[0]);
      column = parseInt(cell[1]);
      console.log("Cell row: " + row);
      console.log("Cell column: " + column);
    }
    return { row: row, column: column };
  },

  transformJsonToTable(cells, rows, columns) {
    let table = [];
    let index = 0;
    for (let i = 0; i < rows; i++) {
      item = {};
      for (let j = 0; j < columns; j++) {
        let cell = cells[index];
        let column = j.toString();
        if (cell.visible)
          item[column] = cell.state;
        else
          if (cell.flagged)
            item[column] = 'flag'
          else
            item[column] = 'hidden';
        index++;
      }
      table.push(item);
    }
    return table;
  }
}

