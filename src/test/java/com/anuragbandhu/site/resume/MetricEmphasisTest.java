package com.anuragbandhu.site.resume;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricEmphasisTest {

    private final MetricEmphasis metrics = new MetricEmphasis();

    @Test
    void htmlBoldsProofNumbersAndLeavesVersionsAlone() {
        String html = metrics.html(
                "CareFabric: ~3 billion messages (~1,150/sec, 24/7). Heap 92-98%. "
                        + "Java 21 and Spring Boot 3.x. 14 FHIR resources. 74-repository PR. "
                        + "3 days to 18 hours. ~90% coverage."
        );
        assertTrue(html.contains("<strong class=\"metric\">~3 billion</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">~1,150/sec</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">24/7</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">92-98%</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">14 FHIR</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">74-repository</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">3 days</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">18 hours</strong>"));
        assertTrue(html.contains("<strong class=\"metric\">~90%</strong>"));
        assertFalse(html.contains("<strong class=\"metric\">21</strong>"));
        assertFalse(html.contains("<strong class=\"metric\">3.x</strong>"));
    }

    @Test
    void latexWrapsPercentsAsTextbf() {
        String tex = metrics.latex("Heap 92-98%. ~3 billion.");
        assertTrue(tex.contains("\\textbf{92-98\\%}"));
        assertTrue(tex.contains("\\textbf{\\textasciitilde{}3 billion}"));
    }
}
