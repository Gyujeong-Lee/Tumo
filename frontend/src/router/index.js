import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/user/Login.vue";
import Signup from "../views/user/Signup.vue";
import Profile from "../views/user/Profile.vue";
import UpdateInfo from "../views/user/UpdateInfo.vue";
import ArticleDetail from "../views/article/ArticleDetail.vue";
import Main from "../views/Main.vue";
import SearchResult from "../views/SearchResult.vue";

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
    path: "/user/update",
    name: "update",
    component: UpdateInfo,
  },
  {
    path: "/user/:nickname",
    name: "profile",
    component: Profile,
    props: true,
  },
  {
    path: "/search/:keyword",
    name: "search",
    component: SearchResult,
  },
  {
    path: "/article/:boardIdx",
    name: "articleDetail",
    component: ArticleDetail,
    props: true,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
