package com.verbtrainer.dictionary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.JlptLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JlptVerbLevelInitializer implements ApplicationRunner {
  private static final Logger log = LoggerFactory.getLogger(JlptVerbLevelInitializer.class);
  private final VerbRepository verbs;
  private final ObjectMapper json;

  public JlptVerbLevelInitializer(VerbRepository verbs, ObjectMapper json) {
    this.verbs = verbs;
    this.json = json;
  }

  public record LevelEntry(String word, String reading, JlptLevel level, int commonRank) {}
  public record UnmatchedEntry(String word, String reading, JlptLevel level, String reason) {}
  public record LevelStats(int total, int matched, int unmatched) {}
  public record InitializationReport(Map<JlptLevel, LevelStats> levels, List<UnmatchedEntry> unmatchedEntries) {}

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws IOException {
    var resource = new ClassPathResource("jlpt-verb-levels.json");
    List<LevelEntry> entries;
    try (var input = resource.getInputStream()) {
      entries = json.readValue(input, new TypeReference<>() {});
    }

    var report = synchronize(entries);
    report.levels().forEach((level, stats) ->
        log.info("JLPT_LEVEL_REPORT level={} total={} matched={} unmatched={}",
            level, stats.total(), stats.matched(), stats.unmatched()));
    report.unmatchedEntries().forEach(entry ->
        log.warn("JLPT_UNMATCHED level={} word={} reading={} reason={}",
            entry.level(), entry.word(), entry.reading(), entry.reason()));
  }

  @Transactional
  public InitializationReport synchronize(List<LevelEntry> entries) {
    verbs.clearJlptClassification();
    var totals = new EnumMap<JlptLevel, Integer>(JlptLevel.class);
    var matched = new EnumMap<JlptLevel, Integer>(JlptLevel.class);
    var unmatched = new ArrayList<UnmatchedEntry>();
    var preferredByWord = new HashMap<String, LevelEntry>();
    for (var entry : entries) preferredByWord.put(entry.word() + "\u0000" + entry.reading(), entry);
    for (var entry : entries) {
      totals.merge(entry.level(), 1, Integer::sum);
      if (entry.commonRank() < 1) {
        throw new IllegalArgumentException("commonRank 必须大于 0：" + entry.word());
      }
      var preferred = preferredByWord.get(entry.word() + "\u0000" + entry.reading());
      if (!entry.equals(preferred)) {
        unmatched.add(new UnmatchedEntry(entry.word(), entry.reading(), entry.level(),
            "等级冲突，保留" + preferred.level()));
        continue;
      }
      var matches = verbs.findByLemmaAndReading(entry.word(), entry.reading());
      String reason = "原形和读音未找到";
      if (matches.isEmpty() && entry.word().endsWith("する") && entry.reading().endsWith("する")
          && entry.word().length() > 2 && entry.reading().length() > 2) {
        var baseWord = entry.word().substring(0, entry.word().length() - 2);
        var baseReading = entry.reading().substring(0, entry.reading().length() - 2);
        matches = verbs.findByLemmaAndReadingAndVerbClass(baseWord, baseReading, VerbClass.SURU);
        reason = "する基础词条未找到";
      }
      if (matches.size() != 1) {
        if (matches.size() > 1) reason = "存在多个候选";
        unmatched.add(new UnmatchedEntry(entry.word(), entry.reading(), entry.level(), reason));
        continue;
      }
      var verb = matches.get(0);
      verb.jlptLevel = entry.level();
      verb.commonRank = entry.commonRank();
      verbs.save(verb);
      matched.merge(entry.level(), 1, Integer::sum);
    }
    var stats = new EnumMap<JlptLevel, LevelStats>(JlptLevel.class);
    for (var level : JlptLevel.values()) {
      int total = totals.getOrDefault(level, 0);
      int success = matched.getOrDefault(level, 0);
      stats.put(level, new LevelStats(total, success, total - success));
    }
    return new InitializationReport(Map.copyOf(stats), List.copyOf(unmatched));
  }
}
