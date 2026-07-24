package com.verbtrainer.dictionary;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.smart.JlptLevel;
import jakarta.persistence.*;
@Entity @Table(name="verb_entry",indexes={@Index(name="idx_verb_lemma",columnList="lemma"),@Index(name="idx_verb_reading",columnList="reading")})
public class VerbEntry {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String lemma;
 @Column(nullable=false) public String reading;
 @Column(nullable=false,length=1000) public String meanings;
 @Enumerated(EnumType.STRING) @Column(nullable=false) public VerbClass verbClass;
 public String jmdictTags;
 @Enumerated(EnumType.STRING) @Column(length=2) public JlptLevel jlptLevel;
 public Integer commonRank;
 protected VerbEntry(){}
 public VerbEntry(String l,String r,String m,VerbClass c,String t){lemma=l;reading=r;meanings=m;verbClass=c;jmdictTags=t;}
}
