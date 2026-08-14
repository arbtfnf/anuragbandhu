package com.anuragbandhu.site.domain;

import java.util.List;

public record CaseStudy(
        String id,
        String title,
        String period,
        String role,
        String lede,
        List<CaseStudyStat> stats,
        List<CaseStudyBlock> blocks
) {}
