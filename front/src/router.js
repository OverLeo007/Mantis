// Templates for route
import { createRouter, createWebHashHistory } from "vue-router";
import Boards from "@/pages/Boards.vue";
import BoardPage from "@/pages/BoardPage.vue";
import CalendarPage from "@/pages/CalendarPage.vue";
import Login from "@/pages/Login.vue";
import Registration from "@/pages/Registration.vue";
import AuthApi from "@/api/auth/AuthApi.js";


const routes = [
    { path: '/', component: Boards },
    { path: '/board/:id', component: BoardPage, name: 'board'},
    { path: '/calendar', component: CalendarPage },
    { path: '/login', component: Login, name: 'login'},
    { path: '/signin', component: Registration, name: 'signin'}
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach(async (to, from, next) => {
    const canAccess = await AuthApi.canUserAccess();

    if (!canAccess && (to.name === 'login' || to.name === 'signin')) {
        next();
    } else if (!canAccess && to.name !== 'login') {
        next({name: 'login'})
    } else {
        next();
    }
});

export default router;