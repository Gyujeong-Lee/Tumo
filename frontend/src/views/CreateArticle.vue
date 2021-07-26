<template>
  <div id="createArticle">
    <form>
      <div class="d-flex justify-content-between align-items-end">
        <div class="w-100 me-5">
          <label for="formTitle">제목</label>
          <v-text-field
            solo
            rounded
            dense
            v-model="data.title" 
            id="formTitle"
          ></v-text-field>
        </div>
        <v-select
          :items="items"
          dense
          solo
          rounded
          v-model="data.stock"
        ></v-select>
      </div>
      <div>
        <label for="">내용</label>
        <v-textarea
          solo
          rounded
          dense
          v-model="data.content"
          id="formContent"
        ></v-textarea>
      </div>
      <div class="d-flex justify-content-between align-items-end">
        <div class="w-75">
          <label for="">태그</label>
          <v-text-field
            dense
            solo
            rounded
            hide-details
            @keypress.enter="addTag"
            v-model="inputTag"
          ></v-text-field>
        </div>
        <v-btn rounded color="error" @click="addTag">추가</v-btn>
      </div>
      <br>
      <div>
        <v-chip v-for="(tag, idx) in data.tags" :key="idx" label close @click:close="popTag(idx)" class="me-3 mb-3">#{{ tag }}</v-chip>
      </div>
      <br>
      <v-btn block rounded color="primary">작성</v-btn>
    </form>
  </div>
</template>

<script>
export default {
  name: 'CreateArticle',
  data: function () {
    return {
      items: ['국내주식', '해외주식', '국내채권', '해외채권'],
      inputTag: '',
      data: {
        title: '',
        stock: '국내주식',
        content: '',
        tags: [],
      }
    }
  },
  methods: {
    addTag: function () {
      if (this.inputTag.trim()) {
        this.data.tags.push(this.inputTag)
      }
      this.inputTag = ''
    },
    popTag: function (idx) {
      this.data.tags.splice(idx, 1)
    }
  }
}
</script>

<style>
#createArticle form {
  width: 576px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 5%;
}
</style>