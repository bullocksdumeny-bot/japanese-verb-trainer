package com.verbtrainer.api;
import com.verbtrainer.dictionary.*;import com.verbtrainer.learning.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/library") public class LibraryController{
 private final FavoriteRepository favorites;private final RecentRepository recent;private final VerbRepository verbs;
 public LibraryController(FavoriteRepository f,RecentRepository r,VerbRepository v){favorites=f;recent=r;verbs=v;}
 @GetMapping("/favorites")public List<VerbEntry> favorites(){return verbs.findAllById(favorites.findAll().stream().map(x->x.verbId).toList());}
 @PutMapping("/favorites/{id}")public void add(@PathVariable Long id){if(!verbs.existsById(id))throw new NoSuchElementException();favorites.save(new Favorite(id));}
 @DeleteMapping("/favorites/{id}")public void remove(@PathVariable Long id){favorites.deleteById(id);}
 @GetMapping("/recent")public List<VerbEntry> recent(){return recent.findTop10ByOrderByQueriedAtDesc().stream().map(x->verbs.findById(x.verbId).orElse(null)).filter(Objects::nonNull).toList();}
}
