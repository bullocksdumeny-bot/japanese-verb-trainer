package com.verbtrainer.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.verbtrainer.conjugation.VerbClass;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class JmdictImporterTest {
    @Test
    void importsExpandedJmdictPartOfSpeechEntity() throws Exception {
        VerbRepository repository = mock(VerbRepository.class);
        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE JMdict [
              <!ENTITY v5k "Godan verb with 'ku' ending">
            ]>
            <JMdict><entry>
              <k_ele><keb>書く</keb></k_ele>
              <r_ele><reb>かく</reb></r_ele>
              <sense><pos>&v5k;</pos><gloss>to write</gloss></sense>
            </entry></JMdict>
            """;

        int imported = new JmdictImporter(repository).importXml(
            new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        assertThat(imported).isEqualTo(1);
        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<VerbEntry>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());
        assertThat(captor.getValue()).singleElement().satisfies(entry -> {
            assertThat(entry.lemma).isEqualTo("書く");
            assertThat(entry.reading).isEqualTo("かく");
            assertThat(entry.verbClass).isEqualTo(VerbClass.GODAN);
        });
    }
}
