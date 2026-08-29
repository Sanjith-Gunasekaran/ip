package heisenberg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/** Interprets a user command and provides its parsed values. */
public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HHmm", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    private final String[] parts;
    private final CommandType command;

    public Parser(String input) {
        if (input.trim().isEmpty()) {
            throw new InvalidCommandException("Unknown command was given.");
        }

        parts = input.trim().split("\\s+");
        try {
            command = CommandType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Unknown command was given.");
        }
    }

    public CommandType getCommand() {
        return command;
    }

    public void requireNoArguments() {
        if (parts.length != 1) {
            throw new InvalidFormatException("Command is formatted incorrectly.");
        }
    }

    public int getTaskNumber() {
        if (parts.length != 2) {
            throw new InvalidFormatException("Command is formatted incorrectly.");
        }

        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Command is formatted incorrectly.");
        }
    }

    public String getKeyword() {
        if (parts.length != 2) {
            throw new InvalidFormatException("Command is formatted incorrectly.");
        }
        return parts[1];
    }

    public String getDescription() {
        int end = switch (command) {
        case DEADLINE -> findMarker("/by");
        case EVENT -> findMarker("/from");
        default -> parts.length;
        };

        if (end == -1) {
            end = parts.length;
        }
        return joinParts(1, end);
    }

    public LocalDateTime getDeadlineDateTime() {
        int byIndex = requireMarker("/by");
        return parseDateTime(joinParts(byIndex + 1, parts.length));
    }

    public LocalDateTime getEventFromDateTime() {
        int fromIndex = requireMarker("/from");
        int toIndex = requireMarker("/to");
        if (toIndex < fromIndex) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return parseDateTime(joinParts(fromIndex + 1, toIndex));
    }

    public LocalDateTime getEventToDateTime() {
        int fromIndex = requireMarker("/from");
        int toIndex = requireMarker("/to");
        if (toIndex < fromIndex) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return parseDateTime(joinParts(toIndex + 1, parts.length));
    }

    private int requireMarker(String marker) {
        int index = findMarker(marker);
        if (index == -1) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return index;
    }

    private int findMarker(String marker) {
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    private String joinParts(int start, int end) {
        StringBuilder value = new StringBuilder();
        for (int i = start; i < end; i++) {
            value.append(parts[i]).append(" ");
        }

        String result = value.toString().trim();
        if (result.isEmpty()) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return result;
    }

    private LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value, DATE_TIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Date/time must use yyyy-MM-dd HHmm format.");
        }
    }
}
