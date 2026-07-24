package com.verbtrainer.smart;import java.util.*;
public interface JlptTrainingPolicy{Set<ConjugationType> allowedConjugationTypes(JlptLevel level);Set<QuestionType> allowedQuestionTypes(JlptLevel level);DifficultyProfile getDifficultyProfile(JlptLevel level);}
