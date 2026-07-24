package com.verbtrainer.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.JlptLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class VerbLevelRepositoryTest {
  @Autowired VerbRepository verbs;

  @Test
  void filtersExactLevelAndOrdersCommonWordsFirst() {
    save("常用", JlptLevel.N3, 1);
    save("冷门", JlptLevel.N3, 90);
    save("其他等级", JlptLevel.N5, 1);
    save("未分类", null, null);

    var candidates = verbs.findByJlptLevelOrderByCommonRankAscIdAsc(JlptLevel.N3, PageRequest.of(0, 20));

    assertThat(candidates).extracting(v -> v.lemma).containsExactly("常用", "冷门");
  }

  private void save(String lemma, JlptLevel level, Integer rank) {
    var verb = new VerbEntry(lemma, lemma, "测试", VerbClass.ICHIDAN, "test");
    verb.jlptLevel = level;
    verb.commonRank = rank;
    verbs.save(verb);
  }
}
