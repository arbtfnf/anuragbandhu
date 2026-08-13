package com.anuragbandhu.site.web;

import com.anuragbandhu.site.content.PortfolioCatalog;
import com.anuragbandhu.site.domain.ResumeDocument;
import com.anuragbandhu.site.resume.LatexResumeRenderer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
public class ResumeController {

    private static final MediaType TEX = new MediaType("application", "x-tex", StandardCharsets.UTF_8);

    private final PortfolioCatalog catalog;
    private final LatexResumeRenderer latex;

    public ResumeController(PortfolioCatalog catalog, LatexResumeRenderer latex) {
        this.catalog = catalog;
        this.latex = latex;
    }

    @GetMapping("/resume")
    public String preview(Model model) {
        ResumeDocument resume = catalog.resume();
        model.addAttribute("person", resume.person());
        model.addAttribute("resume", resume);
        return "resume";
    }

    @GetMapping("/resume.tex")
    public ResponseEntity<byte[]> downloadTex() {
        ResumeDocument resume = catalog.resume();
        byte[] body = latex.render(resume).getBytes(StandardCharsets.UTF_8);
        String filename = resume.fileStem() + ".tex";
        return ResponseEntity.ok()
                .contentType(TEX)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(body);
    }
}
