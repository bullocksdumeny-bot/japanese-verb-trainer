package com.verbtrainer.conjugation;
import org.springframework.stereotype.Component;
import java.util.*;
@Component public class IchidanStrategy extends AbstractStrategy {
  public VerbClass supports(){return VerbClass.ICHIDAN;}
  public Map<ConjugationForm,ConjugationResult> conjugate(String v){
    var m=new LinkedHashMap<ConjugationForm,ConjugationResult>(); String s=v.substring(0,v.length()-1);
    put(m,ConjugationForm.DICTIONARY,v,"保留原形");
    put(m,ConjugationForm.MASU,s+"ます","去掉「る」","加「ます」");
    put(m,ConjugationForm.MASEN,s+"ません","去掉「る」","加「ません」");
    put(m,ConjugationForm.MASHITA,s+"ました","去掉「る」","加「ました」");
    put(m,ConjugationForm.MASEN_DESHITA,s+"ませんでした","去掉「る」","加「ませんでした」");
    put(m,ConjugationForm.NAI,s+"ない","去掉「る」","加「ない」");
    put(m,ConjugationForm.NAKATTA,s+"なかった","去掉「る」","加「なかった」");
    put(m,ConjugationForm.TE,s+"て","去掉「る」","加「て」");
    put(m,ConjugationForm.TA,s+"た","去掉「る」","加「た」");
    put(m,ConjugationForm.POTENTIAL,s+"られる","去掉「る」","加「られる」");
    put(m,ConjugationForm.PASSIVE,s+"られる","去掉「る」","加「られる」");
    put(m,ConjugationForm.CAUSATIVE,s+"させる","去掉「る」","加「させる」");
    put(m,ConjugationForm.CAUSATIVE_PASSIVE,s+"させられる","去掉「る」","加「させられる」");
    put(m,ConjugationForm.VOLITIONAL,s+"よう","去掉「る」","加「よう」");
    put(m,ConjugationForm.IMPERATIVE,s+"ろ","去掉「る」","加「ろ」");
    put(m,ConjugationForm.PROHIBITIVE,v+"な","原形后加「な」");
    put(m,ConjugationForm.CONDITIONAL,s+"れば","去掉「る」","加「れば」");
    put(m,ConjugationForm.TARA,s+"たら","去掉「る」","加「たら」");
    put(m,ConjugationForm.TAI,s+"たい","去掉「る」","加「たい」"); return m;
  }
}
