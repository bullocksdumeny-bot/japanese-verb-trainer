package com.verbtrainer.knowledge;

import com.verbtrainer.smart.JlptLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleKnowledgePointRepository extends JpaRepository<KnowledgePoint, Long> {
    Optional<KnowledgePoint> findByCodeAndActiveTrue(String code);
    List<KnowledgePoint> findByJlptLevelAndActiveTrueOrderByDisplayOrderAsc(JlptLevel level);
}
