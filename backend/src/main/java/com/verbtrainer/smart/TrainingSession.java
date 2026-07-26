package com.verbtrainer.smart;
import com.verbtrainer.knowledge.KnowledgePoint;
import jakarta.persistence.*;import java.time.*;import java.util.*;
@Entity @Table(name="training_session")public class TrainingSession{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)public Long id;@Enumerated(EnumType.STRING)public JlptLevel jlptLevel;@Enumerated(EnumType.STRING)public TrainingMode mode;public int requestedCount,actualCount;public String shortageReason;public Instant createdAt=Instant.now();
 @ManyToOne @JoinColumn(name="knowledge_point_id")public KnowledgePoint knowledgePoint;
 @OneToMany(mappedBy="session",cascade=CascadeType.ALL,orphanRemoval=true)public List<TrainingQuestion> questions=new ArrayList<>();
 protected TrainingSession(){}public TrainingSession(JlptLevel l,TrainingMode m,int n){jlptLevel=l;mode=m;requestedCount=n;}
}
