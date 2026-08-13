package com.anuragbandhu.site.domain;

public record SpokenLanguage(String name, String level) {
    public String display() {
        return name + " (" + level + ")";
    }
}
