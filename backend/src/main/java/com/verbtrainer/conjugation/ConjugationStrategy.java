package com.verbtrainer.conjugation;
import java.util.Map;
public interface ConjugationStrategy {
  VerbClass supports();
  Map<ConjugationForm, ConjugationResult> conjugate(String verb);
}
