package com.verbtrainer.learning;
import org.springframework.data.jpa.repository.JpaRepository;import java.time.*;import java.util.*;
public interface StudyRepository extends JpaRepository<StudyItem,Long>{
 Optional<StudyItem> findByVerbIdAndForm(Long verbId,String form);
 List<StudyItem> findByNextReviewBeforeOrderByNextReview(LocalDateTime now);
}
