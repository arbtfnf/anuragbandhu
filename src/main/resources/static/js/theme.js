(() => {
  const root = document.documentElement;
  const stored = localStorage.getItem("theme");
  root.dataset.theme = stored === "night" ? "night" : "day";

  document.addEventListener("click", (event) => {
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

  if (new URLSearchParams(window.location.search).get("download") === "1") {
    const gate = document.getElementById("resume-gate");
    if (gate && typeof gate.showModal === "function") {
      const source = document.getElementById("resume-gate-source");
      if (source) source.value = "resume-link";
      gate.showModal();
    }
  }
})();
