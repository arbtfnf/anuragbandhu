package com.anuragbandhu.site.domain;

public record Education(String school, String degree, String start, String end) {
    public String dates() {
        return start + " to " + end;
    }
}
