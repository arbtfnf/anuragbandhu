(() => {
  const root = document.documentElement;
  const stored = localStorage.getItem("theme");
  root.dataset.theme = stored === "night" ? "night" : "day";

  document.addEventListener("click", (event) => {
    const button = event.target.closest("[data-theme-toggle]");
    if (!button) return;
    const next = root.dataset.theme === "night" ? "day" : "night";
    root.dataset.theme = next;
    localStorage.setItem("theme", next);
  });
})();
