package com.anuragbandhu.site.content;

import com.anuragbandhu.site.domain.Education;
import com.anuragbandhu.site.domain.Leadership;
import com.anuragbandhu.site.domain.Person;
import com.anuragbandhu.site.domain.Project;
import com.anuragbandhu.site.domain.ResumeDocument;
import com.anuragbandhu.site.domain.Role;
import com.anuragbandhu.site.domain.RoleKind;
import com.anuragbandhu.site.domain.SiteModel;
import com.anuragbandhu.site.domain.Skills;
import com.anuragbandhu.site.domain.SpokenLanguage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Single source of truth for the public site. LinkedIn is the employment ledger;
 * Netsmart / Zeitview / Trippe bullets come from the resume. Trippe ends Nov 2023
 * so Credit Saison (Feb–Oct 2024) is not covered.
 */
@Component
public class PortfolioCatalog {

    private final SiteModel site = build();

    private static final Set<String> RESUME_ROLE_IDS = Set.of(
            "netsmart", "zeitview", "credit-saison", "trippe"
    );

    public SiteModel site() {
        return site;
    }

    public ResumeDocument resume() {
        List<Role> roles = site.experience().stream()
                .filter(role -> RESUME_ROLE_IDS.contains(role.id()))
                .toList();
        return new ResumeDocument(
                site.person(),
                site.person().skills(),
                roles,
                site.projects(),
                List.of(
                        new Leadership(
                                "Technical mentorship",
                                "Bi-weekly sessions on distributed systems and code quality; onboarded 3 junior engineers at Netsmart."
                        ),
                        new Leadership(
                                "AI workflow",
                                site.practices().getFirst()
                        )
                ),
                site.education()
        );
    }

    private static SiteModel build() {
        Person person = new Person(
                "Anurag Rakesh Bandhu",
                "Anurag",
                "Backend engineer. Civic builder.",
                "Bengaluru, Karnataka, India",
                "anrgjobs@gmail.com",
                "+91 85532 20749",
                "tel:+918553220749",
                "https://github.com/arbtfnf",
                "arbtfnf",
                "https://www.linkedin.com/in/anuragbandhu",
                "https://anuragbandhu.com",
                "I build backend systems that have to be correct under load — queues, migrations, checksums — and civic products that have to be believed. Currently Senior Software Engineer at Netsmart. The Bangalore Notebook is the independent work: a livability ledger for Bengaluru, ward by ward.",
                List.of(
                        "I have spent the last few years on high-stakes data movement: Elasticsearch to Postgres, millions of records onto S3, checksums that have to come back clean. Java, Spring Boot, AWS, PostgreSQL.",
                        "In 2016 I helped start YourMarch, a civic network that was right about the problem and early about the country. The Bangalore Notebook is the second chapter — quieter, more precise: scores, reports, and capital aimed at streets, pickup, and air, not another feed.",
                        "Before Netsmart I was at Zeitview and Credit Saison, and I co-founded Trippe World. BE Computer Science, NIE Mysuru, 2020."
                ),
                new Skills(
                        List.of("Java", "Python", "JavaScript/TypeScript", "SQL", "C++", "Bash"),
                        List.of("AWS (S3, SQS, Lambda, ECS, CloudWatch)", "GCP", "Docker", "Kubernetes", "Terraform"),
                        List.of("Spring Boot", "Spring MVC", "REST APIs", "Git", "PostgreSQL", "MySQL", "Elasticsearch")
                ),
                List.of(
                        new SpokenLanguage("English", "Full professional"),
                        new SpokenLanguage("Hindi", "Native or bilingual")
                )
        );

        List<Project> projects = List.of(
                new Project(
                        "notebook",
                        "The Bangalore Notebook",
                        "2025 – present",
                        "Independent · founder-engineer",
                        "https://thebangalorenotebook.com",
                        "https://github.com/arbtfnf/the-bangalore-notebook",
                        "A civic livability ledger for Bengaluru. Ward- and block-level scores, neighbour reports that become a shared record, and a path for CSR and residents to fund what actually gets mended.",
                        List.of(
                                "Next.js, TypeScript, maps, and a report → share → verify loop (X, LinkedIn) with stable public refs.",
                                "Guides on garbage and traffic so the product teaches the city, not only charts it.",
                                "The quiet successor to YourMarch: measure the street, then fix it, instead of another debate feed."
                        )
                ),
                new Project(
                        "yourmarch",
                        "YourMarch",
                        "2016 – 2017",
                        "Founding member",
                        "https://yourmarch.com",
                        null,
                        "A nonpartisan civic social network for India. The bet: let people who care about issues debate in the open and influence the policies that shape daily life. The idea was right; the timing was early.",
                        List.of(
                                "Discussion and debate tools aimed at citizens, with locality-level sentiment for institutions.",
                                "Taught what does not stick when the street itself is broken — the scar tissue behind the Notebook."
                        )
                ),
                new Project(
                        "trippe",
                        "Trippe World",
                        "2021 – 2023",
                        "Co-founder & software developer",
                        null,
                        null,
                        "Travel platform matching travelers with local guides and experiences. Full-stack: Spring Boot, React, Android, MySQL, OAuth2, payments, ratings.",
                        List.of(
                                "Team of four, production microservices, recommendation and trip-planning workflows.",
                                "Analytics-led iteration on engagement and conversion."
                        )
                )
        );

        List<Role> experience = List.of(
                new Role(
                        "netsmart",
                        "Netsmart",
                        "Senior Software Engineer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Jul 2025",
                        "Present",
                        "Java, AWS, PostgreSQL, Spring Boot",
                        null,
                        List.of(
                                "Led architecture and delivery of a high-concurrency migration pipeline (Java / SQS / Lambda): 1.39M records to S3, processing time 48h → 9h, with circuit breakers, exponential backoff, and a REST migration controller.",
                                "Built SHA-256 checksum validation with automated re-download verification so every migrated object could be proven intact.",
                                "Cut infrastructure cost ~20% by tuning ECS / Lambda allocation and a batching strategy that reduced database I/O ~60%.",
                                "Used JProfiler to clear memory retention issues; added CloudWatch custom metrics, throughput monitoring, and DLQ alerting.",
                                "Wrote architectural playbooks and recovery protocols for production handoff; mentored three junior engineers in bi-weekly distributed-systems sessions."
                        )
                ),
                new Role(
                        "zeitview",
                        "Zeitview",
                        "Software Engineer 2",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Oct 2024",
                        "May 2025",
                        "Java, PostgreSQL, Elasticsearch",
                        null,
                        List.of(
                                "Led design of a large Elasticsearch → PostgreSQL migration, using graph-based dependency analysis to map relational order.",
                                "Improved response times ~30% by tracing distributed bottlenecks and tuning JVM heap during heavy transform cycles.",
                                "Stood up ephemeral databases so integration tests ran at unit-test speed; ~90% coverage and fewer regressions in production deploys.",
                                "Tightened review standards; code-review cycle time dropped ~40%."
                        )
                ),
                new Role(
                        "credit-saison",
                        "Credit Saison India",
                        "Software Engineer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Feb 2024",
                        "Oct 2024",
                        null,
                        null,
                        List.of()
                ),
                new Role(
                        "trippe",
                        "Trippe World",
                        "Co-founder & Software Developer",
                        RoleKind.FOUNDER,
                        "India",
                        "Jul 2021",
                        "Nov 2023",
                        "Java, Spring Boot, React, MySQL",
                        null,
                        List.of(
                                "Directed technical roadmap and architecture for a travel platform connecting travelers with local guides — authentication, payments, ratings — from concept to production.",
                                "Led a team of four on OAuth2, trip-planning workflows, and a recommendation engine that lifted engagement ~25%.",
                                "Shipped Android + React clients against a Spring Boot / MySQL backend; used Google Analytics funnels to decide what to build next.",
                                "Pitched the product with co-founders to investors and partners."
                        )
                ),
                new Role(
                        "commscope",
                        "CommScope",
                        "Software Engineer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Aug 2020",
                        "Aug 2021",
                        null,
                        null,
                        List.of(
                                "Built and maintained web applications and APIs with OpenID Connect, REST, and GitHub-centered version control.",
                                "Worked with product, design, and clients on auth, performance, and delivery."
                        )
                ),
                new Role(
                        "ceph",
                        "Ceph",
                        "Open Source Developer",
                        RoleKind.OPEN_SOURCE,
                        "Remote",
                        "2020",
                        "2021",
                        null,
                        null,
                        List.of()
                ),
                new Role(
                        "redhat",
                        "Red Hat",
                        "Software Engineer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Jan 2020",
                        "Jul 2020",
                        null,
                        null,
                        List.of()
                ),
                new Role(
                        "yourmarch-role",
                        "YourMarch",
                        "Growth hacker & founding member",
                        RoleKind.FOUNDER,
                        "Bengaluru",
                        "2016",
                        "2017",
                        null,
                        "https://yourmarch.com",
                        List.of(
                                "Civic engagement platform for people to share and debate the issues they care about — nonpartisan, locality-aware, built before smartphone civic habit was widespread in India."
                        )
                ),
                new Role(
                        "nisb",
                        "NISB · NIE IEEE Student Branch",
                        "Sponsorship team",
                        RoleKind.VOLUNTEER,
                        "Mysuru",
                        "Feb 2015",
                        "Mar 2016",
                        null,
                        null,
                        List.of()
                ),
                new Role(
                        "uber",
                        "Uber",
                        "Summer intern — organizing team",
                        RoleKind.INTERN,
                        "Mysuru",
                        "Nov 2015",
                        "Feb 2016",
                        null,
                        null,
                        List.of(
                                "Organizing team, Uber Challenge Mysore 2016 — event operations, not a software-engineering internship."
                        )
                )
        );

        return new SiteModel(
                person,
                projects,
                experience,
                new Education(
                        "The National Institute of Engineering, Mysuru",
                        "Bachelor of Engineering, Computer Science",
                        "Aug 2016",
                        "Jun 2020"
                ),
                List.of(
                        "Introduction to Containers, Kubernetes, and Red Hat OpenShift (DO180)",
                        "Blockchain for Business — Hyperledger (LinuxFoundationX)",
                        "Web Security: OAuth and OpenID Connect",
                        "Git: Branches, Merges, and Remotes",
                        "Entrepreneurship 1: Developing the Opportunity"
                ),
                List.of(
                        "Winner, NISB techNIEks Hackathon 2018",
                        "Winner, Yes Bank Datathon"
                ),
                List.of(
                        "Amazon Q and MCP servers wired to local docs and codebase context — routine coding time down ~25% on the Netsmart team."
                )
        );
    }
}
