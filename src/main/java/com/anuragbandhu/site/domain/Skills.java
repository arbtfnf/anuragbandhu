package com.anuragbandhu.site.domain;

import java.util.List;

public record Skills(List<String> languages, List<String> cloud, List<String> tools) {
    public String languagesLine() {
        return String.join(" · ", languages);
    }

    public String cloudLine() {
        return String.join(" · ", cloud);
    }

    public String toolsLine() {
        return String.join(" · ", tools);
    }
}
