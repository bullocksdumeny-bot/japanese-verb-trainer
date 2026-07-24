<script setup lang="ts">import {House,Search,EditPen,Clock,CollectionTag} from '@element-plus/icons-vue';</script>
<template><div class="shell"><aside><div class="brand"><span>動</span><div><b>活用ノート</b><small>Japanese Verb Trainer</small></div></div><nav>
<router-link to="/"><el-icon><House/></el-icon>今日</router-link><router-link to="/search"><el-icon><Search/></el-icon>活用查询</router-link><router-link to="/training"><el-icon><EditPen/></el-icon>训练</router-link><router-link to="/review"><el-icon><Clock/></el-icon>错题复习</router-link></nav>
<div class="side-note"><el-icon><CollectionTag/></el-icon><b>本地规则判定</b><p>AI 只负责解释，绝不参与答案判断。</p></div></aside><main><router-view/></main></div>
<div class="mobile-nav"><router-link to="/">今日</router-link><router-link to="/search">查询</router-link><router-link to="/training">训练</router-link><router-link to="/review">复习</router-link></div></template>
