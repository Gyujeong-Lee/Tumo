<template>
  <v-dialog
    persistent
    width="640"
    v-model="isDrawCreateArticle" 
    id="createArticle"
  >
    <v-card>
      <h1 class="text-center mb-5"><v-icon large color="black">mdi-pencil</v-icon> 새 게시물</h1>
      <v-form
        ref="form"
        v-model="valid"
      >
        <div class="d-flex flex-column flex-sm-row justify-content-sm-between">
          <div class="w-100">
            <label for="formTitle">제목</label>
            <v-text-field
              solo
              rounded
              dense
              counter=20
              :rules="titleRules"
              v-model="data.title" 
              id="formTitle"
            ></v-text-field>
          </div>
          <div>
            <label for="formStock">종목</label>
            <v-select
              :items="items"
              dense
              solo
              rounded
              v-model="data.stock"
              id="formStock"
            ></v-select>
          </div>
        </div>
        <div>
          <label for="">내용</label>
          <v-textarea
            solo
            rounded
            dense
            :rules="contentRules"
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
          <v-btn rounded class="me-5" color="error" @click="addTag">추가</v-btn>          
        </div>
        <br>
        <div>
          <v-chip v-for="(tag, idx) in data.tags" :key="idx" label close @click:close="popTag(idx)" class="me-3 mb-3">#{{ tag }}</v-chip>
        </div>
        <br>
        <div class="d-flex justify-content-end">
          <v-btn rounded class="me-5" color="error" @click="closeModal">작성 취소</v-btn>
          <v-btn rounded color="primary" :disabled="!valid" @click="submitForm">작성 완료</v-btn>
        </div>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'CreateArticle',
  data: function () {
    return {
      valid: true,
      items: ['국내주식', '해외주식', '국내채권', '해외채권'],
      inputTag: '',
      titleRules: [
        v => !!v || '제목을 적어주세요.',
        v => (v && v.length <= 20) || '제목이 너무 길어요...',
      ],
      contentRules: [
        v => !!v || '내용을 적어주세요.',
      ],
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
    },
    closeModal: function () {
      this.$store.state.drawCreateArticle = false
    },
    submitForm: function () {
      // axios 요청
      // alert
      this.$store.state.drawCreateArticle = false
    }
  },
  computed: {
    isDrawCreateArticle: function () {
      return this.$store.state.drawCreateArticle
    }
  }
}
</script>

<style>
.v-card {
  padding: 5% 7%;
}

#createArticle form {
  margin-left: 8%;
  margin-right: 8%;
}

@media screen and (min-width: 600px){
  #createArticle form {
    width: 600px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 5%;
  }

  form > div > div:first-child {
    margin-right: 5%;
  }
  
}
</style>