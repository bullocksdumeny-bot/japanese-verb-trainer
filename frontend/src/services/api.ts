import axios from 'axios';export const api=axios.create({baseURL:'/api'});
export interface Verb{id:number;lemma:string;reading:string;meanings:string;verbClass:string;jmdictTags:string}
export interface Form{form:string;label:string;value:string;steps:string[];exception:boolean}
