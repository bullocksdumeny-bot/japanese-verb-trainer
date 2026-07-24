package com.verbtrainer.smart;
import org.springframework.stereotype.Component;import java.util.*;
@Component public class DefaultJlptTrainingPolicy implements JlptTrainingPolicy{
 private static final EnumSet<ConjugationType>N5=EnumSet.of(ConjugationType.POLITE,ConjugationType.POLITE_NEGATIVE,ConjugationType.POLITE_PAST,ConjugationType.POLITE_PAST_NEGATIVE,ConjugationType.NAI,ConjugationType.TE,ConjugationType.PAST,ConjugationType.DESIDERATIVE_TAI);
 private static final EnumSet<ConjugationType>N4=with(N5,ConjugationType.VOLITIONAL,ConjugationType.POTENTIAL,ConjugationType.CONDITIONAL_BA,ConjugationType.CONDITIONAL_TARA,ConjugationType.PROHIBITIVE,ConjugationType.IMPERATIVE);
 private static final EnumSet<ConjugationType>N3=with(N4,ConjugationType.PASSIVE,ConjugationType.CAUSATIVE,ConjugationType.CAUSATIVE_PASSIVE);
 private static EnumSet<ConjugationType>with(Set<ConjugationType>b,ConjugationType...x){var r=EnumSet.copyOf(b);r.addAll(List.of(x));return r;}
 public Set<ConjugationType>allowedConjugationTypes(JlptLevel l){return Collections.unmodifiableSet(switch(l){case N5->N5;case N4->N4;case N3->N3;case N2->EnumSet.allOf(ConjugationType.class);});}
 public Set<QuestionType>allowedQuestionTypes(JlptLevel l){return switch(l){case N5->EnumSet.of(QuestionType.DICTIONARY_TO_CONJUGATION,QuestionType.VERB_CLASS,QuestionType.MULTIPLE_CHOICE_CONJUGATION,QuestionType.TRUE_FALSE);case N4->EnumSet.complementOf(EnumSet.of(QuestionType.CONTEXT_SELECTION));case N3,N2->EnumSet.allOf(QuestionType.class);};}
 public DifficultyProfile getDifficultyProfile(JlptLevel l){return switch(l){case N5->new DifficultyProfile(1,false,false,"基础礼貌形、否定、过去和て形");case N4->new DifficultyProfile(2,false,true,"加入可能、意志、命令与条件表达");case N3->new DifficultyProfile(3,false,true,"加入被动、使役、使役被动与语境题");case N2->new DifficultyProfile(4,true,true,"全部活用及尊敬语特殊动词");};}
}
