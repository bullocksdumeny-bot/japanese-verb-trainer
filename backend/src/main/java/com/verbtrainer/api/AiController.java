package com.verbtrainer.api;
import com.verbtrainer.ai.AiService;import jakarta.validation.constraints.NotBlank;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ai") public class AiController{
 private final AiService ai;public AiController(AiService a){ai=a;}public record Request(@NotBlank String lemma,@NotBlank String form,@NotBlank String correct,String attempt){}
 @PostMapping("/explain")public java.util.Map<String,String> explain(@RequestBody Request r){return java.util.Map.of("explanation",ai.explain(r.lemma,r.form,r.correct,r.attempt));}
}
