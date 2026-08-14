package com.anuragbandhu.site.domain;

import java.util.List;

/** One-page application resume, tighter than the public site timeline. */
public record ResumeDocument(
        Person person,
        Skills skills,
        List<Role> roles,
        List<Role> earlier,
        List<Project> projects,
        List<Leadership> leadership,
        Education education
) {
    public String fileStem() {
        return person.name().replace(' ', '_') + "_Resume";
    }

    public boolean hasEarlier() {
        return earlier != null && !earlier.isEmpty();
    }
}
