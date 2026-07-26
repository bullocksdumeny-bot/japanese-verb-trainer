<script setup lang="ts">
import{ref,computed,onMounted}from'vue';import{useRouter}from'vue-router';import{api}from'../services/api';import{ElMessage}from'element-plus';
type Level='N5'|'N4'|'N3'|'N2';const router=useRouter(),level=ref<Level>('N5'),points=ref<any[]>([]),selected=ref<any>(),loading=ref(false);
const grouped=computed(()=>points.value.reduce((all:Record<string,any[]>,p:any)=>{(all[p.conjugationType]??=[]).push(p);return all},{}));
async function load(){loading.value=true;selected.value=undefined;try{points.value=(await api.get('/v1/knowledge-points',{params:{level:level.value}})).data}catch{ElMessage.error('知识点目录加载失败')}finally{loading.value=false}}
async function open(code:string){selected.value=(await api.get(`/v1/knowledge-points/${code}`)).data}
function practice(){router.push({path:'/training',query:{knowledgePoint:selected.value.code,level:selected.value.jlptLevel}})}
onMounted(load);
</script>
<template><div class="eyebrow">RULE-DRIVEN LEARNING</div><h1 class="page-title">先学规则，再做专项训练。</h1>
<div class="level-tabs"><el-radio-group v-model="level" @change="load"><el-radio-button v-for="l in ['N5','N4','N3','N2']" :key="l" :value="l">{{l}}</el-radio-button></el-radio-group></div>
<el-alert v-if="!loading&&!points.length" title="这个等级的规则课程正在整理中；普通训练仍可使用。" type="info" :closable="false"/>
<div class="knowledge-layout" v-loading="loading"><section><div v-for="(items,form) in grouped" :key="form" class="topic-group"><h2>{{form}} 专题</h2>
<button v-for="p in items" :key="p.code" class="card knowledge-card" :class="{active:selected?.code===p.code}" @click="open(p.code)"><span class="tag">{{p.verbClass}}</span><h3>{{p.name}}</h3><p>{{p.summary}}</p><b>{{p.formula}}</b></button></div></section>
<aside v-if="selected" class="card rule-detail"><div class="eyebrow">{{selected.jlptLevel}} · {{selected.conjugationType}}</div><h2>{{selected.name}}</h2><h3>判断方法</h3><p>{{selected.identificationRule}}</p><h3>变化公式</h3><div class="formula">{{selected.formula}}</div><p>{{selected.explanation}}</p><h3>例子</h3><p v-for="x in selected.examples" :key="x" class="example">{{x}}</p><h3>常见错误</h3><p v-for="x in selected.commonMistakes" :key="x" class="mistake">{{x}}</p><el-button class="primary" type="primary" size="large" @click="practice">开始 20 题专项训练</el-button></aside></div></template>
<style scoped>.level-tabs{margin:24px 0}.knowledge-layout{display:grid;grid-template-columns:minmax(0,1fr) minmax(320px,.8fr);gap:24px}.topic-group{display:grid;grid-template-columns:1fr 1fr;gap:12px;margin-bottom:24px}.topic-group h2{grid-column:1/-1}.knowledge-card{text-align:left;border:1px solid var(--line);cursor:pointer}.knowledge-card.active{border-color:#c94b40;box-shadow:0 0 0 2px #c94b4022}.knowledge-card p,.rule-detail p{line-height:1.7}.rule-detail{position:sticky;top:24px;align-self:start}.formula{font-size:1.15rem;font-weight:700;background:#f7f1e7;padding:16px;border-radius:10px}.example{border-left:3px solid #1c637e;padding-left:12px}.mistake{color:#a43d35}@media(max-width:800px){.knowledge-layout,.topic-group{grid-template-columns:1fr}.rule-detail{position:static}}</style>
