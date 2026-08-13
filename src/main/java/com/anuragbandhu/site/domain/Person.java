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
        String siteUrl,
        String summary,
        List<String> about,
        Skills skills,
        List<SpokenLanguage> spokenLanguages
) {
    public String githubLabel() {
        return "github.com/" + githubHandle;
    }

    public String spokenLine() {
        return spokenLanguages.stream()
                .map(SpokenLanguage::display)
                .reduce((a, b) -> a + " · " + b)
                .orElse("");
    }
}
