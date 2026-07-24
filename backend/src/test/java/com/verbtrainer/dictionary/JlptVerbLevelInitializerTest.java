package com.verbtrainer.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.JlptLevel;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JlptVerbLevelInitializerTest {
  @Test
  void assignsExactWordAndReadingAndSkipsMissingEntries() {
    var repository = Mockito.mock(VerbRepository.class);
    var taberu = new VerbEntry("食べる", "たべる", "吃", VerbClass.ICHIDAN, "v1");
    Mockito.when(repository.findByLemmaAndReading("食べる", "たべる")).thenReturn(List.of(taberu));
    var initializer = new JlptVerbLevelInitializer(repository, new ObjectMapper());

    var report = initializer.synchronize(List.of(
        new JlptVerbLevelInitializer.LevelEntry("食べる", "たべる", JlptLevel.N5, 1),
        new JlptVerbLevelInitializer.LevelEntry("不存在", "ふそんざい", JlptLevel.N4, 2)));

    assertThat(taberu.jlptLevel).isEqualTo(JlptLevel.N5);
    assertThat(taberu.commonRank).isEqualTo(1);
    assertThat(report.levels().get(JlptLevel.N5).matched()).isEqualTo(1);
    assertThat(report.levels().get(JlptLevel.N4).unmatched()).isEqualTo(1);
    Mockito.verify(repository).clearJlptClassification();
    Mockito.verify(repository).save(taberu);
  }

  @Test
  void fallsBackToBaseSuruEntryButRejectsAmbiguousCandidates() {
    var repository = Mockito.mock(VerbRepository.class);
    var benkyou = new VerbEntry("勉強", "べんきょう", "学习", VerbClass.SURU, "vs");
    var duplicateA = new VerbEntry("連絡", "れんらく", "联系", VerbClass.SURU, "vs");
    var duplicateB = new VerbEntry("連絡", "れんらく", "联系", VerbClass.SURU, "vs");
    Mockito.when(repository.findByLemmaAndReading("勉強する", "べんきょうする")).thenReturn(List.of());
    Mockito.when(repository.findByLemmaAndReadingAndVerbClass("勉強", "べんきょう", VerbClass.SURU)).thenReturn(List.of(benkyou));
    Mockito.when(repository.findByLemmaAndReading("連絡する", "れんらくする")).thenReturn(List.of());
    Mockito.when(repository.findByLemmaAndReadingAndVerbClass("連絡", "れんらく", VerbClass.SURU)).thenReturn(List.of(duplicateA, duplicateB));
    var initializer = new JlptVerbLevelInitializer(repository, new ObjectMapper());

    var report = initializer.synchronize(List.of(
        new JlptVerbLevelInitializer.LevelEntry("勉強する", "べんきょうする", JlptLevel.N5, 10),
        new JlptVerbLevelInitializer.LevelEntry("連絡する", "れんらくする", JlptLevel.N4, 20)));

    assertThat(benkyou.jlptLevel).isEqualTo(JlptLevel.N5);
    assertThat(benkyou.commonRank).isEqualTo(10);
    assertThat(report.levels().get(JlptLevel.N5).matched()).isEqualTo(1);
    assertThat(report.levels().get(JlptLevel.N4).unmatched()).isEqualTo(1);
    assertThat(report.unmatchedEntries()).singleElement().extracting(JlptVerbLevelInitializer.UnmatchedEntry::reason).isEqualTo("存在多个候选");
    Mockito.verify(repository).save(benkyou);
    Mockito.verify(repository, Mockito.never()).save(duplicateA);
  }
}
