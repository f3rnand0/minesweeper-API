import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { BootstrapVue } from 'bootstrap-vue'


Vue.config.productionTip = false

/*new Vue({
  render: h => h(App),
}).$mount('#app')*/

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

Vue.use(BootstrapVue);
