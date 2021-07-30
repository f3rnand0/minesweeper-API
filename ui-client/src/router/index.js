import Vue from 'vue'
import Router from 'vue-router'
import StartGame from '@/components/StartGame'
//import PlayGame from '@/components/PlayGame'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'StartGame',
      component: StartGame
    }/*,
    {
      path: '/play',
      name: 'PlayGame',
      component: PlayGame
    }*/
  ]
})
