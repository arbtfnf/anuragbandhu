package com.anuragbandhu.site.resume;

final class LatexEscaper {

    private LatexEscaper() {}

    static String text(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }
        StringBuilder out = new StringBuilder(raw.length() + 16);
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '\\' -> out.append("\\textbackslash{}");
                case '&' -> out.append("\\&");
                case '%' -> out.append("\\%");
                case '$' -> out.append("\\$");
                case '#' -> out.append("\\#");
                case '_' -> out.append("\\_");
                case '{' -> out.append("\\{");
                case '}' -> out.append("\\}");
                case '~' -> out.append("\\textasciitilde{}");
                case '^' -> out.append("\\textasciicircum{}");
                case '—' -> out.append("---");
                case '–' -> out.append("--");
                case '→' -> out.append("$\\rightarrow$");
                case '·' -> out.append(" / ");
                default -> out.append(c);
            }
        }
        return out.toString();
    }
}
