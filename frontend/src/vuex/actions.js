export default {
    search (context, search_item) {
        // 검색 axios 요청 보내기
        // 코드 작성
        // axios()
        //.then
        // context.commit('ADD_SEARCH_RESULT', res)
        //최근 검색 결과 목록에 추가하기.
        context.commit('ADD_SEARCH_HISTORY', search_item)
    },
    activate_alertcenter ({commit}) {
        commit('ACTIVATE_ALERTCENTER')
    }
}
