// Templates for route
import { createRouter, createWebHashHistory } from "vue-router";
import Boards from "@/pages/Boards.vue";
import BoardPage from "@/pages/BoardPage.vue";
import board from "@/components/Board.vue";


const routes = [
    { path: '/', component: Boards },
    { path: '/board/:id', component: BoardPage, name: 'board'},
];

export default createRouter({
    history: createWebHashHistory(),
    routes,
});