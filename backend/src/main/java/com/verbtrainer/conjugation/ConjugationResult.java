package com.verbtrainer.conjugation;
import java.util.List;
public record ConjugationResult(ConjugationForm form, String label, String value, List<String> steps, boolean exception) {}
