package com.verbtrainer.smart;
import com.verbtrainer.conjugation.ConjugationForm;
public enum ConjugationType {
 POLITE(ConjugationForm.MASU),POLITE_NEGATIVE(ConjugationForm.MASEN),POLITE_PAST(ConjugationForm.MASHITA),POLITE_PAST_NEGATIVE(ConjugationForm.MASEN_DESHITA),
 NAI(ConjugationForm.NAI),PAST(ConjugationForm.TA),TE(ConjugationForm.TE),POTENTIAL(ConjugationForm.POTENTIAL),PASSIVE(ConjugationForm.PASSIVE),
 CAUSATIVE(ConjugationForm.CAUSATIVE),CAUSATIVE_PASSIVE(ConjugationForm.CAUSATIVE_PASSIVE),VOLITIONAL(ConjugationForm.VOLITIONAL),
 IMPERATIVE(ConjugationForm.IMPERATIVE),CONDITIONAL_BA(ConjugationForm.CONDITIONAL),CONDITIONAL_TARA(ConjugationForm.TARA),
 DESIDERATIVE_TAI(ConjugationForm.TAI),PROHIBITIVE(ConjugationForm.PROHIBITIVE);
 public final ConjugationForm form; ConjugationType(ConjugationForm form){this.form=form;}
}
