package com.verbtrainer.conjugation;
import org.springframework.stereotype.Component;
import java.util.*;
@Component public class GodanStrategy extends AbstractStrategy {
  private static final Map<Character,String[]> ROW=Map.of(
    'う',new String[]{"わ","い","う","え","お"},'く',new String[]{"か","き","く","け","こ"},
    'ぐ',new String[]{"が","ぎ","ぐ","げ","ご"},'す',new String[]{"さ","し","す","せ","そ"},
    'つ',new String[]{"た","ち","つ","て","と"},'ぬ',new String[]{"な","に","ぬ","ね","の"},
    'ぶ',new String[]{"ば","び","ぶ","べ","ぼ"},'む',new String[]{"ま","み","む","め","も"},
    'る',new String[]{"ら","り","る","れ","ろ"});
  public VerbClass supports(){return VerbClass.GODAN;}
  public Map<ConjugationForm,ConjugationResult> conjugate(String v){
    var m=new LinkedHashMap<ConjugationForm,ConjugationResult>(); char e=v.charAt(v.length()-1); String stem=v.substring(0,v.length()-1); String[] row=ROW.get(e);
    String te,ta; if("うつる".indexOf(e)>=0){te=stem+"って";ta=stem+"った";} else if("ぬぶむ".indexOf(e)>=0){te=stem+"んで";ta=stem+"んだ";} else if(e=='く'){te=stem+"いて";ta=stem+"いた";} else if(e=='ぐ'){te=stem+"いで";ta=stem+"いだ";} else {te=stem+"して";ta=stem+"した";}
    put(m,ConjugationForm.DICTIONARY,v,"保留原形"); put(m,ConjugationForm.MASU,stem+row[1]+"ます","识别词尾「"+e+"」","变为い段「"+row[1]+"」","加「ます」");
    put(m,ConjugationForm.MASEN,stem+row[1]+"ません","词尾变い段","加「ません」"); put(m,ConjugationForm.MASHITA,stem+row[1]+"ました","词尾变い段","加「ました」");
    put(m,ConjugationForm.MASEN_DESHITA,stem+row[1]+"ませんでした","词尾变い段","加「ませんでした」");
    put(m,ConjugationForm.NAI,stem+row[0]+"ない","词尾变あ段「"+row[0]+"」","加「ない」"); put(m,ConjugationForm.NAKATTA,stem+row[0]+"なかった","词尾变あ段","加「なかった」");
    put(m,ConjugationForm.TE,te,"按五段音便规则变化"); put(m,ConjugationForm.TA,ta,"て形音便后将て/で变为た/だ");
    put(m,ConjugationForm.POTENTIAL,stem+row[3]+"る","词尾变え段","加「る」"); put(m,ConjugationForm.PASSIVE,stem+row[0]+"れる","词尾变あ段","加「れる」");
    put(m,ConjugationForm.CAUSATIVE,stem+row[0]+"せる","词尾变あ段","加「せる」"); put(m,ConjugationForm.CAUSATIVE_PASSIVE,stem+row[0]+"せられる","词尾变あ段","加「せられる」");
    put(m,ConjugationForm.VOLITIONAL,stem+row[4]+"う","词尾变お段","加「う」"); put(m,ConjugationForm.IMPERATIVE,stem+row[3],"词尾变え段");
    put(m,ConjugationForm.PROHIBITIVE,v+"な","原形后加「な」"); put(m,ConjugationForm.CONDITIONAL,stem+row[3]+"ば","词尾变え段","加「ば」");
    put(m,ConjugationForm.TARA,ta+"ら","た形后加「ら」");
    put(m,ConjugationForm.TAI,stem+row[1]+"たい","词尾变い段","加「たい」"); return m;
  }
}
