package com.verbtrainer.knowledge;

import com.verbtrainer.VerbTrainerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VerbTrainerApplication.class)
@AutoConfigureMockMvc
class KnowledgePointApiTest {
    @Autowired MockMvc mvc;

    @Test
    void n5CatalogProvidesTeachableRuleDetails() throws Exception {
        mvc.perform(get("/api/v1/knowledge-points").param("level", "N5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.code == 'N5_POLITE_GODAN')].name").value("五段动词ます形"))
            .andExpect(jsonPath("$[?(@.code == 'N5_TE_ICHIDAN')].formula").value("去る + て"));

        mvc.perform(get("/api/v1/knowledge-points/N5_POLITE_GODAN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.identificationRule").isNotEmpty())
            .andExpect(jsonPath("$.examples").isArray())
            .andExpect(jsonPath("$.commonMistakes[0]").value("書くます ❌"));
    }

    @Test
    void unknownKnowledgePointReturnsNotFound() throws Exception {
        mvc.perform(get("/api/v1/knowledge-points/UNKNOWN"))
            .andExpect(status().isNotFound());
    }
}
