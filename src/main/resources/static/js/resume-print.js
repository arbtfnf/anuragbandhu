(() => {
  if (!document.body.classList.contains("resume-body")) return;

  let savedTitle = document.title;

  window.addEventListener("beforeprint", () => {
    savedTitle = document.title;
    document.title = "\u00A0";
  });

  window.addEventListener("afterprint", () => {
    document.title = savedTitle;
  });
})();
