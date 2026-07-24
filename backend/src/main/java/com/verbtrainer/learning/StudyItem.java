package com.verbtrainer.learning;
import jakarta.persistence.*;import java.time.*;
@Entity @Table(name="study_item",uniqueConstraints=@UniqueConstraint(columnNames={"verb_id","form"}))
public class StudyItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Column(name="verb_id") public Long verbId; public String form;
 public int repetitions;public int errorCount;public int correctStreak;public double easeFactor=2.5;public int intervalDays;public LocalDateTime lastReviewed;public LocalDateTime nextReview=LocalDateTime.now();
 protected StudyItem(){} public StudyItem(Long v,String f){verbId=v;form=f;}
 public void review(boolean correct){lastReviewed=LocalDateTime.now();if(correct){correctStreak++;repetitions++;intervalDays=repetitions==1?1:repetitions==2?4:Math.max(7,(int)Math.round(intervalDays*easeFactor));easeFactor=Math.min(3, easeFactor+0.1);}else{errorCount++;correctStreak=0;repetitions=0;intervalDays=0;easeFactor=Math.max(1.3,easeFactor-0.2);}nextReview=lastReviewed.plusDays(correct?intervalDays:0);}
}
