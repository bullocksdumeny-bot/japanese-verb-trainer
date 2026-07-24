package com.verbtrainer.conjugation;

import com.verbtrainer.smart.ConjugationType;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DefaultConjugationLabelProvider implements ConjugationLabelProvider {
  private static final Map<ConjugationType, ConjugationDisplayLabel> LABELS =
      new EnumMap<>(ConjugationType.class);

  static {
    put(ConjugationType.POLITE, "丁寧形（ていねいけい）", "ます形", "ます形（礼貌表达）", "用于礼貌、正式的表达。");
    put(ConjugationType.POLITE_NEGATIVE, "丁寧否定", "ません形", "ません形（礼貌否定）", "礼貌地表示不做某事。");
    put(ConjugationType.POLITE_PAST, "丁寧過去", "ました形", "ました形（礼貌过去）", "礼貌地表示已经发生的动作。");
    put(ConjugationType.POLITE_PAST_NEGATIVE, "丁寧過去否定", "ませんでした形", "ませんでした形", "礼貌地表示过去没有做某事。");
    put(ConjugationType.NAI, "未然形・ない形", "ない形", "ない形（否定）", "表示不做某事。");
    put(ConjugationType.PAST, "過去形・た形", "た形", "た形（过去）", "表示动作已经发生。");
    put(ConjugationType.TE, "て形", "て形", "て形（连接、请求等）", "用于连接动作、提出请求等。");
    put(ConjugationType.POTENTIAL, "可能形（かのうけい）", "可能形", "可能形（能够……）", "表示能够做某事。");
    put(ConjugationType.PASSIVE, "受身形（うけみけい）", "被动形", "被动形（被……）", "表示主语受到某个动作。");
    put(ConjugationType.CAUSATIVE, "使役形（しえきけい）", "使役形", "使役形（让/使……）", "表示让或使某人做某事。");
    put(ConjugationType.CAUSATIVE_PASSIVE, "使役受身形", "使役被动形", "使役被动形", "表示被迫做某事。");
    put(ConjugationType.VOLITIONAL, "意志形（いしけい）", "意志形", "意志形（……吧）", "表示意志、提议或邀请。");
    put(ConjugationType.IMPERATIVE, "命令形（めいれいけい）", "命令形", "命令形", "用于直接命令，语气较强。");
    put(ConjugationType.PROHIBITIVE, "禁止形", "禁止形", "禁止形（不要……）", "表示禁止做某事。");
    put(ConjugationType.CONDITIONAL_BA, "仮定形（ば形）", "ば形", "ば形（条件表达）", "表示假定条件。");
    put(ConjugationType.CONDITIONAL_TARA, "たら形", "たら形", "たら形（如果……的话）", "表示条件或动作完成后的情形。");
    put(ConjugationType.DESIDERATIVE_TAI, "希望形", "たい形", "たい形（想要……）", "表示说话人想做某事。");
  }

  private static void put(ConjugationType type, String japanese, String chinese, String display, String explanation) {
    LABELS.put(type, new ConjugationDisplayLabel(japanese, chinese, display, explanation));
  }

  @Override
  public ConjugationDisplayLabel getLabel(ConjugationType type) {
    var label = LABELS.get(type);
    if (label == null) throw new IllegalArgumentException("缺少活用显示名称: " + type);
    return label;
  }

  @Override
  public ConjugationDisplayLabel getLabel(ConjugationForm form) {
    for (var type : ConjugationType.values()) {
      if (type.form == form) return getLabel(type);
    }
    return switch (form) {
      case DICTIONARY -> new ConjugationDisplayLabel("辞書形（じしょけい）", "原形", "原形（辞书形）", "词典中收录的基本形式。");
      case NAKATTA -> new ConjugationDisplayLabel("なかった形", "なかった形", "なかった形（过去否定）", "表示过去没有做某事。");
      default -> new ConjugationDisplayLabel(form.label, form.label, form.label, "日语动词活用形式。");
    };
  }
}
