package com.verbtrainer.ruletraining;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.verbtrainer.VerbTrainerApplication;
import com.verbtrainer.conjugation.VerbClass;
import com.verbtrainer.dictionary.VerbEntry;
import com.verbtrainer.dictionary.VerbRepository;
import com.verbtrainer.smart.JlptLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes=VerbTrainerApplication.class) @AutoConfigureMockMvc
class RuleTrainingApiTest {
    @Autowired MockMvc mvc;@Autowired VerbRepository verbs;VerbEntry reading;
    @BeforeEach void seed(){reading=new VerbEntry("読む","よむ","read",VerbClass.GODAN,"v5m");reading.jlptLevel=JlptLevel.N5;reading.commonRank=1;reading=verbs.save(reading);}
    @Test void questionDoesNotExposeAnswers()throws Exception{
        mvc.perform(post("/api/v1/rule-training/question").contentType(MediaType.APPLICATION_JSON).content("{\"mode\":\"FULL\",\"excludeVerbIds\":[]}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.categoryOptions").isArray()).andExpect(jsonPath("$.ruleOptions").isArray())
            .andExpect(jsonPath("$.correctAnswer").doesNotExist()).andExpect(jsonPath("$.conjugation").doesNotExist());
    }
    @Test void explainsMuBuNuRuleAndUpdatesStageStatistics()throws Exception{
        mvc.perform(post("/api/v1/rule-training/answer").contentType(MediaType.APPLICATION_JSON)
            .content("{\"verbId\":"+reading.id+",\"stage\":\"RULE\",\"answer\":\"む・ぶ・ぬ → んで\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.conjugation").value("読んで")).andExpect(jsonPath("$.ruleCode").value("MU_BU_NU_TO_NDE"));
        mvc.perform(get("/api/v1/rule-training/stats")).andExpect(status().isOk()).andExpect(jsonPath("$.rule.attempts").value(org.hamcrest.Matchers.greaterThan(0)));
    }
}
