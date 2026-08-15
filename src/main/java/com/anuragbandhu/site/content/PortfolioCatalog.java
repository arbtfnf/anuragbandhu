package com.anuragbandhu.site.content;

import com.anuragbandhu.site.domain.CaseStudy;
import com.anuragbandhu.site.domain.CaseStudyBlock;
import com.anuragbandhu.site.domain.CaseStudyStat;
import com.anuragbandhu.site.domain.Education;
import com.anuragbandhu.site.domain.Hackathon;
import com.anuragbandhu.site.domain.Leadership;
import com.anuragbandhu.site.domain.Person;
import com.anuragbandhu.site.domain.Project;
import com.anuragbandhu.site.domain.ResumeDocument;
import com.anuragbandhu.site.domain.Role;
import com.anuragbandhu.site.domain.RoleKind;
import com.anuragbandhu.site.domain.SiteModel;
import com.anuragbandhu.site.domain.Skills;
import com.anuragbandhu.site.domain.SpokenLanguage;
import com.anuragbandhu.site.domain.Writing;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Single source of truth for the public site. LinkedIn is the employment ledger;
 * Netsmart bullets come from the CareFabric 2025-2026 worklog. ~3 billion
 * messages/month included at the author's request. No customer names. Zeitview
 * migration bullets come from the graph-based ES-to-Postgres interview brief.
 * Trippe bullets and /work/trippe come from the Redis caching guide and the
 * indexing strategy (July 2025 write-ups in the Trippe work-log), plus the
 * TrippeWorld GitHub org (private services) and the resume engagement figure.
 * Trippe ends Nov 2023 so Credit Saison / Saison Omni (Feb-Oct 2024) sits on
 * the ledger. Saison Omni bullets describe the co-lending platform; LinkedIn
 * had no metrics.
 */
@Component
public class PortfolioCatalog {

    private final SiteModel site = build();
    private final CaseStudy trippe = trippeStudy();

    private static final Set<String> RESUME_ROLE_IDS = Set.of(
            "netsmart", "zeitview", "credit-saison", "trippe"
    );

    private static final Set<String> RESUME_PROJECT_IDS = Set.of("notebook");

    private static final Set<String> RESUME_EARLIER_IDS = Set.of(
            "commscope", "ceph", "redhat"
    );

    public SiteModel site() {
        return site;
    }

    public CaseStudy trippe() {
        return trippe;
    }

    public ResumeDocument resume() {
        List<Role> roles = site.experience().stream()
                .filter(role -> RESUME_ROLE_IDS.contains(role.id()))
                .map(PortfolioCatalog::forResume)
                .toList();
        List<Role> earlier = site.experience().stream()
                .filter(role -> RESUME_EARLIER_IDS.contains(role.id()))
                .toList();
        return new ResumeDocument(
                site.person(),
                resumeSkills(),
                roles,
                earlier,
                site.projects().stream()
                        .filter(project -> RESUME_PROJECT_IDS.contains(project.id()))
                        .map(PortfolioCatalog::forResume)
                        .toList(),
                List.of(
                        new Leadership(
                                "techNIEks Hackathon 2018",
                                "Winner. One-day IEEE NISB hackathon at NIE Mysuru (25 Feb 2018): prototype and live demo."
                        ),
                        new Leadership(
                                "Yes Bank Datathon 2019",
                                "Winner. Techkriti, IIT Kanpur: campus datathon on banking data problems, judged on the model and the pitch."
                        )
                ),
                site.education()
        );
    }

    private static Skills resumeSkills() {
        return new Skills(
                List.of("Java", "Python", "JavaScript/TypeScript", "SQL"),
                List.of("AWS (S3, SQS, Lambda, ECS, CloudWatch)", "Docker", "Kubernetes"),
                List.of("Spring Boot", "REST APIs", "PostgreSQL", "Redis", "OpenSearch", "FHIR", "Elasticsearch")
        );
    }

    private static Role forResume(Role role) {
        return switch (role.id()) {
            case "netsmart" -> withBullets(role, "Java, Spring Boot, OpenSearch, AWS", List.of(
                    "Platform team on CareFabric, Netsmart's healthcare interoperability layer: ~3 billion clinical messages per month (~1,150/sec, 24/7).",
                    "Delivered 14 FHIR resources as first-class search/store types. Migrated the Inbox Admin Tool onto the platform SDK (Java 21, Spring Boot 3.x).",
                    "Implemented FHIR _lastUpdated across a multi-repository data store. Closed an unmerged 74-repository PR, then split schema and query into two reviewable changes.",
                    "Created AI agents in Kiro CLI wired to local docs and codebase context.",
                    "Production on-call and prod incident support: 14 rotations in 12 months; 9 Inbox defects, including one that passed QA; Dec 2025 OpenSearch yellow cluster restored to green (_id paging default to _doc)."
            ));
            case "zeitview" -> withBullets(role, List.of(
                    "Modeled Elasticsearch to PostgreSQL as a DAG in Java (JGraphT): topological load order so children never landed before parents; DFS/SCC for cycles.",
                    "Flattened nested Elasticsearch arrays into junction tables; validated with counts, checksums, and rollback on failure.",
                    "Ran independent DAG levels in parallel (~60% of tables), cutting estimated migration from 3 days to 18 hours. Ephemeral DBs for tests; ~90% coverage."
            ));
            case "credit-saison" -> withBullets(role, List.of(
                    "Backend on Saison Omni, Credit Saison's co-lending platform: banks and NBFCs from origination through loan management.",
                    "Partner-facing APIs and LMS sync; stayed with partner go-lives in a regulated NBFC environment."
            ));
            case "trippe" -> withBullets(role, List.of(
                    "Designed and architected an itinerary planner using a Bloom filter.",
                    "PostgreSQL JSONB GIN indexes then Redis persona feeds: tag search 8,934ms to 5ms (34ms with indexes alone)."
            ));
            default -> role;
        };
    }

    private static Role withBullets(Role role, List<String> bullets) {
        return withBullets(role, role.stack(), bullets);
    }

    private static Role withBullets(Role role, String stack, List<String> bullets) {
        return new Role(
                role.id(),
                role.company(),
                role.title(),
                role.kind(),
                role.location(),
                role.start(),
                role.end(),
                stack,
                role.href(),
                bullets
        );
    }

    private static Project forResume(Project project) {
        if (!"notebook".equals(project.id())) {
            return project;
        }
        return new Project(
                project.id(),
                project.name(),
                project.period(),
                project.role(),
                project.href(),
                project.github(),
                "Civic livability ledger for Bengaluru: ward scores, neighbour reports, Next.js/TypeScript.",
                List.of()
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
                "https://leetcode.com/u/anuragbandhu007/",
                "anuragbandhu007",
                "https://anuragbandhu.com",
                "I build backend systems that have to be correct under load (queues, migrations, checksums) and civic products that have to be believed. Currently Senior Software Engineer at Netsmart. The Bangalore Notebook is the independent work: a livability ledger for Bengaluru, ward by ward.",
                List.of(
                        "I have spent the last few years on high-stakes data movement: Elasticsearch to Postgres, millions of records onto S3, checksums that have to come back clean. Java, Spring Boot, AWS, PostgreSQL.",
                        "In 2016 I helped start YourMarch, a civic network that was right about the problem and early about the country. The Bangalore Notebook is the second chapter: quieter, more precise. Scores, reports, and capital aimed at streets, pickup, and air, not another feed.",
                        "Before Netsmart I was at Zeitview and Credit Saison, and I was a founding member at Trippe World. Earlier: CommScope, a Red Hat internship, and open-source work on Ceph. BE Computer Science, NIE Mysuru, 2020."
                ),
                new Skills(
                        List.of("Java", "Python", "JavaScript/TypeScript", "SQL", "C++", "Bash"),
                        List.of("AWS (S3, SQS, Lambda, ECS, CloudWatch)", "GCP", "Docker", "Kubernetes", "Terraform"),
                        List.of("Spring Boot", "Spring MVC", "REST APIs", "Git", "PostgreSQL", "MySQL", "Redis", "OpenSearch", "Elasticsearch", "FHIR")
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
                        "2025 to present",
                        "Forward deployed engineer",
                        "https://thebangalorenotebook.com",
                        "https://github.com/arbtfnf/the-bangalore-notebook",
                        "A civic livability ledger for Bengaluru. Ward- and block-level scores, neighbour reports that become a shared record, and a path for CSR and residents to fund what actually gets mended.",
                        List.of(
                                "Next.js, TypeScript, maps, and a report, share, verify loop (X, LinkedIn) with stable public refs.",
                                "Guides on garbage and traffic so the product teaches the city, not only charts it.",
                                "The quiet successor to YourMarch: measure the street, then fix it, instead of another debate feed."
                        )
                ),
                new Project(
                        "claudegravity",
                        "ClaudeGravity",
                        "2026 to present",
                        "Author",
                        "https://github.com/arbtfnf/claudegravity",
                        "https://github.com/arbtfnf/claudegravity",
                        "Plug-and-play AI agents and skills for Cursor, Claude Code, Junie, and Antigravity. Agents and skills live in the repo. App code lives elsewhere.",
                        List.of(
                                "Workflow agent that keeps state across sessions: a current-work file, hooks, lessons, and a closed loop instead of goldfish memory.",
                                "Agent evaluator that grades configs on ten dimensions. Prompts are suggestions. Hooks are guarantees.",
                                "Portable coding skills (batch, simplify, loop, debug) and a README author built on Hook, Prove, Enable, Extend.",
                                "An installer so the continuity layer can drop into any repo."
                        )
                ),
                new Project(
                        "yourmarch",
                        "YourMarch",
                        "2016 to 2017",
                        "Founding member",
                        "https://yourmarch.com",
                        null,
                        "A nonpartisan civic social network for India. The bet: let people who care about issues debate in the open and influence the policies that shape daily life. The idea was right; the timing was early.",
                        List.of(
                                "Discussion and debate tools aimed at citizens, with locality-level sentiment for institutions.",
                                "Taught what does not stick when the street itself is broken: the scar tissue behind the Notebook."
                        )
                ),
                new Project(
                        "trippe",
                        "Trippe World",
                        "2021 to 2023",
                        "Software Developer & founding member",
                        "/work/trippe",
                        null,
                        "Travel-commerce platform: travelers, local guides, and trip stories. Spring Boot microservices (auth, content, recommendation, communicator), React/Redux web, Android, PostgreSQL, Redis, AWS.",
                        List.of(
                                "JWT auth (OTP, password, Google), trip planning and locations, S3 media, community/ratings, and a feed ranked from post-view signals.",
                                "PostgreSQL JSONB GIN indexes cut tag search from 8,934ms to 34ms on a million trip records. Redis persona feeds took the same path to 5ms.",
                                "Thursday 7pm cache warmup before a 5x weekend-planning spike (500 concurrent). Team of four. Mixpanel funnels for what to ship next."
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
                        "CareFabric, Java, Spring Boot, OpenSearch, FHIR, AWS",
                        null,
                        List.of(
                                "Platform team on CareFabric, Netsmart's healthcare interoperability layer: ~3 billion clinical messages per month (~1,150/sec, 24/7). A careless schema field or EAGER fetch is a cost paid billions of times.",
                                "Owned a Dec 2025 OpenSearch production incident: cluster yellow, ~70 unassigned replica shards, JVM heap 92-98%, CircuitBreakerExceptions on fielddata. Queries with no sort defaulted to _id, which loaded every document ID into heap. One-line paging change to default _doc (Lucene insertion order, zero fielddata). After deploy: heap under 80%, 0 unassigned replicas, cluster green, circuit breakers gone. Explicit orderBy queries were untouched.",
                                "Production on-call, 14 rotations in 12 months (about every 4 weeks). Closed 9 production Inbox defects, including same-day paired backend and admin-UI releases, and re-diagnosed one that passed lower environments but failed in prod.",
                                "Led migration of the Inbox Admin Tool from hand-maintained REST onto CF-SDK: Java 21 and Spring Boot 3.x, eight staged changes to prove the contract on a few flows, then deleted the old endpoints.",
                                "Delivered 14 FHIR clinical resources (CareTeam, CarePlan, Coverage, Condition, Goal, ServiceRequest, Specimen, MedicationRequest, MedicationDispense, and others) as first-class data-store citizens: SDK contracts, OpenSearch schemas, search, then transport, in strict repo order.",
                                "Shipped patient-name search on an OpenSearch inbox index by resolving names at ingestion, not query time, then a full re-index promoted through development, QA, and production.",
                                "Implemented FHIR's _lastUpdated search parameter across a multi-repository data store. Closed an unmerged 74-repository PR, then split schema and query into two reviewable changes."
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
                        "Java, JGraphT, PostgreSQL, Elasticsearch",
                        null,
                        List.of(
                                "Modeled an Elasticsearch to PostgreSQL migration as a DAG in Java (JGraphT plus Postgres catalog queries): nodes were indexes/document types mapped to tables, edges were foreign keys and nested-object dependencies, then topological sort for load order so children never landed before parents.",
                                "Detected cycles with DFS and strongly connected components (self-referencing tables). For those, temporarily disabled foreign keys, loaded the data, then re-enabled constraints with validation.",
                                "Flattened nested Elasticsearch arrays into junction tables and turned document refs / embedded objects into normalized tables with foreign keys. Validated with record counts, checksums, and relationship integrity, with rollback on failure.",
                                "Ran independent DAG levels in parallel (about 60% of tables), cutting estimated migration time from 3 days to 18 hours with no foreign-key violations on load.",
                                "Improved response times ~30% by tracing distributed bottlenecks and tuning JVM heap during heavy transform cycles.",
                                "Stood up ephemeral databases so integration tests ran at unit-test speed; ~90% coverage and fewer regressions in production deploys."
                        )
                ),
                new Role(
                        "credit-saison",
                        "Credit Saison India",
                        "Software Developer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Feb 2024",
                        "Oct 2024",
                        "Saison Omni, Java, Spring Boot",
                        "https://creditsaison.in/",
                        List.of(
                                "Delivered backend services on Saison Omni, Credit Saison's co-lending platform connecting banks and NBFCs from origination through loan management.",
                                "Integrated partner loan-management systems so applications, credit decisions, and servicing events stayed in sync on the shared book.",
                                "Shipped partner-facing APIs and data contracts for partnership lending, then stayed with partner go-lives in production.",
                                "Worked in a regulated NBFC environment where identity, credit, and repayment data had to stay consistent across originator and co-lender."
                        )
                ),
                new Role(
                        "trippe",
                        "Trippe World",
                        "Software Developer & founding member",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Jul 2021",
                        "Nov 2023",
                        "Java, Spring Boot, PostgreSQL, Redis, React, Android, AWS",
                        "/work/trippe",
                        List.of(
                                "Designed and architected an itinerary planner using a Bloom filter.",
                                "Built the backend for a travel-commerce product that matched travelers with local guides and shared trips: trip planning, locations, diaries, community, tags, ratings, and referrals, from first service to production.",
                                "Split the platform into Spring Boot microservices (Java 8, JPA): auth, content, recommendation, and communicator (email, SMS, push), plus a shared Java SDK so services talked over REST (Retrofit) instead of a monolith.",
                                "Indexed the trip store in PostgreSQL for how people actually search: JSONB GIN on tags (no full-text search), partial indexes that skip soft-deletes, GiST for nearby locations. On a million trip records, tag search 8,934ms to 34ms, location queries 5,247ms to 89ms, a user's trips 2,156ms to 12ms.",
                                "Put Redis in front of persona feeds (cache-aside, 15 travel personas, TTL by how fast that tag set changes). Same tag path 8,934ms to 5ms, 85-95% hit rate, 80% fewer direct database queries. Scheduled warmup Thursday 7pm before the 5x weekend-planning spike (500 concurrent), with a database fallback if Redis missed.",
                                "Auth issued JWTs (Spring Security + JJWT) with OTP, password, and Google sign-in. Led a team of four across a React/Redux web app and an Android client. Recommendation work lifted engagement ~25%."
                        )
                ),
                new Role(
                        "commscope",
                        "CommScope",
                        "Software Engineer",
                        RoleKind.FULL_TIME,
                        "Bengaluru",
                        "Jul 2020",
                        "Jul 2021",
                        "OpenID Connect, REST, GitHub",
                        null,
                        List.of(
                                "Built and maintained web applications and APIs with OpenID Connect, REST, and GitHub-centered version control.",
                                "Worked with product, design, and clients on auth, performance, and delivery."
                        )
                ),
                new Role(
                        "ceph",
                        "Ceph",
                        "Open source contributor",
                        RoleKind.OPEN_SOURCE,
                        "Remote",
                        "2020",
                        "2021",
                        null,
                        "https://github.com/ceph/ceph",
                        List.of(
                                "Contributed to Ceph, the open-source distributed object, block, and file storage platform."
                        )
                ),
                new Role(
                        "redhat",
                        "Red Hat",
                        "Software Engineering Intern",
                        RoleKind.INTERN,
                        "Bengaluru",
                        "Jan 2020",
                        "Jul 2020",
                        null,
                        null,
                        List.of(
                                "Software engineering intern in the last two semesters at NIE. Containers / OpenShift track (DO180)."
                        )
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
                                "Civic engagement platform for people to share and debate the issues they care about: nonpartisan, locality-aware, built before smartphone civic habit was widespread in India."
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
                        "Summer intern, organizing team",
                        RoleKind.INTERN,
                        "Mysuru",
                        "Nov 2015",
                        "Feb 2016",
                        null,
                        null,
                        List.of(
                                "Organizing team, Uber Challenge Mysore 2016. Event operations, not a software-engineering internship."
                        )
                )
        );

        List<Writing> writing = List.of(
                new Writing(
                        "Building a 100/100 Workflow Agent From Scratch",
                        "https://medium.com/@anrgbndhu/building-a-100-100-workflow-agent-from-scratch-bb2a0f6c95d6",
                        "28 Jul 2026",
                        "Medium",
                        "A practical blueprint for an agent that remembers where you left off across sessions: state files, the 80-line rule, and a closed loop instead of goldfish memory.",
                        List.of("ai-agent", "claude-code", "software-development", "kiro")
                ),
                new Writing(
                        "We Had 20+ AI Agents and No Way to Know If They Were Any Good. So I Built One.",
                        "https://medium.com/@anrgbndhu/we-had-20-ai-agents-and-no-way-to-know-if-they-were-any-good-so-i-built-one-8f522ce07a37",
                        "2 Jul 2026",
                        "Medium",
                        "How I graded a fleet of AI agents like a Forward Deployed Engineer: ten dimensions, and what the scores taught me about agents that actually work.",
                        List.of("ai-agent", "performance", "claude")
                ),
                new Writing(
                        "My Prompt Engineering Journey: Teaching an AI to Debug a Legacy Enterprise System",
                        "https://medium.com/@anrgbndhu/my-prompt-engineering-journey-teaching-an-ai-to-debug-a-legacy-enterprise-system-730e0d39be97",
                        "8 Jun 2026",
                        "Medium",
                        "Iteratively refining prompts until an assistant became a useful pair-programmer on a multi-service healthcare codebase.",
                        List.of("prompt-engineering", "backend-development", "technical-debt", "software-engineering")
                )
        );

        List<Hackathon> hackathons = List.of(
                new Hackathon(
                        "Yes Bank Datathon",
                        "Winner",
                        "Techkriti 2019 · IIT Kanpur",
                        "May 2019",
                        "Yes Bank's campus datathon at IIT Kanpur's technical festival. Banking data problems, a working model, and a judged pitch. First place.",
                        List.of(
                                "Built and presented a data-science solution on a live banking brief: features, a model, and a pitch to Yes Bank and Techkriti judges.",
                                "One stop on Yes Bank's 2019 campus datathon series with the IITs, aimed at data-driven product ideas for retail banking."
                        )
                ),
                new Hackathon(
                        "techNIEks Hackathon",
                        "Winner",
                        "NISB · NIE Mysuru",
                        "25 Feb 2018",
                        "One-day campus hackathon at The National Institute of Engineering, hosted by the IEEE student branch during techNIEks. First place.",
                        List.of(
                                "24-hour sprint: a live brief, a working prototype, and a demo in front of campus judges.",
                                "IEEE NISB fest. The same student branch I had volunteered with on sponsorship in 2015-16."
                        )
                )
        );

        return new SiteModel(
                person,
                projects,
                writing,
                "https://medium.com/@anrgbndhu",
                experience,
                hackathons,
                new Education(
                        "The National Institute of Engineering, Mysuru",
                        "Bachelor of Engineering, Computer Science",
                        "Aug 2016",
                        "Jun 2020"
                ),
                List.of(
                        "Introduction to Containers, Kubernetes, and Red Hat OpenShift (DO180)",
                        "Blockchain for Business (Hyperledger, LinuxFoundationX)",
                        "Web Security: OAuth and OpenID Connect",
                        "Git: Branches, Merges, and Remotes",
                        "Entrepreneurship 1: Developing the Opportunity"
                ),
                List.of(
                        "Amazon Q and MCP servers wired to local docs and codebase context. Routine coding time down ~25% on the Netsmart team."
                )
        );
    }

    private static CaseStudy trippeStudy() {
        return new CaseStudy(
                "trippe",
                "Trippe World",
                "2021 to 2023",
                "Software Developer & founding member",
                "Social travel: travelers, local guides, and trip stories. These notes come from the Redis caching guide and the indexing strategy written for the platform. Numbers below are from those documents, not invented for this page.",
                List.of(
                        new CaseStudyStat("Tag search", "8,934ms to 5ms with Redis (34ms with indexes alone)"),
                        new CaseStudyStat("Persona feed cache", "85-95% hit rate"),
                        new CaseStudyStat("Thursday night", "5x traffic, 500 concurrent"),
                        new CaseStudyStat("Index bench", "1 million trip records")
                ),
                List.of(
                        new CaseStudyBlock(
                                "How people actually search",
                                List.of(
                                        "Most traffic is reads: browsing trips, searching tags, opening feeds. Writes (new trips, likes, comments) are the rest. Tag search is about 70% of discovery. Thursday night, when people plan the weekend, traffic is about 5x a normal evening.",
                                        "Without indexes, a million trip records made tag search take 8,934ms. Location queries 5,247ms. A user's own trips 2,156ms. Load tests at 500 concurrent users fell over after about 50."
                                )
                        ),
                        new CaseStudyBlock(
                                "Indexes first",
                                List.of(
                                        "PostgreSQL, indexed for those query patterns. JSONB tags with a GIN containment index. Composite and partial indexes that skip soft-deleted rows. GiST on trip locations for nearby points. Popular-trip and budget filters on the columns the homepage actually uses.",
                                        "We did not add full-text search on title and description. Tags covered about 90% of discovery, and Elasticsearch stayed a later option. After the indexes: tag search 34ms, location 89ms, a user's trips 12ms, popular trips 28ms."
                                )
                        ),
                        new CaseStudyBlock(
                                "Then Redis on 15 personas",
                                List.of(
                                        "Cache-aside in front of the feed: check Redis, on miss build from the database, store with a TTL. Fifteen travel personas (adventure, food, beach, culture, family, budget, luxury, solo, and the rest), each mapped to a tag set, each with its own TTL depending on how fast that content moves.",
                                        "A scheduled job warms those feeds every hour during the day, and again at 7pm on Thursday before the spike. If Redis errors, the request falls back to PostgreSQL. Tag search on a warm persona feed: 5ms. Cache hit rate 85-95%. About 80% fewer direct database queries."
                                )
                        ),
                        new CaseStudyBlock(
                                "The rest of the stack",
                                List.of(
                                        "Spring Boot microservices: auth (JWT with OTP, password, Google), content (trips, S3 media, social graph), recommendation (feed from post-view signals), communicator (email, SMS, push). React/Redux web, Android, AWS. Team of four."
                                )
                        )
                )
        );
    }
}
