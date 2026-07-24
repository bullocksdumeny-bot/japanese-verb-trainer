package com.verbtrainer.dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.*;
public interface VerbRepository extends JpaRepository<VerbEntry,Long>{
 List<VerbEntry> findTop20ByLemmaContainingIgnoreCaseOrReadingContainingIgnoreCase(String lemma,String reading);
 Optional<VerbEntry> findFirstByLemma(String lemma);
 List<VerbEntry> findByLemmaAndReading(String lemma,String reading);
 List<VerbEntry> findByJlptLevelOrderByCommonRankAscIdAsc(com.verbtrainer.smart.JlptLevel level,Pageable pageable);
 @Modifying @Query("update VerbEntry v set v.jlptLevel=null,v.commonRank=null")
 int clearJlptClassification();
}
