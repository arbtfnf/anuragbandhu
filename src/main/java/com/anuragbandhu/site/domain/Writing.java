package com.anuragbandhu.site.domain;

import java.util.List;

public record Writing(
        String title,
        String href,
        String published,
        String venue,
        String summary,
        List<String> tags
) {}
