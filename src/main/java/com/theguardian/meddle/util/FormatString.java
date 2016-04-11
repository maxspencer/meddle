package com.theguardian.meddle.util;

import java.util.Locale;

/**
 * Encapsulates a format string that you'd use with {@link String#format(String, Object...)} so you
 * can call that method more naturally, e.g. <code>MY_FORMAT.format(arg1, arg2)</code>
 */
public final class FormatString {

    private final String formatString;

    public FormatString(final String formatString) {
        this.formatString = formatString;
    }

    public String getFormatString() {
        return this.formatString;
    }

    public String format(Object... args) {
        return String.format(formatString, args);
    }

    public String format(Locale locale, Object... args) {
        return String.format(locale, formatString, args);
    }

}
