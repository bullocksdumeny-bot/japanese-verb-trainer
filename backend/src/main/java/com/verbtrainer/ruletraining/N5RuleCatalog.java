package com.verbtrainer.ruletraining;

import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.dictionary.VerbEntry;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class N5RuleCatalog {
    public record Rule(String code,String label,String explanation,List<String> examples,String masteryKey){}
    private final List<Rule> rules=List.of(
        new Rule("ICHIDAN_TE","去る + て","一段动词去掉词尾「る」，再加「て」。",List.of("食べる → 食べて","見る → 見て"),"GODAN_ENDING_RULE:ICHIDAN_REMOVE_RU_TE"),
        new Rule("U_TSU_RU_TO_TTE","う・つ・る → って","五段动词以「う、つ、る」结尾时，て形变为「って」。",List.of("買う → 買って","待つ → 待って","帰る → 帰って"),"GODAN_ENDING_RULE:U_TSU_RU_TO_TTE"),
        new Rule("MU_BU_NU_TO_NDE","む・ぶ・ぬ → んで","五段动词以「む、ぶ、ぬ」结尾时，て形变为「んで」。",List.of("読む → 読んで","遊ぶ → 遊んで","死ぬ → 死んで"),"GODAN_ENDING_RULE:MU_BU_NU_TO_NDE"),
        new Rule("KU_TO_ITE","く → いて","五段动词以「く」结尾时，て形通常变为「いて」。",List.of("書く → 書いて","聞く → 聞いて"),"GODAN_ENDING_RULE:KU_TO_ITE"),
        new Rule("GU_TO_IDE","ぐ → いで","五段动词以「ぐ」结尾时，て形变为「いで」。",List.of("泳ぐ → 泳いで"),"GODAN_ENDING_RULE:GU_TO_IDE"),
        new Rule("SU_TO_SHITE","す → して","五段动词以「す」结尾时，て形变为「して」。",List.of("話す → 話して"),"GODAN_ENDING_RULE:SU_TO_SHITE"),
        new Rule("IKU_TO_ITTE","特殊：行く → 行って","「行く」是特殊音便，不能套用「く → いて」。",List.of("行く → 行って"),"GODAN_ENDING_RULE:IKU_TO_ITTE"),
        new Rule("SURU_TO_SHITE","する → して","する动词的て形是「して」。",List.of("する → して","勉強する → 勉強して"),"GODAN_ENDING_RULE:SURU_TO_SHITE"),
        new Rule("KURU_TO_KITE","来る → 来て","来る是不规则动词，て形读作「きて」。",List.of("来る → 来て"),"GODAN_ENDING_RULE:KURU_TO_KITE")
    );
    private final Map<String,Rule> byCode=rules.stream().collect(java.util.stream.Collectors.toUnmodifiableMap(Rule::code,x->x));
    public List<Rule> all(){return rules;}
    public Rule forVerb(VerbEntry v){
        if(v.lemma.equals("行く"))return byCode.get("IKU_TO_ITTE");
        if(v.verbClass==VerbClass.ICHIDAN)return byCode.get("ICHIDAN_TE");
        if(v.verbClass==VerbClass.SURU)return byCode.get("SURU_TO_SHITE");
        if(v.verbClass==VerbClass.KURU)return byCode.get("KURU_TO_KITE");
        char e=v.lemma.charAt(v.lemma.length()-1);
        return byCode.get("むぶぬ".indexOf(e)>=0?"MU_BU_NU_TO_NDE":"うつる".indexOf(e)>=0?"U_TSU_RU_TO_TTE":e=='く'?"KU_TO_ITE":e=='ぐ'?"GU_TO_IDE":"SU_TO_SHITE");
    }
    public Rule byCode(String code){return byCode.get(code);}
    public String category(VerbClass c){return switch(c){case ICHIDAN->"一段动词";case GODAN->"五段动词";case SURU->"する动词";case KURU->"来る动词";default->"特殊动词";};}
    public List<String> categoryOptions(){return List.of("一段动词","五段动词","する动词","来る动词");}
    public List<Map<String,Object>> display(){return rules.stream().map(r->Map.<String,Object>of("code",r.code,"label",r.label,"explanation",r.explanation,"examples",r.examples)).toList();}
}
