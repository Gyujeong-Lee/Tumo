<template>
  <div class="mb-4 p-3 border border-dark">
    {{ data }}
  </div>
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