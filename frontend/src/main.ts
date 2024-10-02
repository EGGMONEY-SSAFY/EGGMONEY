import "@/assets/main.css"
import { createApp } from "vue"
import { createPinia } from "pinia"
import piniaPersistedstate from "pinia-plugin-persistedstate"
import App from "./App.vue"
import router from "./router"
// @ts-ignore
import DOMPurify from 'dompurify';


const app = createApp(App)

app.directive('sanitize-html',{
    mounted(el) {
        el.addEventListener('input', (event: Event) => {
          const target = event.target as HTMLInputElement;
          target.value = DOMPurify.sanitize(target.value);
        });
      },
      updated(el) {
        el.addEventListener('input', (event: Event) => {
          const target = event.target as HTMLInputElement;
          target.value = DOMPurify.sanitize(target.value);
        });
      }
})

const pinia = createPinia()
pinia.use(piniaPersistedstate) // pinia-plugin-persistedstate 추가

app.use(pinia)
app.use(router)

app.mount("#app")
