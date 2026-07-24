package com.verbtrainer.api;
import com.verbtrainer.VerbTrainerApplication;import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(classes=VerbTrainerApplication.class) @AutoConfigureMockMvc class VerbApiTest{
 @Autowired MockMvc mvc; @Test void searchWorks()throws Exception{mvc.perform(get("/api/verbs/search").param("q","帰る")).andExpect(status().isOk());}
}
