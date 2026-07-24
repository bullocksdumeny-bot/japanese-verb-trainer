package com.verbtrainer.api;
import com.verbtrainer.dictionary.*;import com.verbtrainer.conjugation.*;
import com.verbtrainer.learning.*;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.multipart.MultipartFile;
import java.util.*;
@RestController @RequestMapping("/api/verbs") public class VerbController {
 private final VerbRepository repo;private final ConjugationService service;private final JmdictImporter importer;private final RecentRepository recent;
 public VerbController(VerbRepository r,ConjugationService s,JmdictImporter i,RecentRepository recent){repo=r;service=s;importer=i;this.recent=recent;}
 @GetMapping("/search") public List<VerbEntry> search(@RequestParam String q){return repo.findTop20ByLemmaContainingIgnoreCaseOrReadingContainingIgnoreCase(q,q);}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable Long id){var v=repo.findById(id).orElseThrow();recent.save(new RecentQuery(id));return Map.of("verb",v,"conjugations",service.conjugate(v.lemma,v.verbClass));}
 @PostMapping(value="/import",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public Map<String,Integer> importJmdict(@RequestPart MultipartFile file)throws Exception{return Map.of("imported",importer.importXml(file.getInputStream()));}
}
