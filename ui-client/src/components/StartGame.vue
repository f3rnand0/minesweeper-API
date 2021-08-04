<template>
  <div class="container">
    <div class="row">
      <div class="col-md-7 mrgnbtm">
        <h2>Game parameters</h2>
        <form>
          <div class="row">
            <div class="form-group col-md-12">
              <label htmlFor="name">Name</label>
              <input
                type="text"
                class="form-control"
                v-model="name"
                name="name"
                id="name"
                placeholder="Input your name"
              />
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-12">
              <label htmlFor="rows">Rows</label>
              <input
                type="text"
                class="form-control"
                v-model="rows"
                name="rows"
                id="rows"
                placeholder="Indicate number of rows of the board. Must be greater than 3"
              />
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-12">
              <label htmlFor="columns">Columns</label>
              <input
                type="text"
                class="form-control"
                v-model="columns"
                name="columns"
                id="columns"
                placeholder="Indicate number of columns of the board. Must be greater than 3"
              />
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-12">
              <label htmlFor="mines">Mines</label>
              <input
                type="text"
                class="form-control"
                v-model="mines"
                name="mines"
                id="mines"
                placeholder="Indicate number of mines to be generated. Must be greater than 3"
              />
            </div>
          </div>
          <div class="row">
            <div class="form-group col-md-12">
              <button type="button" @click="startGame()" class="btn btn-primary">
                Start game
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { addGame, modifyGame } from "../services/GameService";

export default {
  name: "StartGame",
  data() {
    return {
      name: "",
      rows: "",
      columns: "",
      mines: ""
    };
  },
  methods: {
    startGame() {
      const game = {
        rows: this.rows,
        columns: this.columns,
        mines: this.mines,
        gameTurn: "ZERO",
        user: {
          name: this.name
        }
      };
      console.log(JSON.stringify(game));

      let response = addGame(GAME_ADD, game);
      if (!response) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      this.clearForm();
      this.$router.push({ path: "/play" });
    },
    clearForm() {
      this.name = "";
      this.rows = "";
      this.columns = "";
      this.mines = "";
    }
  }
};
</script>
