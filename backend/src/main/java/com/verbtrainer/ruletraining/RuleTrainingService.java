package com.verbtrainer.ruletraining;

import com.verbtrainer.conjugation.ConjugationForm;
import com.verbtrainer.conjugation.ConjugationService;
import com.verbtrainer.dictionary.VerbEntry;
import com.verbtrainer.dictionary.VerbRepository;
import com.verbtrainer.smart.*;
import java.time.Instant;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuleTrainingService {
    public enum Mode{CATEGORY,RULE,FULL}
    public enum Stage{CATEGORY,RULE,CONJUGATION}
    public record QuestionRequest(Mode mode,List<Long>excludeVerbIds){}
    public record AnswerRequest(long verbId,Stage stage,String answer){}
    private final VerbRepository verbs;private final N5RuleCatalog catalog;private final ConjugationService conjugator;private final KnowledgePointService knowledge;private final KnowledgePointRepository mastery;
    public RuleTrainingService(VerbRepository v,N5RuleCatalog c,ConjugationService cs,KnowledgePointService k,KnowledgePointRepository m){verbs=v;catalog=c;conjugator=cs;knowledge=k;mastery=m;}
    @Transactional(readOnly=true) public Map<String,Object> question(QuestionRequest req){
        Set<Long>excluded=new HashSet<>(Objects.requireNonNullElse(req.excludeVerbIds(),List.of()));
        Map<String,KnowledgePointMastery>scores=new HashMap<>();mastery.findAll().forEach(x->scores.put(x.knowledgePointKey,x));
        var candidates=verbs.findByJlptLevelOrderByCommonRankAscIdAsc(JlptLevel.N5,PageRequest.of(0,500)).stream().filter(v->!excluded.contains(v.id)).sorted(Comparator.comparingDouble((VerbEntry v)->masteryScore(scores,catalog.forVerb(v).masteryKey())).thenComparing(v->Objects.requireNonNullElse(v.commonRank,9999))).toList();
        if(candidates.isEmpty())throw new IllegalStateException("没有更多可用的 N5 规则训练动词");
        VerbEntry v=candidates.get(0);var out=new LinkedHashMap<String,Object>();out.put("verbId",v.id);out.put("word",displayLemma(v));out.put("reading",displayReading(v));out.put("mode",req.mode());out.put("categoryOptions",catalog.categoryOptions());out.put("ruleOptions",catalog.all().stream().map(N5RuleCatalog.Rule::label).toList());return out;
    }
    @Transactional public Map<String,Object> answer(AnswerRequest req){
        VerbEntry v=verbs.findById(req.verbId()).orElseThrow();var rule=catalog.forVerb(v);String correct=switch(req.stage()){case CATEGORY->catalog.category(v.verbClass);case RULE->rule.label();case CONJUGATION->te(v);};boolean ok=normalize(correct).equals(normalize(req.answer()));String key=switch(req.stage()){case CATEGORY->"VERB_CLASS:"+v.verbClass;case RULE->rule.masteryKey();case CONJUGATION->"CONJUGATION_TYPE:TE";};String stageKey="CONTEXT_USAGE:N5_RULE_"+req.stage();knowledge.update(List.of(key,stageKey),ok,Instant.now());var out=new LinkedHashMap<String,Object>();out.put("correct",ok);out.put("correctAnswer",correct);out.put("category",catalog.category(v.verbClass));out.put("ruleCode",rule.code());out.put("rule",rule.label());out.put("explanation",explain(v,rule,req.stage(),ok));out.put("examples",rule.examples());out.put("conjugation",te(v));return out;
    }
    @Transactional(readOnly=true) public Map<String,Object> stats(){return Map.of("category",rate("CONTEXT_USAGE:N5_RULE_CATEGORY"),"rule",rate("CONTEXT_USAGE:N5_RULE_RULE"),"conjugation",rate("CONTEXT_USAGE:N5_RULE_CONJUGATION"));}
    private Map<String,Object>rate(String prefix){var x=mastery.findAll().stream().filter(m->m.knowledgePointKey.startsWith(prefix)).toList();int a=x.stream().mapToInt(m->m.attemptCount).sum(),c=x.stream().mapToInt(m->m.correctCount).sum();return Map.of("attempts",a,"correct",c,"accuracy",a==0?0:Math.round(c*1000.0/a)/10.0);}
    private double masteryScore(Map<String,KnowledgePointMastery> scores,String key){var value=scores.get(key);return value==null?50:value.masteryScore;}
    private String explain(VerbEntry v,N5RuleCatalog.Rule r,Stage stage,boolean ok){if(stage==Stage.CATEGORY&&v.lemma.equals("帰る"))return"虽然「帰る」以「る」结尾，但它属于五段动词。它会变成「帰って、帰らない、帰ります」。";if(stage==Stage.CATEGORY)return displayLemma(v)+"属于"+catalog.category(v.verbClass)+"。";return displayLemma(v)+"属于"+catalog.category(v.verbClass)+"，词尾适用「"+r.label()+"」，所以变为「"+te(v)+"」。";}
    private String te(VerbEntry v){return conjugator.conjugate(displayLemma(v),v.verbClass).stream().filter(x->x.form()==ConjugationForm.TE).findFirst().orElseThrow().value();}
    private String displayLemma(VerbEntry v){return v.verbClass.name().equals("SURU")&&!v.lemma.endsWith("する")?v.lemma+"する":v.lemma;}
    private String displayReading(VerbEntry v){return v.verbClass.name().equals("SURU")&&!v.reading.endsWith("する")?v.reading+"する":v.reading;}
    private String normalize(String s){return Objects.requireNonNullElse(s,"").strip().replace(" ","").replace("　","");}
}
