export default {
  search(context, search_item) {
    context.commit("ADD_SEARCH_HISTORY", search_item);
  },
  activate_alertcenter({ commit }) {
    commit("ACTIVATE_ALERTCENTER");
  },
  confirmStockInfo(context, tempInfo) {
    context.commit("CONFIRM_STOCK_INFO", tempInfo)
  }
};
