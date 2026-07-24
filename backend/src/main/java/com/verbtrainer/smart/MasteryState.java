package com.verbtrainer.smart;import java.time.*;
public record MasteryState(int attempts,int correct,int wrong,int streak,double score,Instant lastAttempt,Instant lastWrong){}
