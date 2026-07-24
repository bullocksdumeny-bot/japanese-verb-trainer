package com.verbtrainer.dictionary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verbtrainer.smart.JlptLevel;
import java.io.IOException;
import java.util.List;
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

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws IOException {
    var resource = new ClassPathResource("jlpt-verb-levels.json");
    List<LevelEntry> entries;
    try (var input = resource.getInputStream()) {
      entries = json.readValue(input, new TypeReference<>() {});
    }

    verbs.clearJlptClassification();
    int assigned = 0;
    int skipped = 0;
    for (var entry : entries) {
      if (entry.commonRank() < 1) {
        throw new IllegalArgumentException("commonRank 必须大于 0：" + entry.word());
      }
      var matches = verbs.findByLemmaAndReading(entry.word(), entry.reading());
      if (matches.size() != 1) {
        skipped++;
        continue;
      }
      var verb = matches.getFirst();
      verb.jlptLevel = entry.level();
      verb.commonRank = entry.commonRank();
      verbs.save(verb);
      assigned++;
    }
    log.info("JLPT 动词等级初始化完成：配置 {} 条，匹配并更新 {} 条，未匹配或不唯一 {} 条", entries.size(), assigned, skipped);
  }
}
