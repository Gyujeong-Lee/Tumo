<template>
  <v-list>
    <!-- 팔로우 요청, 좋아요, 댓글, 스크랩 알림 따로 처리 -->
    <v-list-item
      v-for="(item, idx) in items"
      :key="idx"
    >
      <v-list-item-title>{{ item.title }}</v-list-item-title>
    </v-list-item>
  </v-list>
</template>

<script>
export default {
  name: 'AlertCenter',
  data: () => ({
    // vuex로 처리할 것
    items: [
      { title: 'Click Me11111111111111111' },
      { title: 'Click Me' },
      { title: 'Click Me' },
      { title: 'Click Me 2' },
    ],
  }),
}
</script>