package com.anuragbandhu.site.web;

import com.anuragbandhu.site.content.PortfolioCatalog;
import com.anuragbandhu.site.domain.SiteModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PortfolioApiController {

    private final PortfolioCatalog catalog;

    public PortfolioApiController(PortfolioCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping
    public SiteModel profile() {
        return catalog.site();
    }
}
