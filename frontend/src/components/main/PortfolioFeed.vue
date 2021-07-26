<template>
  <v-sheet 
    elevation="5"
    rounded="xl"
    class="mx-2 my-5"
    height="auto"
    width="auto">
    <p class="p-3">{{ data }}</p>
  </v-sheet>
</template>

<script>
export default {
  name: 'PortfolioFeed',
  props: {
    data: {
      type: Object
    },
  }
}
</script>

<style>

</style>