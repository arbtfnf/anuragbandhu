package com.anuragbandhu.site.web;

import com.anuragbandhu.site.content.PortfolioCatalog;
import com.anuragbandhu.site.domain.ResumeDocument;
import com.anuragbandhu.site.resume.LatexResumeRenderer;
import com.anuragbandhu.site.resume.ResumeDownloadLog;
import com.anuragbandhu.site.resume.ResumePdfRenderer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Controller
public class ResumeController {

    private static final MediaType TEX = new MediaType("application", "x-tex", StandardCharsets.UTF_8);

    private final PortfolioCatalog catalog;
    private final LatexResumeRenderer latex;
    private final ResumePdfRenderer pdf;
    private final ResumeDownloadLog downloads;

    public ResumeController(
            PortfolioCatalog catalog,
            LatexResumeRenderer latex,
            ResumePdfRenderer pdf,
            ResumeDownloadLog downloads
    ) {
        this.catalog = catalog;
        this.latex = latex;
        this.pdf = pdf;
        this.downloads = downloads;
    }

    @GetMapping("/resume")
    public String preview(Model model) {
        ResumeDocument resume = catalog.resume();
        model.addAttribute("person", resume.person());
        model.addAttribute("resume", resume);
        return "resume";
    }

    @GetMapping("/resume.pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        ResumeDocument resume = catalog.resume();
        byte[] body = pdf.render(resume);
        String filename = resume.fileStem() + ".pdf";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(body);
    }

    @GetMapping("/resume.tex")
    public RedirectView downloadGate() {
        return new RedirectView("/resume?download=1");
    }

    @PostMapping("/resume.tex")
    public ResponseEntity<byte[]> downloadTex(
            @RequestParam String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String source,
            HttpServletRequest request
    ) {
        downloads.record(email, name, source, clientIp(request), request.getHeader("User-Agent"), request.getHeader("Referer"));
        ResumeDocument resume = catalog.resume();
        byte[] body = latex.render(resume).getBytes(StandardCharsets.UTF_8);
        String filename = resume.fileStem() + ".tex";
        return ResponseEntity.ok()
                .contentType(TEX)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(body);
    }

    private static String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage());
    }
}
