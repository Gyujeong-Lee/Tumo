<template>
  <div class="h-100">
    <div class="d-flex justify-content-center h-100">
      <v-sheet 
        elevation="4"
        rounded
        id="articleDetail"
      >
        <div>
          <Comments :data="commentData"/>
        </div>
      </v-sheet>
      <SubFeed/>
    </div>
  </div>
</template>

<script>
import SubFeed from '@/components/main/SubFeed'
import Comments from '@/components/comment/Comments'

export default {
  name: 'ArticleDetail',
  components: {
    SubFeed,
    Comments,
  },
  data: function () {
    return {
      commentData: [],
    }
  }
}
</script>

<style>
#articleDetail {
  width: 614px;
  padding: 2rem;
  margin-top: 3rem;
  margin-bottom: 1rem;
}

</style>