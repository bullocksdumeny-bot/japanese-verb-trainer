package com.verbtrainer.learning;
import jakarta.persistence.*;import java.time.*;
@Entity public class RecentQuery{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)public Long id;public Long verbId;public LocalDateTime queriedAt=LocalDateTime.now();protected RecentQuery(){}public RecentQuery(Long v){verbId=v;}}
