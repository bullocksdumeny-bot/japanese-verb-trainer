package com.verbtrainer.conjugation;
import java.util.*;
abstract class AbstractStrategy implements ConjugationStrategy {
  protected ConjugationResult r(ConjugationForm f,String v,String... steps){
    return new ConjugationResult(f,f.label,v,List.of(steps),false);
  }
  protected void put(Map<ConjugationForm,ConjugationResult> m, ConjugationForm f,String v,String...s){m.put(f,r(f,v,s));}
}
