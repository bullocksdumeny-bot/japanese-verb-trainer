package com.verbtrainer.ruletraining;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/rule-training")
public class RuleTrainingController {
    private final RuleTrainingService service;private final N5RuleCatalog catalog;
    public RuleTrainingController(RuleTrainingService s,N5RuleCatalog c){service=s;catalog=c;}
    @GetMapping("/catalog")public Map<String,Object>catalog(){return Map.of("level","N5","rules",catalog.display());}
    @PostMapping("/question")public Map<String,Object>question(@RequestBody RuleTrainingService.QuestionRequest request){return service.question(request);}
    @PostMapping("/answer")public Map<String,Object>answer(@RequestBody RuleTrainingService.AnswerRequest request){return service.answer(request);}
    @GetMapping("/stats")public Map<String,Object>stats(){return service.stats();}
}
