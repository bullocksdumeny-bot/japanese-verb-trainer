package com.verbtrainer.smart;
import org.springframework.data.jpa.repository.*;import org.springframework.data.repository.query.Param;import java.util.*;
public interface JlptVocabularyRepository extends JpaRepository<JlptVocabularyLevel,Long>{
 Optional<JlptVocabularyLevel> findByDictionaryEntryIdAndJlptLevel(Long id,JlptLevel l);
 @Query("select v from VerbEntry v join JlptVocabularyLevel j on v.id=j.dictionaryEntryId where j.jlptLevel=:level order by v.id")
 List<com.verbtrainer.dictionary.VerbEntry> findCandidates(@Param("level") JlptLevel level,org.springframework.data.domain.Pageable pageable);
}
