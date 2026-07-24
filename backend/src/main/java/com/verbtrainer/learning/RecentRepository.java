package com.verbtrainer.learning;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface RecentRepository extends JpaRepository<RecentQuery,Long>{List<RecentQuery> findTop10ByOrderByQueriedAtDesc();}
