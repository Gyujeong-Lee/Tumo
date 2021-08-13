import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./vuex/store";
import vuetify from "./plugins/vuetify";
import VueWordCloud from "vuewordcloud";
import VueSimpleAlert from "vue-simple-alert";
import { Popconfirm, Message } from "element-ui";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import VueGlide from "vue-glide-js";

import "remixicon/fonts/remixicon.css";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "element-ui/lib/theme-chalk/index.css";
import "vue-glide-js/dist/vue-glide.css";

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(Popconfirm);
Vue.use(VueSimpleAlert);
Vue.use(VueGlide);
Vue.filter("money", (value) => {
  return value.toLocaleString("ko-KR");
});

Vue.component(VueWordCloud.name, VueWordCloud);

Vue.prototype.$message = Message;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
