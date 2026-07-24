package com.verbtrainer.conjugation;
import org.springframework.stereotype.Component;
import java.util.*;
@Component public class KuruStrategy extends AbstractStrategy {
 public VerbClass supports(){return VerbClass.KURU;}
 public Map<ConjugationForm,ConjugationResult> conjugate(String v){var m=new LinkedHashMap<ConjugationForm,ConjugationResult>();
  put(m,ConjugationForm.DICTIONARY,v,"保留原形");put(m,ConjugationForm.MASU,"来ます","特殊变化");put(m,ConjugationForm.MASEN,"来ません","特殊变化");put(m,ConjugationForm.MASHITA,"来ました","特殊变化");put(m,ConjugationForm.MASEN_DESHITA,"来ませんでした","特殊变化");put(m,ConjugationForm.NAI,"来ない","特殊变化");
  put(m,ConjugationForm.NAKATTA,"来なかった","特殊变化");put(m,ConjugationForm.TE,"来て","特殊变化");put(m,ConjugationForm.TA,"来た","特殊变化");put(m,ConjugationForm.POTENTIAL,"来られる","特殊变化");put(m,ConjugationForm.PASSIVE,"来られる","特殊变化");
  put(m,ConjugationForm.CAUSATIVE,"来させる","特殊变化");put(m,ConjugationForm.CAUSATIVE_PASSIVE,"来させられる","特殊变化");put(m,ConjugationForm.VOLITIONAL,"来よう","特殊变化");put(m,ConjugationForm.IMPERATIVE,"来い","特殊变化");put(m,ConjugationForm.PROHIBITIVE,"来るな","原形后加「な」");put(m,ConjugationForm.CONDITIONAL,"来れば","特殊变化");put(m,ConjugationForm.TARA,"来たら","特殊变化");put(m,ConjugationForm.TAI,"来たい","特殊变化");return m;}
}
