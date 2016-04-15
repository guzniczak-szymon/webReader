package com.SG;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
    private final Date dat = new Date();

    private static final String format = "[%1$td %1$tb %1$tk:%1$tM:%1$tS] [Level-%2$s]: %3$s%n";

    @Override
    public String format(LogRecord record) {
        dat.setTime(record.getMillis());

        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(format,
                dat,
                record.getLevel().getLocalizedName(),
                message,
                throwable);
    }
}

