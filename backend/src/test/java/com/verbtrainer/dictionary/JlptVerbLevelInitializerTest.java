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
  void assignsExactWordAndReadingAndSkipsMissingEntries() throws Exception {
    var repository = Mockito.mock(VerbRepository.class);
    var taberu = new VerbEntry("食べる", "たべる", "吃", VerbClass.ICHIDAN, "v1");
    Mockito.when(repository.findByLemmaAndReading("食べる", "たべる")).thenReturn(List.of(taberu));
    var initializer = new JlptVerbLevelInitializer(repository, new ObjectMapper());

    initializer.run(Mockito.mock(org.springframework.boot.ApplicationArguments.class));

    assertThat(taberu.jlptLevel).isEqualTo(JlptLevel.N5);
    assertThat(taberu.commonRank).isEqualTo(1);
    Mockito.verify(repository).clearJlptClassification();
    Mockito.verify(repository).save(taberu);
  }
}
