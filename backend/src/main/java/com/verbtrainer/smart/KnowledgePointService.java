package com.verbtrainer.smart;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.time.*;import java.util.*;
@Service public class KnowledgePointService{
 private final KnowledgePointRepository repo;private final MasteryCalculator calc;public KnowledgePointService(KnowledgePointRepository r,MasteryCalculator c){repo=r;calc=c;}
 @Transactional public List<String>update(Collection<String>keys,boolean correct,Instant at){var existing=repo.findByKnowledgePointKeyIn(keys);Map<String,KnowledgePointMastery>map=new HashMap<>();existing.forEach(x->map.put(x.knowledgePointKey,x));List<KnowledgePointMastery>save=new ArrayList<>();
  for(String key:keys){var type=KnowledgePointType.valueOf(key.substring(0,key.indexOf(':')<0?key.length():key.indexOf(':')));var m=map.getOrDefault(key,new KnowledgePointMastery(type,key));var r=calc.calculate(new MasteryState(m.attemptCount,m.correctCount,m.wrongCount,m.correctStreak,m.masteryScore,m.lastAttemptAt,m.lastWrongAt),correct,at);m.attemptCount=r.attempts();m.correctCount=r.correct();m.wrongCount=r.wrong();m.correctStreak=r.streak();m.masteryScore=r.score();m.lastAttemptAt=at;if(!correct)m.lastWrongAt=at;m.updatedAt=at;save.add(m);}repo.saveAll(save);return new ArrayList<>(keys);}
}
