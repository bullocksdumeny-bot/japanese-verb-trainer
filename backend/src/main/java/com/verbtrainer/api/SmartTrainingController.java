package com.verbtrainer.api;
import com.verbtrainer.smart.*;import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/v1/training/sessions")public class SmartTrainingController{
 private final TrainingSessionService service;private final JlptTrainingPolicy policy;private final com.verbtrainer.conjugation.ConjugationLabelProvider labels;public SmartTrainingController(TrainingSessionService s,JlptTrainingPolicy p,com.verbtrainer.conjugation.ConjugationLabelProvider labels){service=s;policy=p;this.labels=labels;}
 @PostMapping public Map<String,Object>create(@Valid@RequestBody CreateTrainingSessionRequest r){return service.create(r);}
 public record AnswerRequest(@NotBlank String answer){}
 @PostMapping("/{sid}/questions/{qid}/answer")public Map<String,Object>answer(@PathVariable Long sid,@PathVariable Long qid,@Valid@RequestBody AnswerRequest r){return service.answer(sid,qid,r.answer());}
 @GetMapping("/{sid}/summary")public Map<String,Object>summary(@PathVariable Long sid){return service.summary(sid);}
 @GetMapping("/policy/{level}")public Map<String,Object>policy(@PathVariable JlptLevel level){var labelList=policy.allowedConjugationTypes(level).stream().map(type->{var l=labels.getLabel(type);return Map.of("type",type,"japaneseName",l.japaneseName(),"chineseName",l.chineseName(),"displayName",l.displayName(),"explanation",l.explanation());}).toList();return Map.of("level",level,"conjugationTypes",policy.allowedConjugationTypes(level),"conjugationLabels",labelList,"questionTypes",policy.allowedQuestionTypes(level),"profile",policy.getDifficultyProfile(level));}
}
