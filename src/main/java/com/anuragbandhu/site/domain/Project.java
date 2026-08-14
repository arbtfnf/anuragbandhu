package com.anuragbandhu.site.domain;

import java.util.List;

public record Project(
        String id,
        String name,
        String period,
        String role,
        String href,
        String github,
        String summary,
        List<String> points
) {
    public boolean hasHref() {
        return href != null && !href.isBlank();
    }

    public boolean isExternalHref() {
        return hasHref() && (href.startsWith("http://") || href.startsWith("https://"));
    }

    public boolean hasGithub() {
        return github != null && !github.isBlank();
    }
}
