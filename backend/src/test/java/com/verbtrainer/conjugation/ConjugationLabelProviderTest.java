package com.verbtrainer.conjugation;

import static org.assertj.core.api.Assertions.assertThat;
import com.verbtrainer.smart.ConjugationType;
import org.junit.jupiter.api.Test;

class ConjugationLabelProviderTest {
  private final ConjugationLabelProvider labels = new DefaultConjugationLabelProvider();

  @Test
  void everyInternalTypeHasALearnerFriendlyLabel() {
    for (var type : ConjugationType.values()) {
      var label = labels.getLabel(type);
      assertThat(label.displayName()).isNotBlank().isNotEqualTo(type.name());
      assertThat(label.japaneseName()).isNotBlank();
      assertThat(label.chineseName()).isNotBlank();
      assertThat(label.explanation()).isNotBlank();
    }
  }

  @Test
  void commonFormsUseTextbookNames() {
    assertThat(labels.getLabel(ConjugationType.POLITE).displayName()).isEqualTo("ます形（礼貌表达）");
    assertThat(labels.getLabel(ConjugationType.TE).chineseName()).isEqualTo("て形");
    assertThat(labels.getLabel(ConjugationType.POTENTIAL).displayName()).isEqualTo("可能形（能够……）");
  }
}
