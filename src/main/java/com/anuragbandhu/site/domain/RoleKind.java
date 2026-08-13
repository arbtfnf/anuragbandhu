package com.anuragbandhu.site.domain;

public enum RoleKind {
    FULL_TIME(null),
    FOUNDER("Founder"),
    OPEN_SOURCE("Open source"),
    VOLUNTEER("Volunteer"),
    INTERN("Intern");

    private final String badge;

    RoleKind(String badge) {
        this.badge = badge;
    }

    public String badge() {
        return badge;
    }
}
