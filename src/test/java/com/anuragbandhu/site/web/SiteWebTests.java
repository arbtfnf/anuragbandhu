package com.anuragbandhu.site.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(content().string(containsString("ClaudeGravity")))
                .andExpect(content().string(containsString("github.com/arbtfnf/claudegravity")))
                .andExpect(content().string(containsString("Download LaTeX resume")))
                .andExpect(content().string(containsString("Tagged from Medium")))
                .andExpect(content().string(containsString("CommScope")))
                .andExpect(content().string(containsString("Software Engineering Intern")))
                .andExpect(content().string(containsString("Open source contributor")))
                .andExpect(content().string(containsString("techNIEks")))
                .andExpect(content().string(containsString("Techkriti")))
                .andExpect(content().string(containsString("IIT Kanpur")))
                .andExpect(content().string(containsString("25 Feb 2018")))
                .andExpect(content().string(containsString("Credit Saison")))
                .andExpect(content().string(containsString("co-lending")))
                .andExpect(content().string(containsString("leetcode.com/anuragbandhu007")))
                .andExpect(content().string(containsString("/work/trippe")))
                .andExpect(content().string(containsString("8,934ms")))
                .andExpect(content().string(containsString("data-nav-toggle")))
                .andExpect(content().string(containsString("data-theme=\"night\"")))
                .andExpect(content().string(not(containsString("85532"))))
                .andExpect(content().string(not(containsString("telephone"))));
    }

    @Test
    void trippeCaseStudyRendersIndexingAndRedis() throws Exception {
        mockMvc.perform(get("/work/trippe"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Trippe World")))
                .andExpect(content().string(containsString("8,934ms")))
                .andExpect(content().string(containsString("Fifteen travel personas")))
                .andExpect(content().string(containsString("JSONB")))
                .andExpect(content().string(containsString("Thursday")))
                .andExpect(content().string(not(containsString("85532"))));
    }

    @Test
    void resumePreviewAndLatexDownload() throws Exception {
        mockMvc.perform(get("/resume"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Download LaTeX")))
                .andExpect(content().string(containsString("Download PDF")))
                .andExpect(content().string(not(containsString("Professional Summary"))))
                .andExpect(content().string(not(containsString("data-resume-print"))))
                .andExpect(content().string(containsString("Netsmart")))
                .andExpect(content().string(not(containsString("Open Source Contribution"))))
                .andExpect(content().string(not(containsString("Ceph"))))
                .andExpect(content().string(not(containsString("Earlier"))))
                .andExpect(content().string(not(containsString("CommScope"))))
                .andExpect(content().string(not(containsString("Red Hat"))))
                .andExpect(content().string(not(containsString("techNIEks Hackathon 2018"))))
                .andExpect(content().string(containsString("AI Agent Workflow Framework")))
                .andExpect(content().string(not(containsString("Building a 100/100 Workflow Agent From Scratch"))))
                .andExpect(content().string(not(containsString("Prompt Engineering Journey"))))
                .andExpect(content().string(containsString("medium.com")))
                .andExpect(content().string(containsString("Saison Omni")))
                .andExpect(content().string(containsString("co-lending")))
                .andExpect(content().string(containsString("Techkriti")))
                .andExpect(content().string(not(containsString("ClaudeGravity"))))
                .andExpect(content().string(not(containsString("Amazon Q"))))
                .andExpect(content().string(containsString("OpenSearch")))
                .andExpect(content().string(containsString("FHIR")))
                .andExpect(content().string(containsString("Java 21, Spring Boot 3.x, OpenSearch, AWS, FHIR")))
                .andExpect(content().string(not(containsString("CareFabric, Java"))))
                .andExpect(content().string(not(containsString("Technical mentorship"))))
                .andExpect(content().string(not(containsString("onboarded 3 junior"))))
                .andExpect(content().string(containsString("travel itinerary planner")))
                .andExpect(content().string(containsString("Backend &amp; Architecture")))
                .andExpect(content().string(containsString("HTI-1")))
                .andExpect(content().string(containsString("74-repository")))
                .andExpect(content().string(containsString("1.39 million")))
                .andExpect(content().string(containsString("3 days")))
                .andExpect(content().string(containsString("18 hours")))
                .andExpect(content().string(containsString("Oct 2024 to May 2025")))
                .andExpect(content().string(containsString("Feb 2024 to Oct 2024")))
                .andExpect(content().string(containsString("Jul 2021 to Nov 2023")))
                .andExpect(content().string(not(containsString("withdrew"))))
                .andExpect(content().string(not(containsString("Loom"))))
                .andExpect(content().string(not(containsString("PRISM"))))
                .andExpect(content().string(not(containsString("8,934ms"))))
                .andExpect(content().string(not(containsString("Android"))))
                .andExpect(content().string(containsString("85532")))
                .andExpect(content().string(containsString("class=\"metric\"")))
                .andExpect(content().string(containsString("~3 billion")))
                .andExpect(content().string(containsString("The National Institute of Engineering, Mysuru")))
                .andExpect(content().string(containsString("BE in Computer Science")))
                .andExpect(content().string(containsString("paper-dates\">2020</span>")))
                .andExpect(content().string(not(containsString("Bachelor of Engineering, Computer Science"))))
                .andExpect(content().string(not(containsString("Aug 2016 to Jun 2020"))));

        mockMvc.perform(get("/resume.pdf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", containsString("Anurag_Rakesh_Bandhu_Resume.pdf")));

        mockMvc.perform(get("/resume.tex"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/resume?download=1")));

        mockMvc.perform(post("/resume.tex")
                        .param("email", "recruiter@example.com")
                        .param("name", "Test Recruiter")
                        .param("source", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType("application/x-tex")))
                .andExpect(header().string("Content-Disposition", containsString("Anurag_Rakesh_Bandhu_Resume.tex")))
                .andExpect(content().string(containsString("\\documentclass")))
                .andExpect(content().string(containsString("Netsmart")))
                .andExpect(content().string(not(containsString("Open Source Contribution"))))
                .andExpect(content().string(not(containsString("Ceph"))))
                .andExpect(content().string(not(containsString("CommScope"))))
                .andExpect(content().string(not(containsString("Software Engineering Intern"))))
                .andExpect(content().string(containsString("85532")))
                .andExpect(content().string(containsString("\\%")))
                .andExpect(content().string(containsString("\\textbf{")));
    }

    @Test
    void apiReturnsCatalog() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.name").value("Anurag Rakesh Bandhu"))
                .andExpect(jsonPath("$.experience[0].company").value("Netsmart"))
                .andExpect(jsonPath("$.projects[0].name").value("The Bangalore Notebook"))
                .andExpect(jsonPath("$.projects[1].name").value("ClaudeGravity"))
                .andExpect(jsonPath("$.writing[0].venue").value("Medium"))
                .andExpect(jsonPath("$.writing[0].tags[0]").value("ai-agent"))
                .andExpect(jsonPath("$.person.leetcodeHandle").value("anuragbandhu007"))
                .andExpect(jsonPath("$.hackathons[0].name").value("Yes Bank Datathon"))
                .andExpect(jsonPath("$.hackathons[0].result").value("Winner"))
                .andExpect(jsonPath("$.person.phoneDisplay").doesNotExist())
                .andExpect(jsonPath("$.person.phoneHref").doesNotExist());
    }
}
