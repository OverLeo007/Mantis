// Templates for route
import { createRouter, createWebHashHistory } from "vue-router";
import Boards from "@/pages/Boards.vue";
import BoardPage from "@/pages/BoardPage.vue";


const routes = [
    { path: '/', component: Boards },
    { path: '/board/:id', component: BoardPage },
];

export default createRouter({
    history: createWebHashHistory(),
    routes,
});