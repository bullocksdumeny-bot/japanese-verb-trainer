package com.verbtrainer.knowledge;

import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.ConjugationType;
import com.verbtrainer.smart.JlptLevel;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "knowledge_point")
public class KnowledgePoint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable = false, unique = true, length = 100) public String code;
    @Column(nullable = false, length = 200) public String name;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 2) public JlptLevel jlptLevel;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 50) public ConjugationType conjugationType;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) public VerbClass verbClass;
    @Column(nullable = false, length = 500) public String summary;
    @Column(nullable = false, length = 1000) public String identificationRule;
    @Column(nullable = false, length = 500) public String transformationFormula;
    @Column(nullable = false, length = 2000) public String explanation;
    @Column(nullable = false, length = 4000) public String examplesJson;
    @Column(nullable = false, length = 4000) public String commonMistakesJson;
    public int displayOrder;
    public boolean active = true;
    public Instant createdAt = Instant.now();
    public Instant updatedAt = Instant.now();

    protected KnowledgePoint() {}

    public KnowledgePoint(String code, String name, JlptLevel level, ConjugationType form,
                          VerbClass verbClass, String summary, String identificationRule,
                          String formula, String explanation, String examplesJson,
                          String commonMistakesJson, int order) {
        this.code = code; this.name = name; this.jlptLevel = level;
        this.conjugationType = form; this.verbClass = verbClass; this.summary = summary;
        this.identificationRule = identificationRule; this.transformationFormula = formula;
        this.explanation = explanation; this.examplesJson = examplesJson;
        this.commonMistakesJson = commonMistakesJson; this.displayOrder = order;
    }
}

