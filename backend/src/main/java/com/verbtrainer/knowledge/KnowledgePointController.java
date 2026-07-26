package com.verbtrainer.knowledge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verbtrainer.smart.JlptLevel;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/knowledge-points")
public class KnowledgePointController {
    private final RuleKnowledgePointRepository repository;
    private final ObjectMapper json;

    public KnowledgePointController(RuleKnowledgePointRepository repository, ObjectMapper json) {
        this.repository = repository; this.json = json;
    }

    @GetMapping
    public List<Map<String, Object>> list(@RequestParam JlptLevel level) {
        return repository.findByJlptLevelAndActiveTrueOrderByDisplayOrderAsc(level)
            .stream().map(this::summary).toList();
    }

    @GetMapping("/{code}")
    public Map<String, Object> detail(@PathVariable String code) {
        return detail(repository.findByCodeAndActiveTrue(code)
            .orElseThrow(() -> new NoSuchElementException("知识点不存在或未启用")));
    }

    private Map<String, Object> summary(KnowledgePoint p) {
        var out = new LinkedHashMap<String, Object>();
        out.put("code", p.code); out.put("name", p.name); out.put("jlptLevel", p.jlptLevel);
        out.put("conjugationType", p.conjugationType); out.put("verbClass", p.verbClass);
        out.put("summary", p.summary); out.put("formula", p.transformationFormula);
        return out;
    }

    private Map<String, Object> detail(KnowledgePoint p) {
        var out = new LinkedHashMap<>(summary(p));
        out.put("identificationRule", p.identificationRule);
        out.put("explanation", p.explanation);
        out.put("examples", read(p.examplesJson));
        out.put("commonMistakes", read(p.commonMistakesJson));
        return out;
    }

    private List<String> read(String value) {
        try { return json.readValue(value, new TypeReference<>() {}); }
        catch (Exception e) { return List.of(); }
    }
}
