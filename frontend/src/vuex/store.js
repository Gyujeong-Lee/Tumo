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
  drawUpdateArticle: false,
  drawCreatePortfolio: false,
  drawUpdatePortfolio: false,
  drawUpdateAssets: false,
  drawUpdatePassword: false,
  drawFindPassword: false,
  drawDeleteAccount: false,
  drawFollowerList: false,
  drawFollowingList: false,
  drawEmailAuth: false,
  // user
  user_info: {
    // 로그인 한 유저의 idx
    id: null,
    email: null,
    introduce: null,
    nickname: null,
    disclosure: null,
    tags: [],
  },
  // token
  config: {
    Authorization: null,
  },
  // 게시물 상세 페이지
  selectedArticle: null,
  // 읽지 않은 알림
  unreadAlert: null,
  // 포트폴리오 상세 페이지
  selectedPortfolio: null,
  portfolioAssets: null,
  portfolioAmount: null,
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
