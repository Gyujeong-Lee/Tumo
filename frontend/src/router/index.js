import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/User/Login.vue";
import Signup from "../views/User/Signup.vue";
import Profile from "../views/User/Profile.vue";
import Main from "../views/Main.vue";
import createArticle from "../views/createArticle.vue";

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
    path: "/createArticle",
    name: "createAritlce",
    component: createArticle,
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
