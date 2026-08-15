package com.anuragbandhu.site.domain;

import java.util.List;

/** One-page application resume, tighter than the public site timeline. */
public record ResumeDocument(
        Person person,
        Skills skills,
        List<Role> roles,
        List<Project> projects,
        List<Leadership> openSource,
        List<Writing> writing,
        List<Leadership> leadership,
        Education education
) {
    public String fileStem() {
        return person.name().replace(' ', '_') + "_Resume";
    }

    public boolean hasOpenSource() {
        return openSource != null && !openSource.isEmpty();
    }

    public boolean hasWriting() {
        return writing != null && !writing.isEmpty();
    }
}
