# Anurag Rakesh Bandhu

Spring Boot + Thymeleaf personal site. One Java catalog drives the page, the one-page resume, and `/api`.

This is a **separate repo** from The Bangalore Notebook. Notebook workflow agent files (`.workflow/` in that project) do not apply here.

## Run locally

Need **Java 21+** (this machine has 25) and Maven is bundled as `./mvnw`.

```bash
cd /Users/anuragbandhu/localDev/anuragbandhu
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

| URL | What |
| --- | --- |
| `/` | Site |
| `/resume` | One-page preview |
| `/resume.tex` | LaTeX download |
| `/api` | JSON |

```bash
./mvnw test
```

Docker (optional):

```bash
docker build -t anuragbandhu .
docker run --rm -p 8080:8080 anuragbandhu
```

## GitHub → live site

1. Push to `main` (CI runs tests; an image is published to GitHub Container Registry).
2. **One-time:** in [Render](https://render.com) create a **Web Service**, connect `arbtfnf/anuragbandhu`, runtime Docker. Render reads `render.yaml` and redeploys on every push to `main`.
3. Point a domain (e.g. `anuragbandhu.com`) at the Render URL.

Until Render is connected, push updates GitHub and CI only — not a public website.
