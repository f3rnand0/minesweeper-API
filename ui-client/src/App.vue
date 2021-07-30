<template>
  <div id="app">
    <div class="container">
      <transition name="moveInUp">
        <router-view />
      </transition>
    </div>
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import StartGame from "./components/StartGame.vue";

export default {
  name: "App",
  components: {
    StartGame
  }
};
</script>

<style>
@import "./assets/styles/global.css";
</style>
