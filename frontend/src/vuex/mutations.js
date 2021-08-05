export default {
  ADD_SEARCH_HISTORY(state, search_item) {
    // 같은 검색어는 추가 x
    if (!state.search_history.includes(search_item)) {
      state.search_history.unshift(search_item);
    }
    // 10개 이상이면 삭제
    if (state.search_history.length > 10) {
      state.search_history.pop();
      console.log(state.search_history);
    }
  },
  // ADD_SEARCH_RESULT (state, res) {
  //     state.search_result.push(res)
  // },

  LOGIN(state, data) {
    // 유저 nickname과 idx 저장
    state.user_info = {
      id: data.userIdx,
      email: data.email,
      disclosure: data.disclosure,
      introduce: data.introduce,
      nickname: data.nickname,
      tags: data.tags,
    };
    // token 저장
    state.config.Authorization = data.token;
  },
  LOGOUT(state) {
    // state 값 초기화
    state.user_info = {
      id: null,
      email: null,
      introduce: null,
      nickname: null,
      disclosure: null,
      tags: [],
    };
    state.config.Authorization = null;
  },
  INFO_UPDATE(state, data) {
    state.user_info = {
      ...state.user_info,
      nickname: data.nickname,
      disclosure: data.disclosure,
      introduce: data.introduce,
      tags: data.tags,
    };
  },
  ACTIVATE_ALERTCENTER(state) {
    state.activate_alertcenter = !state.activate_alertcenter;
    console.log(state.activate_alertcenter);
  },
  CONFIRM_STOCK_INFO(state, data) {
    state.tmpAsset.price = data.price
    state.tmpAsset.quantity = data.quantity
    state.tmpAsset.goal = data.goal
  }
};
