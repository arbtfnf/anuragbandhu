package com.anuragbandhu.site.domain;

import java.util.List;

public record Role(
        String id,
        String company,
        String title,
        RoleKind kind,
        String location,
        String start,
        String end,
        String stack,
        String href,
        List<String> bullets
) {
    public boolean hasHref() {
        return href != null && !href.isBlank();
    }

    public boolean hasBullets() {
        return bullets != null && !bullets.isEmpty();
    }

    public String badge() {
        return kind.badge();
    }

    public String dates() {
        return start + " to " + end;
    }

    public String locationLine() {
        if (stack == null || stack.isBlank()) {
            return location;
        }
        return location + " · " + stack;
    }
}
