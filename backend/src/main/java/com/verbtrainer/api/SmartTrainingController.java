package com.verbtrainer.api;
import com.verbtrainer.smart.*;import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/v1/training/sessions")public class SmartTrainingController{
 private final TrainingSessionService service;private final JlptTrainingPolicy policy;public SmartTrainingController(TrainingSessionService s,JlptTrainingPolicy p){service=s;policy=p;}
 @PostMapping public Map<String,Object>create(@Valid@RequestBody CreateTrainingSessionRequest r){return service.create(r);}
 public record AnswerRequest(@NotBlank String answer){}
 @PostMapping("/{sid}/questions/{qid}/answer")public Map<String,Object>answer(@PathVariable Long sid,@PathVariable Long qid,@Valid@RequestBody AnswerRequest r){return service.answer(sid,qid,r.answer());}
 @GetMapping("/{sid}/summary")public Map<String,Object>summary(@PathVariable Long sid){return service.summary(sid);}
 @GetMapping("/policy/{level}")public Map<String,Object>policy(@PathVariable JlptLevel level){return Map.of("level",level,"conjugationTypes",policy.allowedConjugationTypes(level),"questionTypes",policy.allowedQuestionTypes(level),"profile",policy.getDifficultyProfile(level));}
}
