package dk.bierproductie.opc_ua_client;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CLIFormatter extends Formatter {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public String format(LogRecord record) {
        if (record.getLevel() == Level.WARNING){
            return String.format("{%s} %s%s \n", record.getLevel(), formatMessage(record), ANSI_RED);
        } else {
            return String.format("{%s} %s%s \n", record.getLevel(), formatMessage(record), ANSI_WHITE);
        }
    }
}
