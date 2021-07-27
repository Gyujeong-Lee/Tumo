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
  //글 작성 모달
  drawCreateArticle: false,

  // user
  user_info: {
    user_id: '',
    //임시 데이터
    user_nickname: 'gyu',
  }
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
