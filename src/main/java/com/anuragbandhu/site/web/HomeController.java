package com.anuragbandhu.site.web;

import com.anuragbandhu.site.content.PortfolioCatalog;
import com.anuragbandhu.site.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final PortfolioCatalog catalog;
    private final ObjectMapper objectMapper;

    public HomeController(PortfolioCatalog catalog, ObjectMapper objectMapper) {
        this.catalog = catalog;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String home(Model model) throws JsonProcessingException {
        var site = catalog.site();
        model.addAttribute("site", site);
        model.addAttribute("person", site.person());
        model.addAttribute("jsonLd", jsonLd(site.person()));
        return "index";
    }

    private String jsonLd(Person person) throws JsonProcessingException {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("@context", "https://schema.org");
        payload.put("@type", "Person");
        payload.put("name", person.name());
        payload.put("jobTitle", "Senior Software Engineer");
        payload.put("email", person.email());
        payload.put("url", person.siteUrl());
        payload.put("sameAs", List.of(
                person.github(),
                person.linkedin(),
                person.leetcode(),
                "https://medium.com/@anrgbndhu"
        ));
        payload.put("alumniOf", "The National Institute of Engineering, Mysuru");
        payload.put("address", Map.of(
                "@type", "PostalAddress",
                "addressLocality", "Bengaluru",
                "addressCountry", "IN"
        ));
        return objectMapper.writeValueAsString(payload);
    }
}
