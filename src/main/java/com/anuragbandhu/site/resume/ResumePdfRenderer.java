package com.anuragbandhu.site.resume;

import com.anuragbandhu.site.domain.ResumeDocument;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

import java.io.ByteArrayOutputStream;

@Component
public class ResumePdfRenderer {

    private final SpringTemplateEngine templates;
    private final ApplicationContext applicationContext;

    public ResumePdfRenderer(SpringTemplateEngine templates, ApplicationContext applicationContext) {
        this.templates = templates;
        this.applicationContext = applicationContext;
    }

    public byte[] render(ResumeDocument resume) {
        Context context = new Context();
        context.setVariable("person", resume.person());
        context.setVariable("resume", resume);
        context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null)
        );
        String html = templates.process("resume-pdf", context);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, "https://anuragbandhu.vercel.app/");
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("Could not render resume PDF", ex);
        }
    }
}
