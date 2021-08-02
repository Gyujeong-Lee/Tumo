<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3">{{ scrap }}</p>
  </v-sheet>
</template>

<script>
export default {
  name: 'ScrapList',
  data: function () {
    return {

    }
  },
  props: {
    scrap: {
      type: Object
    }
  }
}
</script>