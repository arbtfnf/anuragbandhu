package com.anuragbandhu.site.resume;

import com.anuragbandhu.site.domain.Leadership;
import com.anuragbandhu.site.domain.Person;
import com.anuragbandhu.site.domain.Project;
import com.anuragbandhu.site.domain.ResumeDocument;
import com.anuragbandhu.site.domain.Role;
import org.springframework.stereotype.Component;

@Component
public class LatexResumeRenderer {

    public String render(ResumeDocument resume) {
        Person person = resume.person();
        StringBuilder tex = new StringBuilder();
        tex.append("""
                % !TEX program = pdflatex
                % One-page resume. Open in Overleaf and compile with pdfLaTeX.
                % Generated from the same catalog as the public site.
                \\documentclass[11pt,letterpaper]{article}
                \\usepackage[margin=0.5in]{geometry}
                \\usepackage[T1]{fontenc}
                \\usepackage[utf8]{inputenc}
                \\usepackage{titlesec}
                \\usepackage{enumitem}
                \\usepackage[hidelinks]{hyperref}
                \\pagestyle{empty}
                \\setlength{\\parindent}{0pt}
                \\setlist[itemize]{leftmargin=12pt,itemsep=2pt,parsep=0pt,topsep=2pt,partopsep=0pt}
                \\titleformat{\\section}{\\large\\bfseries\\scshape\\raggedright}{}{0em}{}[\\titlerule\\vspace{4pt}]
                \\titlespacing*{\\section}{0pt}{10pt}{6pt}

                \\begin{document}

                """);
        tex.append("\\begin{center}\n");
        tex.append("{\\LARGE\\bfseries ").append(LatexEscaper.text(person.name())).append("}\\\\\n");
        tex.append("\\vspace{4pt}\n");
        tex.append("\\href{").append(person.github()).append("}{github.com/")
                .append(LatexEscaper.text(person.githubHandle())).append("}\n");
        tex.append("$|$ \\href{").append(person.linkedin()).append("}{linkedin.com/in/anuragbandhu}\n");
        tex.append("$|$ \\href{mailto:").append(person.email()).append("}{")
                .append(LatexEscaper.text(person.email())).append("}\n");
        tex.append("$|$ \\href{").append(person.phoneHref()).append("}{")
                .append(LatexEscaper.text(person.phoneDisplay())).append("}\n");
        tex.append("\\end{center}\n\n");

        tex.append("\\section{Skills}\n");
        tex.append("\\textbf{Languages:} ")
                .append(LatexEscaper.text(resume.skills().languagesLine())).append("\\\\\n");
        tex.append("\\textbf{Cloud \\& Infrastructure:} ")
                .append(LatexEscaper.text(resume.skills().cloudLine())).append("\\\\\n");
        tex.append("\\textbf{Tools \\& Frameworks:} ")
                .append(LatexEscaper.text(resume.skills().toolsLine())).append("\n\n");

        tex.append("\\section{Experience}\n");
        for (Role role : resume.roles()) {
            tex.append("\\textbf{").append(LatexEscaper.text(role.company())).append("}");
            tex.append(" $|$ ").append(LatexEscaper.text(role.title()));
            if (role.stack() != null && !role.stack().isBlank()) {
                tex.append(" $|$ ").append(LatexEscaper.text(role.stack()));
            }
            tex.append(" \\hfill ").append(LatexEscaper.text(role.dates())).append("\\\\\n");
            if (role.hasBullets()) {
                tex.append("\\begin{itemize}\n");
                for (String bullet : role.bullets()) {
                    tex.append("  \\item ").append(LatexEscaper.text(bullet)).append("\n");
                }
                tex.append("\\end{itemize}\n");
            } else {
                tex.append("\\vspace{4pt}\n");
            }
            tex.append("\n");
        }

        tex.append("\\section{Projects}\n");
        for (Project project : resume.projects()) {
            tex.append("\\textbf{");
            if (project.hasHref()) {
                tex.append("\\href{").append(project.href()).append("}{")
                        .append(LatexEscaper.text(project.name())).append("}");
            } else {
                tex.append(LatexEscaper.text(project.name()));
            }
            tex.append("} $|$ ").append(LatexEscaper.text(project.role()));
            tex.append(" \\hfill ").append(LatexEscaper.text(project.period())).append("\\\\\n");
            tex.append(LatexEscaper.text(project.summary())).append("\n");
            tex.append("\\vspace{6pt}\n\n");
        }

        tex.append("\\section{Leadership \\& Achievements}\n");
        for (Leadership item : resume.leadership()) {
            tex.append("\\textbf{").append(LatexEscaper.text(item.title())).append(":} ");
            tex.append(LatexEscaper.text(item.body())).append("\\\\\n");
        }
        tex.append("\n");

        tex.append("\\section{Education}\n");
        tex.append("\\textbf{").append(LatexEscaper.text(resume.education().school())).append("}");
        tex.append(" \\hfill ").append(LatexEscaper.text(resume.education().dates())).append("\\\\\n");
        tex.append(LatexEscaper.text(resume.education().degree())).append("\n\n");

        tex.append("\\end{document}\n");
        return tex.toString();
    }
}
