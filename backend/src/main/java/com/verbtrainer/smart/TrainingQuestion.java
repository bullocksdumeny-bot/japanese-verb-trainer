package com.verbtrainer.smart;
import com.verbtrainer.knowledge.KnowledgePoint;
import jakarta.persistence.*;import java.time.*;
@Entity @Table(name="training_question",uniqueConstraints=@UniqueConstraint(columnNames={"session_id","verb_id","conjugation_type","question_type"}))
public class TrainingQuestion{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)public Long id;@ManyToOne(optional=false)@JoinColumn(name="session_id")public TrainingSession session;public Long verbId;
 @Enumerated(EnumType.STRING)public QuestionType questionType;@Enumerated(EnumType.STRING)public ConjugationType conjugationType;
 public String dictionaryForm,reading,prompt,correctAnswer;@Column(length=1000)public String optionsJson,knowledgePointKeys,selectionReasons;public double priorityScore;
 public String submittedAnswer;public Boolean correct;public Instant answeredAt;
 @ManyToOne @JoinColumn(name="knowledge_point_id")public KnowledgePoint knowledgePoint;
 public String mistakeType;
 protected TrainingQuestion(){}
}
