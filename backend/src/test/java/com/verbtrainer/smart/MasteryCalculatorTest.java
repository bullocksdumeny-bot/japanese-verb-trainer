package com.verbtrainer.smart;
import org.junit.jupiter.api.Test;import java.time.*;import static org.assertj.core.api.Assertions.*;
class MasteryCalculatorTest{
 @Test void wrongAnswerResetsStreakAndLowersScore(){var c=new DeterministicMasteryCalculator();var r=c.calculate(new MasteryState(4,4,0,4,95,Instant.now().minusSeconds(60),null),false,Instant.now());assertThat(r.wrong()).isEqualTo(1);assertThat(r.streak()).isZero();assertThat(r.score()).isLessThan(95);}
 @Test void repeatedCorrectAnswersIncreaseMastery(){var c=new DeterministicMasteryCalculator();var r=c.calculate(new MasteryState(2,1,1,0,35,null,null),true,Instant.now());assertThat(r.score()).isGreaterThan(35);}
}
