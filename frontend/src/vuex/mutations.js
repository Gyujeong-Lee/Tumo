export default {
    ADD_SEARCH_HISTORY (state, search_item) {
        // 같은 검색어는 추가 x
        if (!state.search_history.includes(search_item)) {
            state.search_history.unshift(search_item)
        }
        // 10개 이상이면 삭제
        if (state.search_history.length > 10) {
            state.search_history.pop()
            console.log(state.search_history)
        }
    },
    // ADD_SEARCH_RESULT (state, res) {
    //     state.search_result.push(res)
    // }
}