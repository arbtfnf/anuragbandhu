package com.anuragbandhu.site.domain;

public record Education(String school, String degree, String start, String end) {
    public String dates() {
        if (start == null || start.isBlank()) {
            return end == null ? "" : end;
        }
        return start + " to " + end;
    }

    /** Resume layout when {@link #start()} is blank: one line like experience entries. */
    public boolean isCompact() {
        return start == null || start.isBlank();
    }
}
