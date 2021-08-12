<template>
  <div id="top-portfolio">
    <div class="explore-portfolio">
      <img src="@/assets/explore/portfolio/silver.png" alt="silver">
      <div>portfolio</div>
    </div>
    <div>
      <div class="explore-portfolio">
        <img src="@/assets/explore/portfolio/gold.png" alt="gold">
        <div>portfolio</div>
      </div>
      <img src="@/assets/explore/portfolio/person.png" alt="person" class="w-100">
    </div>
    <div class="explore-portfolio">
      <img src="@/assets/explore/portfolio/bronze.png" alt="bronze">
      <div>portfolio</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TopPortfolio'
}
</script>

<style>
#top-portfolio {
  width: 940px;
  margin: 10rem auto 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

#top-portfolio > div {
  width: 300px;
}

.explore-portfolio {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.explore-portfolio img {
  width: 80px;
  margin-bottom: 1rem;
}

</style>