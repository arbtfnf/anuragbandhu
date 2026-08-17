package com.anuragbandhu.site.domain;

import java.util.List;

public record Skills(
        List<String> languages,
        List<String> cloud,
        List<String> tools,
        List<String> architecture
) {
    public Skills(List<String> languages, List<String> cloud, List<String> tools) {
        this(languages, cloud, tools, List.of());
    }

    public boolean hasArchitecture() {
        return architecture != null && !architecture.isEmpty();
    }

    public String languagesLine() {
        return String.join(" · ", languages);
    }

    public String cloudLine() {
        return String.join(" · ", cloud);
    }

    public String toolsLine() {
        return String.join(" · ", tools);
    }

    public String architectureLine() {
        return hasArchitecture() ? String.join(" · ", architecture) : "";
    }
}
