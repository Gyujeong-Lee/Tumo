<template>
  <div id="comments">
    <hr>
    <div v-for="(data, idx) in commentData" :key="idx">
      <div class="d-flex align-items-center mb-1">
        <img src="@/assets/main/user.png" alt="user" style="width: 1em;">
        <span class="px-2 fw-bold">{{ data.nickName }}</span>
      </div>
      <p>{{ data.content }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Comments',
  props: {
    data: {
      type: Array
    }
  },
  data: function () {
    return {
      commentData: this.data
    }
  }
}
</script>

<style>
#comments {
  padding-left: 8%;
  padding-right: 8%;
}
</style>