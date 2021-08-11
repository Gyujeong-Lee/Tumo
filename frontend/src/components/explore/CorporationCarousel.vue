<template>
  <div>
    <vue-glide 
      id="corp-glide"
      v-model="active" 
      :options="options"
      @glide:run-after="moveCarousel"
    >
      <vue-glide-slide> 
        <div class="d-flex flex-column align-items-center">
          <img src="@/assets/explore/samsung.png" alt="samsung">
          <div>
            <h4>삼성전자(주)</h4>
            <p style="font-size: 12px;">SAMSUNG ELECTRONICS CO,.LTD</p>
          </div>
        </div>
        <div>
          <div class="my-2"></div>
          <a href="#">www.sec.co.kr</a>
          <p style="font-size: 14px;">경기도 수원시 영통구 삼성로 129 (매탄동)</p>
          <p style="font-size: 14px;">Tel: 031-200-1114</p>
        </div>
      </vue-glide-slide>
      <vue-glide-slide> 
        <div class="d-flex flex-column align-items-center">
          <img src="@/assets/explore/kakao.png" alt="kakao">
          <div>
            <h4>(주)카카오</h4>
            <p style="font-size: 12px;">Kakao Corp.</p>
          </div>
        </div>
        <div>
          <div class="my-2"></div>
          <a href="#">www.kakaocorp.com</a>
          <p style="font-size: 14px;">제주특별자치도 제주시 첨단로 242</p>
          <p style="font-size: 14px;">Tel: 02-6718-1082</p>
        </div>
      </vue-glide-slide>
      <vue-glide-slide> 
        <div class="d-flex flex-column align-items-center">
          <img src="@/assets/explore/naver.png" alt="naver">
          <div>
            <h4>네이버(주)</h4>
            <p style="font-size: 12px;">NAVER Corporation</p>
          </div>
        </div>
        <div>
          <div class="my-2"></div>
          <a href="#">www.navercorp.com</a>
          <p style="font-size: 14px;">경기도 성남시 분당구 불정로 6</p>
          <p style="font-size: 14px;">Tel: 031-784-4175</p>
        </div>
      </vue-glide-slide>
      <template slot="control">
        <button data-glide-dir="<">prev</button>
        <button data-glide-dir=">">next</button>
      </template>
    </vue-glide>
  </div>
</template>

<script>
export default {
  name: 'CorporationCarousel',
  data: function () {
    return {
      active: 0,
      options: {
        type: "carousel",
        perView: 3,
        gap: 0,
        dragThreshold: false,
        focusAt: "center"
      }
    }
  },
  methods: {
    moveCarousel: function () {
      const active = document.querySelector('.glide__slide--active')
      active.classList.remove('prev_slide', 'next_slide', 'is_active')
      console.log(active.querySelector('img'))
      const next = active.nextElementSibling
      const next2 = next.nextElementSibling
      this.nextSlide(next, next2)
      const prev = active.previousElementSibling
      const prev2 = prev.previousElementSibling
      this.prevSlide(prev, prev2)
    },
    nextSlide: function (n1, n2) {
      n1.classList.remove('prev_slide')
      n2.classList.remove('prev_slide')
      n1.classList.add('next_slide', 'is_active')
      n2.classList.add('next_slide', 'is_active')
    },
    prevSlide: function (n1, n2) {
      n1.classList.remove('next_slide')
      n2.classList.remove('next_slide')
      n1.classList.add('prev_slide', 'is_active')
      n2.classList.add('prev_slide', 'is_active')
      
      }
  }
}
</script>

<style>
#corp-glide .glide__slides {
  display: flex;
  align-items: center;
  transform-style: preserve-3d;
  height: 400px;
}

#corp-glide .glide__slide {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 2px solid #ccc;
  height: 200px;
  padding: 0rem 1.5rem 1.5rem;
  border-radius: 5px;
  transition: all 0.3s;
  opacity: 0.5;
}

#corp-glide .glide__slide div {
  width: 100%;
}

#corp-glide .glide__slide img {
  width: 200px;
}

#corp-glide .glide__slide p,
#corp-glide .glide__slide h4 {
  margin: 0;
}

#corp-glide .glide__slide--active {
  height: 330px;
  opacity: 1;
}

#corp-glide .is_active > div:last-child {
  display: none;
}

#corp-glide .is_active img {
  width: 150px;
}

.prev_slide {
  transform: perspective(1000px) rotateY(-45deg);
}

.next_slide {
  transform: perspective(1000px) rotateY(45deg);
}
</style>