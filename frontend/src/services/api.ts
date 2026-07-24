import axios from 'axios';const baseURL=window.location.pathname.startsWith('/trainer')?'/trainer/api':'/api';export const api=axios.create({baseURL});
export interface Verb{id:number;lemma:string;reading:string;meanings:string;verbClass:string;jmdictTags:string}
export interface Form{type:string;japaneseName:string;chineseName:string;displayName:string;explanation:string;value:string;steps:string[];exception:boolean}
