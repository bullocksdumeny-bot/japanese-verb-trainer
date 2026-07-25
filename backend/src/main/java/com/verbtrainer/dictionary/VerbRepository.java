package com.verbtrainer.dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import com.verbtrainer.conjugation.VerbClass;
import java.util.*;
public interface VerbRepository extends JpaRepository<VerbEntry,Long>{
 @Query("""
  select v from VerbEntry v
  where lower(v.lemma) like lower(:pattern) or lower(v.reading) like lower(:pattern)
  order by case
   when lower(v.lemma)=lower(:query) then 0
   when lower(v.reading)=lower(:query) then 1
   when lower(v.lemma) like lower(:prefix) then 2
   when lower(v.reading) like lower(:prefix) then 3
   else 4
  end, length(v.lemma), v.id
  """)
 List<VerbEntry> searchRanked(String query,String pattern,String prefix,Pageable pageable);
 Optional<VerbEntry> findFirstByLemma(String lemma);
 List<VerbEntry> findByLemmaAndReading(String lemma,String reading);
 List<VerbEntry> findByLemmaAndReadingAndVerbClass(String lemma,String reading,VerbClass verbClass);
 List<VerbEntry> findByJlptLevelOrderByCommonRankAscIdAsc(com.verbtrainer.smart.JlptLevel level,Pageable pageable);
 @Modifying @Query("update VerbEntry v set v.jlptLevel=null,v.commonRank=null")
 int clearJlptClassification();
}
