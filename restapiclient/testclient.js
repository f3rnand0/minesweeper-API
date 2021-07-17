//import { getUser, getUserList, addUser } from './apiclient.js';
const api = require('./apiclient');

let name1 = "Sample 1";
let name2 = "Sample 2";
let name3 = "Sample 3";
let user1 = { name: name1 };
let user2 = { name: name2 };
let user3 = { name: name3 };
let game = { rows: 3, columns: 3 };

async function testAddUser() {
  let response = await api.addUser(user1);
  console.log("addUser status: " + response.status);
  console.log("addUser data: " + JSON.stringify(response.data));
}

async function testGetUser() {
  let response = await api.getUser(name1);
  console.log("getUser status: " + response.status);
  console.log("getUser data: " + JSON.stringify(response.data));
}

async function testGetUserList() {
  let response = await api.getUserList();
  console.log("getUserList status: " + response.status);
  console.log("getUserList data: " + JSON.stringify(response.data));
}

async function testAddGame() {
  let response = await api.addGame(game);
  console.log("addGame status: " + response.status);
  console.log("addGame data: " + JSON.stringify(response.data));
}

async function main() {
  await Promise.all([
/*    testAddUser(),
    testGetUser(),
    testAddUser(),
    testAddUser(),
    testGetUserList()*/
    testAddGame()
  ])
}

main();