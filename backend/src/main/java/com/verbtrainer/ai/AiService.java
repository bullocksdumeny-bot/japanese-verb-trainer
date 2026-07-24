package com.verbtrainer.ai;
import org.springframework.beans.factory.annotation.Value;import org.springframework.http.*;import org.springframework.stereotype.Service;import org.springframework.web.client.RestClient;import java.util.*;
@Service public class AiService{
 @Value("${ai.enabled:false}")boolean enabled;@Value("${ai.base-url:}")String base;@Value("${ai.api-key:}")String key;@Value("${ai.model:}")String model;
 public String explain(String lemma,String form,String correct,String attempt){if(!enabled)return "AI 功能未启用。本地规则判定："+lemma+" 的"+form+"是「"+correct+"」。";
  String prompt="你是JLPT日语老师。用简洁中文解释活用原因，给一个带假名和中文的N5-N3例句。正确答案由本地引擎给定，禁止修改。动词："+lemma+"，目标："+form+"，正确答案："+correct+(attempt==null?"":"，学习者答案："+attempt);
  var body=Map.of("model",model,"messages",List.of(Map.of("role","system","content","绝不能推翻本地规则答案。"),Map.of("role","user","content",prompt)),"temperature",0.4);
  try{var response=RestClient.create(base).post().uri("/chat/completions").header("Authorization","Bearer "+key).contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Map.class);var choices=(List<Map<String,Object>>)response.get("choices");return String.valueOf(((Map<?,?>)choices.get(0).get("message")).get("content"));}catch(Exception e){return "AI 服务暂时不可用。本地正确答案仍为「"+correct+"」。";}
 }
}
