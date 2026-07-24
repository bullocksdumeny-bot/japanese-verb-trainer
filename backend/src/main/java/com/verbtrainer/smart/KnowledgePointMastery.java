package com.verbtrainer.smart;
import jakarta.persistence.*;import java.time.*;
@Entity @Table(name="knowledge_point_mastery",uniqueConstraints=@UniqueConstraint(columnNames={"knowledge_point_type","knowledge_point_key"}))
public class KnowledgePointMastery {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)public Long id;@Enumerated(EnumType.STRING)public KnowledgePointType knowledgePointType;public String knowledgePointKey;
 public int attemptCount,correctCount,wrongCount,correctStreak;public Instant lastAttemptAt,lastWrongAt;public double masteryScore;public Instant createdAt=Instant.now(),updatedAt=Instant.now();
 protected KnowledgePointMastery(){}public KnowledgePointMastery(KnowledgePointType t,String k){knowledgePointType=t;knowledgePointKey=k;}
}
