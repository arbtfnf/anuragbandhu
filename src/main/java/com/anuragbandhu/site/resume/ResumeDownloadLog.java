package com.anuragbandhu.site.resume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class ResumeDownloadLog {

    private static final Logger log = LoggerFactory.getLogger(ResumeDownloadLog.class);
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Path FILE = Path.of("data", "resume-downloads.jsonl");

    public record Event(
            Instant at,
            String email,
            String name,
            String source,
            String ip,
            String userAgent,
            String referer
    ) {
    }

    public Event record(String email, String name, String source, String ip, String userAgent, String referer) {
        String cleanEmail = clean(email, 200).toLowerCase(Locale.ROOT);
        if (!EMAIL.matcher(cleanEmail).matches()) {
            throw new IllegalArgumentException("A valid email is required to download the resume.");
        }
        Event event = new Event(
                Instant.now(),
                cleanEmail,
                blankToNull(clean(name, 120)),
                blankToNull(clean(source, 40)),
                blankToNull(clean(ip, 80)),
                blankToNull(clean(userAgent, 300)),
                blankToNull(clean(referer, 300))
        );
        log.info(
                "resume.download email={} name={} source={} ip={} referer={} ua={}",
                event.email(),
                event.name() == null ? "-" : event.name(),
                event.source() == null ? "-" : event.source(),
                event.ip() == null ? "-" : event.ip(),
                event.referer() == null ? "-" : event.referer(),
                event.userAgent() == null ? "-" : event.userAgent()
        );
        append(event);
        return event;
    }

    private void append(Event event) {
        String line = String.format(
                Locale.ROOT,
                "{\"at\":\"%s\",\"email\":\"%s\",\"name\":\"%s\",\"source\":\"%s\",\"ip\":\"%s\"}%n",
                event.at(),
                escape(event.email()),
                escape(event.name() == null ? "" : event.name()),
                escape(event.source() == null ? "" : event.source()),
                escape(event.ip() == null ? "" : event.ip())
        );
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            log.warn("Could not persist resume download log: {}", ex.getMessage());
        }
    }

    private static String clean(String value, int max) {
        if (value == null) {
            return "";
        }
        String stripped = value.replaceAll("[\\r\\n\\t]", " ").trim();
        return stripped.length() <= max ? stripped : stripped.substring(0, max);
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
