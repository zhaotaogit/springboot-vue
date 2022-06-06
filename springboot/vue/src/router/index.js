import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Manage',
        meta:{name:'首页'},
        component: () => import('../views/Manage.vue'),
        redirect: '/home',   // 输入/，重定向到/home
        children: [
            {
                path: 'home',
                name: 'Home',
                // meta: {name: '首页'},
                component: () => import('../views/Home.vue'),
            },
            {
                path: 'user',
                name: 'User',
                meta: {name: '用户管理'},
                component: () => import('../views/User.vue'),
            },
            {
                path: 'person',
                name: 'Person',
                meta: {name: '个人信息'},
                component: () => import('../views/PersonInfo.vue'),
            },

        ]
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
        {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
