package com.verbtrainer.conjugation;
public enum ConjugationForm {
  DICTIONARY("原形"), MASU("ます形"), MASEN("ません形"), MASHITA("ました形"), MASEN_DESHITA("ませんでした形"),
  NAI("ない形"), NAKATTA("なかった形"), TE("て形"), TA("た形"),
  POTENTIAL("可能形"), PASSIVE("被动形"), CAUSATIVE("使役形"),
  CAUSATIVE_PASSIVE("使役被动形"), VOLITIONAL("意志形"), IMPERATIVE("命令形"),
  PROHIBITIVE("禁止形"), CONDITIONAL("ば条件形"), TARA("たら条件形"), TAI("たい形");
  public final String label;
  ConjugationForm(String label){this.label=label;}
}
