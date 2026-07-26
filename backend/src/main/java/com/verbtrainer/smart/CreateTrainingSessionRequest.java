package com.verbtrainer.smart;
import jakarta.validation.constraints.*;import java.util.*;
public record CreateTrainingSessionRequest(@NotNull JlptLevel jlptLevel,@Min(10)@Max(50)int questionCount,@NotNull TrainingMode mode,
 Set<QuestionType>questionTypes,Set<ConjugationType>conjugationTypes,boolean prioritizeWeakness,boolean prioritizeDueReview,
 boolean includeNewWords,boolean commonWordsOnly,boolean showReading,boolean aiEnhancementEnabled,
 String knowledgePointCode){}
