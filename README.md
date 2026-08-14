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

## Deploy (Vercel)

The site is Spring Boot. Vercel runs it from `Dockerfile.vercel` (nginx on `$PORT`, JVM behind it).

Live: [https://anuragbandhu.vercel.app](https://anuragbandhu.vercel.app)

```bash
npx vercel login
npx vercel --prod --yes
```

Or connect [github.com/arbtfnf/anuragbandhu](https://github.com/arbtfnf/anuragbandhu) in the Vercel dashboard. Every push to `main` then rebuilds.

First request after idle can take a few seconds (JVM cold start). After that it stays warm for a few minutes.

## GitHub CI

Push to `main` runs tests and publishes an image to GitHub Container Registry. That image is for Docker/Render, not required for Vercel.
