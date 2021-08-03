import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import actions from "./actions";
import mutations from "./mutations";

Vue.use(Vuex);

const state = {
  //최근 검색 목록
  search_history: [],
  //검색 결과
  search_result: [],
  //모달 Flag
  drawCreateArticle: false,
  drawUpdatePassword: false,
  drawFindPassword: false,
  drawFollowerList: false,
  drawFollowingList: false,
  // user
  user_info: {
    // 로그인 한 유저의 idx
    id: null,
    email: null,
    introduce: null,
    nickname: null,
    tags: [],
  },
  // token
  config: {
    Authorization: null,
  },
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
