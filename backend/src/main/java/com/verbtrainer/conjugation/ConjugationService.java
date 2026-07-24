package com.verbtrainer.conjugation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.*;
@Service public class ConjugationService {
 private final Map<VerbClass,ConjugationStrategy> strategies=new EnumMap<>(VerbClass.class); private final Map<String,Map<String,String>> exceptions;
 public ConjugationService(List<ConjugationStrategy> all){all.forEach(s->strategies.put(s.supports(),s)); exceptions=load();}
 @SuppressWarnings("unchecked") private Map<String,Map<String,String>> load(){try(InputStream in=new ClassPathResource("conjugation-exceptions.yml").getInputStream()){return new Yaml().load(in);}catch(Exception e){throw new IllegalStateException(e);}}
 public List<ConjugationResult> conjugate(String lemma,VerbClass type){var base=strategies.get(type).conjugate(lemma);var ex=exceptions.getOrDefault(lemma,Map.of());ex.forEach((k,v)->{var f=ConjugationForm.valueOf(k);var old=base.get(f);base.put(f,new ConjugationResult(f,f.label,v,List.of("命中例外表",old.value()+" → "+v),true));});return new ArrayList<>(base.values());}
}
