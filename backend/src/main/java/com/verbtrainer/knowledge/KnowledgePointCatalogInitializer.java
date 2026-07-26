package com.verbtrainer.knowledge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.ConjugationType;
import com.verbtrainer.smart.JlptLevel;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KnowledgePointCatalogInitializer implements ApplicationRunner {
    private final RuleKnowledgePointRepository repository;
    private final ObjectMapper json;

    public KnowledgePointCatalogInitializer(RuleKnowledgePointRepository repository, ObjectMapper json) {
        this.repository = repository; this.json = json;
    }

    @Override @Transactional
    public void run(ApplicationArguments args) {
        definitions().forEach(def -> {
            KnowledgePoint point = repository.findByCodeAndActiveTrue(def.code).orElseGet(KnowledgePoint::new);
            point.code = def.code; point.name = def.name; point.jlptLevel = def.level;
            point.conjugationType = def.form; point.verbClass = def.verbClass;
            point.summary = def.summary; point.identificationRule = def.identification;
            point.transformationFormula = def.formula; point.explanation = def.explanation;
            point.examplesJson = write(def.examples); point.commonMistakesJson = write(def.mistakes);
            point.displayOrder = def.order; point.active = true;
            repository.save(point);
        });
    }

    private List<Definition> definitions() {
        return List.of(
            d("N5_POLITE_GODAN", "五段动词ます形", ConjugationType.POLITE, VerbClass.GODAN,
              "把词尾从う段变为对应い段，再加「ます」。",
              "先确认动词属于五段；词尾是う、く、ぐ、す、つ、ぬ、ぶ、む、る之一。",
              "词尾う段 → 对应い段 + ます",
              "五段动词不是直接加ます。先移动最后一个假名所在的行。",
              List.of("書く → 書きます", "読む → 読みます", "話す → 話します"),
              List.of("書くます ❌", "読むます ❌"), 10),
            d("N5_POLITE_ICHIDAN", "一段动词ます形", ConjugationType.POLITE, VerbClass.ICHIDAN,
              "去掉词尾「る」，再加「ます」。",
              "由 JMdict 词性确认是一段动词，不能只凭「る」结尾猜测。",
              "词干 + ます（去る + ます）",
              "一段动词的词干保持不变，删除最后的「る」。",
              List.of("食べる → 食べます", "見る → 見ます", "起きる → 起きます"),
              List.of("食べるます ❌", "食べります ❌"), 11),
            d("N5_POLITE_SURU", "する动词ます形", ConjugationType.POLITE, VerbClass.SURU,
              "把「する」变为「します」。", "JMdict 标记为 vs 的する动词。",
              "する → します", "复合する动词保留前面的名词部分。",
              List.of("する → します", "勉強する → 勉強します"),
              List.of("勉強するます ❌"), 12),
            d("N5_POLITE_KURU", "来る的ます形", ConjugationType.POLITE, VerbClass.KURU,
              "来る是不规则动词，变为「来ます（きます）」。", "JMdict 标记为 vk。",
              "来る（くる）→ 来ます（きます）", "注意汉字不变，但读音从「く」变为「き」。",
              List.of("来る → 来ます"), List.of("来ります ❌", "来るます ❌"), 13),
            d("N5_TE_GODAN", "五段动词て形", ConjugationType.TE, VerbClass.GODAN,
              "根据词尾应用促音便、拨音便或イ音便。",
              "确认是五段动词，再按最后一个假名选择规则；「行く」单独记忆。",
              "う・つ・る→って；む・ぶ・ぬ→んで；く→いて；ぐ→いで；す→して",
              "五段て形不是单一公式，必须识别词尾组。",
              List.of("帰る → 帰って", "読む → 読んで", "書く → 書いて", "泳ぐ → 泳いで", "話す → 話して", "行く → 行って"),
              List.of("読みて ❌", "行いて ❌"), 20),
            d("N5_TE_ICHIDAN", "一段动词て形", ConjugationType.TE, VerbClass.ICHIDAN,
              "去掉「る」，加「て」。", "由词典词性确认是一段动词。",
              "去る + て", "词干不发生音便。",
              List.of("食べる → 食べて", "見る → 見て"),
              List.of("食べって ❌", "見って ❌"), 21),
            d("N5_TE_SURU", "する动词て形", ConjugationType.TE, VerbClass.SURU,
              "把「する」变为「して」。", "JMdict 标记为 vs 的する动词。",
              "する → して", "复合动词保留前面的名词部分。",
              List.of("する → して", "勉強する → 勉強して"),
              List.of("勉強すって ❌"), 22),
            d("N5_TE_KURU", "来る的て形", ConjugationType.TE, VerbClass.KURU,
              "来る是不规则动词，变为「来て（きて）」。", "JMdict 标记为 vk。",
              "来る（くる）→ 来て（きて）", "读音发生变化，需要整体记忆。",
              List.of("来る → 来て"), List.of("来って ❌"), 23)
        );
    }

    private Definition d(String code, String name, ConjugationType form, VerbClass verbClass,
                         String summary, String identification, String formula, String explanation,
                         List<String> examples, List<String> mistakes, int order) {
        return new Definition(code, name, JlptLevel.N5, form, verbClass, summary, identification,
            formula, explanation, examples, mistakes, order);
    }

    private String write(Object value) {
        try { return json.writeValueAsString(value); }
        catch (Exception e) { throw new IllegalStateException("无法写入知识点目录", e); }
    }

    private record Definition(String code, String name, JlptLevel level, ConjugationType form,
                              VerbClass verbClass, String summary, String identification,
                              String formula, String explanation, List<String> examples,
                              List<String> mistakes, int order) {}
}
