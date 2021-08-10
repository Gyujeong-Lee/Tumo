<template>
  <div id="loading">
    <img src="@/assets/loading/t.png" alt="t" class="t">
    <img src="@/assets/loading/u.png" alt="u" class="u">
    <img src="@/assets/loading/m.png" alt="m" class="m">
    <img src="@/assets/loading/o.png" alt="o" class="o">
  </div>
</template>

<script>
export default {
  name: 'Loading',
}
</script>

<style>
#loading {
  position: relative;
  transform: translateX(-25px) translateY(50px);
}

#loading img {
  position: absolute;
  animation: jumping 3s ease-in-out 0s infinite;
}

#loading img:nth-child(1) {
  left: 0;  
}

#loading img:nth-child(2) {
  left: 85px;
  animation-delay: 0.3s;
}

#loading img:nth-child(3) {
  left: 155px;
  animation-delay: 0.6s;
}

#loading img:nth-child(4) {
  left: 280px;
  animation-delay: 0.9s;
}

@keyframes jumping {
  0% {
    transform: translateY(0px);
  }
  15% {
    transform: translateY(-30px);
  }
  30% {
    transform: translateY(0px);
  }
}
</style>