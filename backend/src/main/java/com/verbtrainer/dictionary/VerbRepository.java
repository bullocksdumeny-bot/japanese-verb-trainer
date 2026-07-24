package com.verbtrainer.dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface VerbRepository extends JpaRepository<VerbEntry,Long>{
 List<VerbEntry> findTop20ByLemmaContainingIgnoreCaseOrReadingContainingIgnoreCase(String lemma,String reading);
 Optional<VerbEntry> findFirstByLemma(String lemma);
 List<VerbEntry> findByLemmaAndReading(String lemma,String reading);
}
