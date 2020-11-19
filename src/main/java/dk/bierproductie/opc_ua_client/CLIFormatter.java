package dk.bierproductie.opc_ua_client;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CLIFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format("{%s} %s \n", record.getLevel(), formatMessage(record));
    }
}
