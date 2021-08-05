export default {
  search(context, search_item) {
    context.commit("ADD_SEARCH_HISTORY", search_item);
  },
  activate_alertcenter({ commit }) {
    commit("ACTIVATE_ALERTCENTER");
  },
};
