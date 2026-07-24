package com.verbtrainer.conjugation;
import org.springframework.stereotype.Component;
import java.util.*;
@Component public class SuruStrategy extends AbstractStrategy {
 public VerbClass supports(){return VerbClass.SURU;}
 public Map<ConjugationForm,ConjugationResult> conjugate(String v){var m=new LinkedHashMap<ConjugationForm,ConjugationResult>();String s=v.substring(0,v.length()-2);
  put(m,ConjugationForm.DICTIONARY,v,"保留原形"); put(m,ConjugationForm.MASU,s+"します","「する」变「します」");put(m,ConjugationForm.MASEN,s+"しません","「する」变「しません」");put(m,ConjugationForm.MASHITA,s+"しました","「する」变「しました」");
  put(m,ConjugationForm.MASEN_DESHITA,s+"しませんでした","「する」变「しませんでした」");
  put(m,ConjugationForm.NAI,s+"しない","「する」变「しない」");put(m,ConjugationForm.NAKATTA,s+"しなかった","「する」变「しなかった」");put(m,ConjugationForm.TE,s+"して","「する」变「して」");put(m,ConjugationForm.TA,s+"した","「する」变「した」");
  put(m,ConjugationForm.POTENTIAL,s+"できる","「する」的可能形是「できる」");put(m,ConjugationForm.PASSIVE,s+"される","「する」变「される」");put(m,ConjugationForm.CAUSATIVE,s+"させる","「する」变「させる」");put(m,ConjugationForm.CAUSATIVE_PASSIVE,s+"させられる","「する」变「させられる」");
  put(m,ConjugationForm.VOLITIONAL,s+"しよう","「する」变「しよう」");put(m,ConjugationForm.IMPERATIVE,s+"しろ","「する」变「しろ」");put(m,ConjugationForm.PROHIBITIVE,v+"な","原形后加「な」");put(m,ConjugationForm.CONDITIONAL,s+"すれば","「する」变「すれば」");put(m,ConjugationForm.TARA,s+"したら","「する」变「したら」");put(m,ConjugationForm.TAI,s+"したい","「する」变「したい」");return m;}
}
