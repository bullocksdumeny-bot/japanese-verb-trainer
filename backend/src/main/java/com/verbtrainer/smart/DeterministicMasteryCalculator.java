package com.verbtrainer.smart;
import org.springframework.stereotype.Component;import java.time.*;
@Component public class DeterministicMasteryCalculator implements MasteryCalculator{
 public MasteryResult calculate(MasteryState s,boolean ok,Instant now){int a=s.attempts()+1,c=s.correct()+(ok?1:0),w=s.wrong()+(ok?0:1),st=ok?s.streak()+1:0;double accuracy=100.0*c/a;double streak=Math.min(15,st*3);double wrongPenalty=ok?0:15;double decay=s.lastAttempt()==null?0:Math.min(20,Duration.between(s.lastAttempt(),now).toDays()/30.0*5);return new MasteryResult(a,c,w,st,Math.max(0,Math.min(100,accuracy+streak-wrongPenalty-decay)));}
}
