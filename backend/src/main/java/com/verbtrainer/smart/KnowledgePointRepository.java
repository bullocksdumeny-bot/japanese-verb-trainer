package com.verbtrainer.smart;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface KnowledgePointRepository extends JpaRepository<KnowledgePointMastery,Long>{Optional<KnowledgePointMastery> findByKnowledgePointKey(String key);List<KnowledgePointMastery> findByKnowledgePointKeyIn(Collection<String> keys);}
