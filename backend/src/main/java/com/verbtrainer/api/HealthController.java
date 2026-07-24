package com.verbtrainer.api;
import org.springframework.web.bind.annotation.*;import java.time.*;import java.util.*;
@RestController public class HealthController{
 @GetMapping("/api/health")public Map<String,Object>health(){return Map.of("status","UP","time",Instant.now());}
}
