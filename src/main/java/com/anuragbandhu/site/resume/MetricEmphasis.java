package com.anuragbandhu.site.resume;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bold the proof numbers on the resume (counts, times, percents).
 * Skips years, Java/Spring versions, and dates in role headers.
 */
@Component("metricEmphasis")
public class MetricEmphasis {

    private static final Pattern METRIC = Pattern.compile(
            "~?\\d+(?:\\.\\d+)?\\s*billion"
                    + "|~?\\d{1,3}(?:,\\d{3})+/sec"
                    + "|\\d{1,3}(?:,\\d{3})*ms"
                    + "|~?\\d+(?:\\.\\d+)?-\\d+(?:\\.\\d+)?%"
                    + "|~?\\d+(?:\\.\\d+)?%"
                    + "|24/7"
                    + "|\\d+-repository"
                    + "|~?\\d+\\s+(?:unassigned|rotations|production|FHIR|junior)"
                    + "|\\d+\\s+(?:days?|hours?|months?|weeks?)"
    );

    public String html(String text) {
        return wrap(text, "<strong class=\"metric\">", "</strong>", true);
    }

    public String latex(String text) {
        return wrap(text, "\\textbf{", "}", false);
    }

    private static String wrap(String text, String open, String close, boolean html) {
        if (text == null || text.isBlank()) {
            return "";
        }
        Matcher matcher = METRIC.matcher(text);
        StringBuilder out = new StringBuilder(text.length() + 32);
        int last = 0;
        while (matcher.find()) {
            out.append(escape(text.substring(last, matcher.start()), html));
            out.append(open);
            out.append(escape(matcher.group(), html));
            out.append(close);
            last = matcher.end();
        }
        out.append(escape(text.substring(last), html));
        return out.toString();
    }

    private static String escape(String value, boolean html) {
        return html ? HtmlUtils.htmlEscape(value) : LatexEscaper.text(value);
    }
}
