(() => {
  const root = document.documentElement;
  const stored = localStorage.getItem("theme");
  root.dataset.theme = stored === "day" ? "day" : "night";

  const closeNav = () => {
    document.querySelectorAll(".chrome-top.is-nav-open").forEach((header) => {
      header.classList.remove("is-nav-open");
      const toggle = header.querySelector("[data-nav-toggle]");
      if (toggle) {
        toggle.setAttribute("aria-expanded", "false");
        toggle.setAttribute("aria-label", "Open menu");
      }
    });
  };

  document.addEventListener("click", (event) => {
    const navToggle = event.target.closest("[data-nav-toggle]");
    if (navToggle) {
      const header = navToggle.closest(".chrome-top");
      if (!header) return;
      const open = !header.classList.contains("is-nav-open");
      header.classList.toggle("is-nav-open", open);
      navToggle.setAttribute("aria-expanded", open ? "true" : "false");
      navToggle.setAttribute("aria-label", open ? "Close menu" : "Open menu");
      return;
    }

    if (event.target.closest(".nav a")) {
      closeNav();
    }

    const button = event.target.closest("[data-theme-toggle]");
    if (button) {
      const next = root.dataset.theme === "night" ? "day" : "night";
      root.dataset.theme = next;
      localStorage.setItem("theme", next);
      return;
    }

    const closer = event.target.closest("[data-gate-close]");
    if (closer) {
      const gate = document.getElementById("resume-gate");
      if (gate && typeof gate.close === "function") gate.close();
      return;
    }

    const trigger = event.target.closest("[data-resume-download]");
    if (!trigger) return;
    event.preventDefault();
    const gate = document.getElementById("resume-gate");
    if (!gate || typeof gate.showModal !== "function") {
      window.location.href = "/resume?download=1";
      return;
    }
    const source = document.getElementById("resume-gate-source");
    if (source) {
      source.value = window.location.pathname.indexOf("/resume") === 0 ? "resume" : "home";
    }
    gate.showModal();
    const email = gate.querySelector('input[name="email"]');
    if (email) email.focus();
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") closeNav();
  });
})();
