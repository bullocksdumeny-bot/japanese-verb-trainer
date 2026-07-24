package com.verbtrainer.smart;
import jakarta.persistence.*;import java.time.*;
@Entity @Table(name="jlpt_vocabulary_levels",uniqueConstraints=@UniqueConstraint(columnNames={"dictionary_entry_id","jlpt_level"}))
public class JlptVocabularyLevel {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;@Column(name="dictionary_entry_id")public Long dictionaryEntryId;
 @Enumerated(EnumType.STRING)public JlptLevel jlptLevel;@Enumerated(EnumType.STRING)public JlptLevelSource source;@Enumerated(EnumType.STRING)public DataConfidence confidence;
 public Instant createdAt=Instant.now(),updatedAt=Instant.now();protected JlptVocabularyLevel(){}public JlptVocabularyLevel(Long id,JlptLevel l,JlptLevelSource s,DataConfidence c){dictionaryEntryId=id;jlptLevel=l;source=s;confidence=c;}
}
