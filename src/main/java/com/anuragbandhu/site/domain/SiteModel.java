package com.anuragbandhu.site.domain;

import java.util.List;

public record SiteModel(
        Person person,
        List<Project> projects,
        List<Writing> writing,
        String mediumProfile,
        List<Role> experience,
        List<Hackathon> hackathons,
        Education education,
        List<String> certifications,
        List<String> practices
) {}
