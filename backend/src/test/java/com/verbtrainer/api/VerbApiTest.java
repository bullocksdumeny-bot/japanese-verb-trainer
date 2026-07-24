package com.verbtrainer.api;
import com.verbtrainer.VerbTrainerApplication;import com.verbtrainer.conjugation.VerbClass;import com.verbtrainer.dictionary.*;import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(classes=VerbTrainerApplication.class) @AutoConfigureMockMvc class VerbApiTest{
 @Autowired MockMvc mvc;@Autowired VerbRepository verbs; @Test void searchWorks()throws Exception{mvc.perform(get("/api/verbs/search").param("q","帰る")).andExpect(status().isOk());}
 @Test void conjugationsExposeFriendlyDisplayFields()throws Exception{var verb=verbs.findFirstByLemma("読む").orElseGet(()->verbs.save(new VerbEntry("読む","よむ","读",VerbClass.GODAN,"v5m")));mvc.perform(get("/api/verbs/"+verb.id)).andExpect(status().isOk()).andExpect(jsonPath("$.conjugations[?(@.type == 'TE')].displayName").value("て形（连接、请求等）")).andExpect(jsonPath("$.conjugations[?(@.type == 'TE')].chineseName").value("て形")).andExpect(jsonPath("$.conjugations[?(@.type == 'TE')].value").value("読んで"));}
}
