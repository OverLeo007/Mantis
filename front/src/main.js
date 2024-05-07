import '@mdi/font/css/materialdesignicons.css'
import './assets/main.css'
import BasesApi from "@/api/BasesApi.js";

import { createApp } from 'vue'
import App from './App.vue'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
    components,
    directives,
})

// Router
import router from "@/router.js";

// Mavon
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import axios from "axios";

BasesApi.addToken();

createApp(App)
    .use(vuetify)
    .use(router)
    .use(mavonEditor)
    .mount('#app')
