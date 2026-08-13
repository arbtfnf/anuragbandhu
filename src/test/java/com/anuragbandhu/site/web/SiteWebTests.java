package com.anuragbandhu.site.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SiteWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homeRendersNameAndNotebook() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Anurag Rakesh Bandhu")))
                .andExpect(content().string(containsString("The Bangalore Notebook")))
                .andExpect(content().string(containsString("Download LaTeX resume")));
    }

    @Test
    void resumePreviewAndLatexDownload() throws Exception {
        mockMvc.perform(get("/resume"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Download LaTeX")))
                .andExpect(content().string(containsString("Netsmart")));

        mockMvc.perform(get("/resume.tex"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType("application/x-tex")))
                .andExpect(header().string("Content-Disposition", containsString("Anurag_Rakesh_Bandhu_Resume.tex")))
                .andExpect(content().string(containsString("\\documentclass")))
                .andExpect(content().string(containsString("Netsmart")))
                .andExpect(content().string(containsString("\\%")));
    }

    @Test
    void apiReturnsCatalog() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.name").value("Anurag Rakesh Bandhu"))
                .andExpect(jsonPath("$.experience[0].company").value("Netsmart"))
                .andExpect(jsonPath("$.projects[0].name").value("The Bangalore Notebook"));
    }
}
