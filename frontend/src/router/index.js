import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/user/Login.vue";
import Signup from "../views/user/Signup.vue";
import Profile from "../views/user/Profile.vue";
import Main from "../views/Main.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/signup",
    name: "signup",
    component: Signup,
  },
  {
    path: "/main",
    name: "main",
    component: Main,
  },
  {
    path: "/user/:userId",
    name: "profile",
    component: Profile,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
