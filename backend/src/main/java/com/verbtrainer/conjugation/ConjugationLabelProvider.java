package com.verbtrainer.conjugation;

import com.verbtrainer.smart.ConjugationType;

public interface ConjugationLabelProvider {
  ConjugationDisplayLabel getLabel(ConjugationType type);
  ConjugationDisplayLabel getLabel(ConjugationForm form);
}
