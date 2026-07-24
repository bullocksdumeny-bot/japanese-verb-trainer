package com.verbtrainer.smart;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface TrainingQuestionRepository extends JpaRepository<TrainingQuestion,Long>{Optional<TrainingQuestion> findByIdAndSessionId(Long id,Long sessionId);List<TrainingQuestion> findTop30ByOrderByIdDesc();}
