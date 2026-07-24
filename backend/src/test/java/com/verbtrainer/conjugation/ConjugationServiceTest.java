package com.verbtrainer.conjugation;
import org.junit.jupiter.api.*;import java.util.*;import static org.assertj.core.api.Assertions.*;
class ConjugationServiceTest{
 ConjugationService s;
 @BeforeEach void setUp(){s=new ConjugationService(List.of(new GodanStrategy(),new IchidanStrategy(),new SuruStrategy(),new KuruStrategy()));}
 String f(String v,VerbClass c,ConjugationForm f){return s.conjugate(v,c).stream().filter(x->x.form()==f).findFirst().orElseThrow().value();}
 @Test void godanTeTa(){assertThat(f("書く",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("書いて");assertThat(f("読む",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("読んで");assertThat(f("遊ぶ",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("遊んで");assertThat(f("泳ぐ",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("泳いで");assertThat(f("話す",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("話して");assertThat(f("帰る",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("帰って");}
 @Test void ichidan(){assertThat(f("食べる",VerbClass.ICHIDAN,ConjugationForm.MASU)).isEqualTo("食べます");assertThat(f("見る",VerbClass.ICHIDAN,ConjugationForm.NAI)).isEqualTo("見ない");}
 @Test void irregular(){assertThat(f("する",VerbClass.SURU,ConjugationForm.TE)).isEqualTo("して");assertThat(f("勉強する",VerbClass.SURU,ConjugationForm.MASU)).isEqualTo("勉強します");assertThat(f("来る",VerbClass.KURU,ConjugationForm.IMPERATIVE)).isEqualTo("来い");assertThat(f("行く",VerbClass.GODAN,ConjugationForm.TE)).isEqualTo("行って");assertThat(f("ある",VerbClass.GODAN,ConjugationForm.NAI)).isEqualTo("ない");}
}
