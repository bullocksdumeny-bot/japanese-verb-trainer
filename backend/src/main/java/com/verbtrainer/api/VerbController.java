package com.verbtrainer.api;
import com.verbtrainer.dictionary.*;import com.verbtrainer.conjugation.*;
import com.verbtrainer.learning.*;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import java.util.*;
@RestController @RequestMapping("/api/verbs") public class VerbController {
 private final VerbRepository repo;private final ConjugationService service;private final JmdictImporter importer;private final RecentRepository recent;private final ConjugationLabelProvider labels;
 public VerbController(VerbRepository r,ConjugationService s,JmdictImporter i,RecentRepository recent,ConjugationLabelProvider labels){repo=r;service=s;importer=i;this.recent=recent;this.labels=labels;}
 @GetMapping("/search") public List<VerbEntry> search(@RequestParam String q){
  String query=q.trim();
  if(query.isEmpty())return List.of();
  return repo.searchRanked(query,"%"+query+"%",query+"%",PageRequest.of(0,20));
 }
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable Long id){var v=repo.findById(id).orElseThrow();recent.save(new RecentQuery(id));var forms=service.conjugate(v.lemma,v.verbClass).stream().map(r->{var l=labels.getLabel(r.form());var m=new LinkedHashMap<String,Object>();m.put("type",r.form().name());m.put("japaneseName",l.japaneseName());m.put("chineseName",l.chineseName());m.put("displayName",l.displayName());m.put("explanation",l.explanation());m.put("value",r.value());m.put("steps",r.steps());m.put("exception",r.exception());return m;}).toList();return Map.of("verb",v,"conjugations",forms);}
 @PostMapping(value="/import",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public Map<String,Integer> importJmdict(@RequestPart MultipartFile file)throws Exception{return Map.of("imported",importer.importXml(file.getInputStream()));}
}
