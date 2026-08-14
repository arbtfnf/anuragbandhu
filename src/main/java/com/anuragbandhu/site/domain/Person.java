package com.anuragbandhu.site.domain;

import java.util.List;

public record Person(
        String name,
        String firstName,
        String headline,
        String location,
        String email,
        String phoneDisplay,
        String phoneHref,
        String github,
        String githubHandle,
        String linkedin,
        String leetcode,
        String leetcodeHandle,
        String siteUrl,
        String summary,
        List<String> about,
        Skills skills,
        List<SpokenLanguage> spokenLanguages
) {
    public String githubLabel() {
        return "github.com/" + githubHandle;
    }

    public String leetcodeLabel() {
        return "leetcode.com/" + leetcodeHandle;
    }

    public boolean hasLeetcode() {
        return leetcode != null && !leetcode.isBlank();
    }

    public String spokenLine() {
        return spokenLanguages.stream()
                .map(SpokenLanguage::display)
                .reduce((a, b) -> a + " · " + b)
                .orElse("");
    }
}
