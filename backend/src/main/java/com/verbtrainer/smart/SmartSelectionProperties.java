package com.verbtrainer.smart;
import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.stereotype.Component;
@Component @ConfigurationProperties(prefix="training.smart-selection") public class SmartSelectionProperties{
 public double dueReviewRatio=.4,weaknessRatio=.3,newWordRatio=.2,explorationRatio=.1;public double dueReviewWeight=100,weaknessWeight=80,historicalErrorWeight=60,recencyGapWeight=30,noveltyWeight=20,commonnessWeight=.5,masteredPenalty=70,recentRepetitionPenalty=90;public int recentQuestionWindow=30,masteredScoreThreshold=85,masteredCorrectStreak=5;
}
