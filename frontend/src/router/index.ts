import {createRouter,createWebHistory} from 'vue-router';
import Home from '../views/Home.vue';import Search from '../views/Search.vue';import Training from '../views/Training.vue';import Review from '../views/Review.vue';
export default createRouter({history:createWebHistory(),routes:[{path:'/',component:Home},{path:'/search',component:Search},{path:'/training',component:Training},{path:'/review',component:Review}]});
