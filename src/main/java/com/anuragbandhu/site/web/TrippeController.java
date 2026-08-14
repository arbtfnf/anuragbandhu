package com.anuragbandhu.site.web;

import com.anuragbandhu.site.content.PortfolioCatalog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrippeController {

    private final PortfolioCatalog catalog;

    public TrippeController(PortfolioCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/work/trippe")
    public String trippe(Model model) {
        var site = catalog.site();
        model.addAttribute("person", site.person());
        model.addAttribute("study", catalog.trippe());
        return "trippe";
    }
}
