package com.verbtrainer.smart;
import org.junit.jupiter.api.Test;import static org.assertj.core.api.Assertions.*;
class DefaultJlptTrainingPolicyTest{
 final DefaultJlptTrainingPolicy p=new DefaultJlptTrainingPolicy();
 @Test void rangesGrowByLevel(){assertThat(p.allowedConjugationTypes(JlptLevel.N5)).doesNotContain(ConjugationType.CAUSATIVE_PASSIVE);assertThat(p.allowedConjugationTypes(JlptLevel.N4)).contains(ConjugationType.POTENTIAL,ConjugationType.CONDITIONAL_BA);assertThat(p.allowedConjugationTypes(JlptLevel.N3)).contains(ConjugationType.PASSIVE,ConjugationType.CAUSATIVE);assertThat(p.allowedConjugationTypes(JlptLevel.N2)).containsExactlyInAnyOrder(ConjugationType.values());}
}
