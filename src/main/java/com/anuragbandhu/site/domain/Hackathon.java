package com.anuragbandhu.site.domain;

import java.util.List;

public record Hackathon(
        String name,
        String result,
        String venue,
        String dates,
        String summary,
        List<String> points
) {}
