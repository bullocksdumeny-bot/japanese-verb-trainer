import {createRouter,createWebHistory} from 'vue-router';
import Home from '../views/Home.vue';import Search from '../views/Search.vue';import Training from '../views/Training.vue';import RuleTraining from '../views/RuleTraining.vue';import Review from '../views/Review.vue';
const base=window.location.pathname.startsWith('/trainer')?'/trainer/':'/';
export default createRouter({history:createWebHistory(base),routes:[{path:'/',component:Home},{path:'/search',component:Search},{path:'/training',component:Training},{path:'/rule-training',component:RuleTraining},{path:'/review',component:Review}]});
