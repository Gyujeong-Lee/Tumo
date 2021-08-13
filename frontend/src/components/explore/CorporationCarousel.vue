<template>
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
          <p>SAMSUNG ELECTRONICS CO,.LTD</p>
        </div>
      </div>
      <div>
        <div class="my-2"></div>
        <a href="#">www.sec.co.kr</a>
        <p>경기도 수원시 영통구 삼성로 129 (매탄동)</p>
        <p>Tel: 031-200-1114</p>
      </div>
    </vue-glide-slide>
    <vue-glide-slide> 
      <div class="d-flex flex-column align-items-center">
        <img src="@/assets/explore/kakao.png" alt="kakao">
        <div>
          <h4>(주)카카오</h4>
          <p>Kakao Corp.</p>
        </div>
      </div>
      <div>
        <div class="my-2"></div>
        <a href="#">www.kakaocorp.com</a>
        <p>제주특별자치도 제주시 첨단로 242</p>
        <p>Tel: 02-6718-1082</p>
      </div>
    </vue-glide-slide>
    <vue-glide-slide> 
      <div class="d-flex flex-column align-items-center">
        <img src="@/assets/explore/naver.png" alt="naver">
        <div>
          <h4>네이버(주)</h4>
          <p>NAVER Corporation</p>
        </div>
      </div>
      <div>
        <div class="my-2"></div>
        <a href="#">www.navercorp.com</a>
        <p>경기도 성남시 분당구 불정로 6</p>
        <p>Tel: 031-784-4175</p>
      </div>
    </vue-glide-slide>
    <vue-glide-slide> 
      <div class="d-flex flex-column align-items-center">
        <img src="@/assets/explore/hynix.png" alt="sk">
        <div>
          <h4>SK하이닉스(주)</h4>
          <p>SK hynix Inc.</p>
        </div>
      </div>
      <div>
        <div class="my-2"></div>
        <a href="#">www.skhynix.com</a>
        <p>경기도 이천시 부발읍 경충대로 2091</p>
        <p>Tel: 031-630-4114</p>
      </div>
    </vue-glide-slide>
    <vue-glide-slide> 
      <div class="d-flex flex-column align-items-center">
        <img src="@/assets/explore/mobis.png" alt="mobis">
        <div>
          <h4>현대모비스(주)</h4>
          <p>HYUNDAI MOBIS CO.,LTD</p>
        </div>
      </div>
      <div>
        <div class="my-2"></div>
        <a href="#">www.mobis.co.kr</a>
        <p>서울 강남구 역삼1동 로담코빌딩 140-2</p>
        <p>Tel: 02-2018-5114</p>
      </div>
    </vue-glide-slide>
    <template slot="control">
      <button data-glide-dir="<"><img src="@/assets/explore/left.png" alt="prev"></button>
      <button data-glide-dir=">"><img src="@/assets/explore/right.png" alt="next"></button>
    </template>
  </vue-glide>
</template>

<script>
import ColorThief from 'colorthief'

export default {
  name: 'CorporationCarousel',
  data: function () {
    return {
      active: 0,
      options: {
        type: "carousel",
        perView: 3,
        gap: 10,
        dragThreshold: false,
        focusAt: "center",
        breakpoints: {
          576: {
            perView: 1
          },
          940: {
            perView: 2
          }
        }
      }
    }
  },
  methods: {
    moveCarousel: function () {
      const active = document.querySelector('.glide__slide--active')
      active.classList.remove('prev_slide', 'next_slide', 'is_active')
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
    },
    getColor: function (img) {
      const colorThief = new ColorThief()
      return colorThief.getColor(img)
    }
  },
  mounted: function () {
    setTimeout(() => {
      const slides = document.querySelectorAll('.glide__slide')
      slides.forEach(slide => {
        const logo = slide.querySelector('img')
        const title = slide.querySelector('h4')
        const color = this.getColor(logo)
        const rgb = `${color[0]}, ${color[1]}, ${color[2]}`
        const subtitle = slide.firstChild.lastChild.lastChild
        
        slide.style.boxShadow = `inset 0px 0px 15px 0px rgba(${rgb}, 0.2)`
        slide.style.border = `2px solid rgba(${rgb}, 0.3)`
        subtitle.style.color = `rgba(${rgb}, 0.7)`
        title.style.color = `rgb(${rgb})`
        slide.style.backgroundColor = `rgba(${rgb}, 0.1)`
      })
    }, 800);
  }
}
</script>

<style>
#corp-glide {
  width: 940px;
  margin: auto;
  position: relative;
  transition: 0.5s;
}

#corp-glide > div:last-child {
  width: 100%;
  position: absolute;
  display: flex;
  justify-content: space-between;
  top: 85%;
}

#corp-glide .glide__slides {
  display: flex;
  align-items: center;
  height: 400px;
  padding: 0;
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
}

#corp-glide .glide__slide div {
  width: 100%;
}

#corp-glide .glide__slide img {
  transition: 0.5s;
  width: 200px;
}

#corp-glide .glide__slide p,
#corp-glide .glide__slide h4 {
  margin: 0;
  font-family: 'Nanum Gothic';
  font-weight: 700;
}

#corp-glide .glide__slide p {
  font-size: 12px;
}

#corp-glide .glide__slide--active {
  height: 330px;
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

@media screen and (max-width: 940px) {
  #corp-glide {
    width: 576px;
  }

  #corp-glide > div:last-child {
    top: 95%;
  }
}

@media screen and (max-width: 576px) {
  #corp-glide {
    width: 80%;
    min-width: 280px;
    max-width: 330px;
  }
}
</style>