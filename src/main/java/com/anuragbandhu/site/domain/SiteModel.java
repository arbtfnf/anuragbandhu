package com.anuragbandhu.site.domain;

import java.util.List;

public record SiteModel(
        Person person,
        List<Project> projects,
        List<Role> experience,
        Education education,
        List<String> certifications,
        List<String> awards,
        List<String> practices
) {}
