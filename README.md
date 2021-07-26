# minesweeper

This is a minesweeper game and set of applications. It is divided into the following:
1.  restapi: a Spring boot rest API that can be used by any front-end application.
  * Open API docs: http://localhost:8080/swagger-ui.html
  * JSON API docs: http://localhost:8080/api-docs/
  * YAML: http://localhost:8080/api-docs.yaml

2.  restapiclient: a nodejs API client library that executes any endpoint exposed by the restapi application.
3.  console-client: a nodejs program that runs on a shell and that simulates the minesweeper game. It uses the restapiclient library to execute the restapi endpoints.
4.  ui-client: a not finished web frontend (HTML5, CSS, and Javascript) that will do the same as the console-client program.

## Features
* A documented RESTful API for the game (Spring boot and Java).
* An API client library for the API developed above (NodeJS).
* Based on the normal minesweeper game:
     - The user initially indicates a name, number of rows, number of columns, and number of mines before starting the game.
     - On the first turn the user selects a cell that can spawn many  empty cells, or numbers surrounding a mine.
     - On the next turns the user can select or flag a cell. If the selected cell is a mine the game ends. Otherwise, many empty cells would be spawn, a numbered cell would be shown or the game will finish (when all the cells (except mines) have been clicked by the user).
* Any visible cell can be flagged or unflagged by indicating the same cell.
* The name of the user, and every turn of the game is being stored on an HSQL in-memory database. Another database vendor can be configured in restapi/src/main/resources/application.properties file.
* The date and time when the game starts and is finished are being stored.
* The amount of time taken to play a game is being stored in "x minutes, y seconds" format.
* The name of the user is asked at the beginning, but also an "anonymous" user can be set at the beginning.

## Installation and usage
### Development requirements (restapi)
* JDK 11 or higher.
* Maven 3.5.3 or higher.
* IDE or your preference.

### Development requirements (restapiclient, console-client, ui-client)
* NodeJS 10.19 or higher.
* IDE or your preference.

### Runtime requirements (restapi)
* JDK 11 or higher.
* Port 8080 available. If you want to change it, will need to do the following:
  1. Modify the "server.port" property in "restapi/src/main/resources/application.properties" file.
  2. Generate a new jar package, and copy it to the runtime folder.
  3. Modify the API_PORT constant in the "console-client/main.js" file, and copy the entire folder to the runtime directory.

### Runtime requirements (restapiclient, console-client, ui-client)
* NodeJS 10.19 or higher.
* Shell or your preference.

### Usage
1.  Clone this repo.
2.  Start a console/shell of your preference.
3.  Go to runtime directory.
4.  Execute "java -jar restapi-1.0.0.jar".
5.  Start another console/shell.
6.  From the runtime directory execute "node main.js". Enjoy!!