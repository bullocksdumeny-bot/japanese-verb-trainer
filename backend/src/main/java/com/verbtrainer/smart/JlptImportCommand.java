package com.verbtrainer.smart;
import com.fasterxml.jackson.databind.ObjectMapper;import org.springframework.boot.*;import org.springframework.stereotype.Component;import java.nio.file.*;
@Component public class JlptImportCommand implements ApplicationRunner{
 private final JlptLevelImporter importer;private final ObjectMapper json;public JlptImportCommand(JlptLevelImporter i,ObjectMapper j){importer=i;json=j;}
 public void run(ApplicationArguments args)throws Exception{if(!args.containsOption("import-jlpt-levels"))return;String file=args.getOptionValues("import-jlpt-levels").get(0);try(var in=Files.newInputStream(Path.of(file))){System.out.println(json.writerWithDefaultPrettyPrinter().writeValueAsString(importer.importFile(in)));}}
}
