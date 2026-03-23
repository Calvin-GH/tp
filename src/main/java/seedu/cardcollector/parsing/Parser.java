package seedu.cardcollector.parsing;

import seedu.cardcollector.CardHistoryType;
import seedu.cardcollector.command.AddCommand;
import seedu.cardcollector.command.RemoveCardByNameCommand;
import seedu.cardcollector.command.RemoveCardByIndexCommand;
import seedu.cardcollector.command.Command;
import seedu.cardcollector.command.HistoryCommand;
import seedu.cardcollector.exception.ParseInvalidArgumentException;
import seedu.cardcollector.exception.ParseUnknownCommandException;

import java.util.UUID;

public class Parser {
    private static final String REGEX_WHITESPACES = "\\s+";

    private static final String KEYWORD_HISTORY_COMMAND = "history";
    private static final String KEYWORD_ADD_COMMAND = "add";
    private static final String KEYWORD_REMOVE_INDEX_COMMAND = "removeindex";
    private static final String KEYWORD_REMOVE_NAME_COMMAND = "removename";

    private static final String[] USAGE_HISTORY_COMMAND = {
        "history [added | modified | removed] [NUMBER | all]",
        "history added"
    };

    public Command parse(String input) throws
            ParseUnknownCommandException,
            ParseInvalidArgumentException {
        // Split by one or more spaces
        String[] parts = input.split(REGEX_WHITESPACES, 2);
        String commandKeyword = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandKeyword) {
        case KEYWORD_HISTORY_COMMAND -> {
            return handleHistory(arguments);
        }
        case KEYWORD_ADD_COMMAND -> {
            return handleAdd(arguments);
        }
        case KEYWORD_REMOVE_INDEX_COMMAND -> {
            return handleRemoveByIndex(arguments);
        }
        case KEYWORD_REMOVE_NAME_COMMAND -> {
            return handleRemoveByName(arguments);
        }
        default -> throw new ParseUnknownCommandException(commandKeyword);
        }
    }

    private static int getMaxDisplayCount(String[] split, String[] usage) throws ParseInvalidArgumentException {
        int maxDisplayCount = -1;

        if (split.length <= 1) {
            return maxDisplayCount;
        }

        String maxDisplayCountString = split[1];
        try {
            int i = Integer.parseInt(maxDisplayCountString);

            if (i < 1) {
                throw new ParseInvalidArgumentException("Display count must be at least 1", usage);
            }

            maxDisplayCount = i;
        } catch (NumberFormatException e) {
            if ("all".startsWith(maxDisplayCountString)) {
                maxDisplayCount = Integer.MAX_VALUE;
            } else {
                throw new ParseInvalidArgumentException("Display count invalid", usage);
            }
        }

        return maxDisplayCount;
    }

    private Command handleAdd(String args) throws ParseInvalidArgumentException{
        try {
            String name = args.split("/n")[1].split("/q|/p|/id")[0].trim();
            int quantity = Integer.parseInt(args.split("/q")[1].split("/n|/p|/id")[0].trim());
            float price = Float.parseFloat(args.split("/p")[1].split("/n|/q|/id")[0].trim());

            UUID uid = null;
            if (args.contains("/id")) {
                String uidString = args.split("/id")[1].split("/n|/q|/p")[0].trim();
                uid = UUID.fromString(uidString);
            }
            return new AddCommand(uid,name,quantity,price);
        } catch (Exception e) {
            throw new ParseInvalidArgumentException("Invalid add format",
                    new String[] {"add /n NAME /q QTY /p PRICE [/id UUID]"});
        }
    }

    private Command handleRemoveByIndex(String args) throws ParseInvalidArgumentException {
        try {
            int index = Integer.parseInt(args.trim()) - 1;
            return new RemoveCardByIndexCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseInvalidArgumentException("Index must be a valid integer",
                    new String[]{"removeindex INDEX"});
        }
    }

    private Command handleRemoveByName(String args) throws ParseInvalidArgumentException {
        if (args.isBlank()) {
            throw new ParseInvalidArgumentException("Name must be provided",
                    new String[]{"removename NAME"});
        }
        return new RemoveCardByNameCommand(args.trim());
    }

    /**
     * Handles the "history" command by displaying different types of inventory change history.
     * The format of the argument is [added | modified | removed] [NUMBER | all]
     * Argument matching is intentionally fuzzy and case-insensitive for fast usage
     * For example, input starting with "a" will
     * match "added", "m" will match "modified", and "r" will match "removed".
     *
     * @param arguments The command argument that determines which history type to display.
     */
    private Command handleHistory(String arguments) throws ParseInvalidArgumentException {
        if (arguments.isBlank()) {
            throw new ParseInvalidArgumentException("Argument must be provided!", USAGE_HISTORY_COMMAND);
        }

        String lowercaseArguments = arguments.trim().toLowerCase();
        String[] split = lowercaseArguments.split(REGEX_WHITESPACES, 2);  // Split by one or more spaces

        String historyType = split[0];

        int maxDisplayCount = getMaxDisplayCount(split, USAGE_HISTORY_COMMAND);

        if (CardHistoryType.ADDED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.ADDED, maxDisplayCount);
        } else if (CardHistoryType.MODIFIED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.MODIFIED, maxDisplayCount);
        } else if (CardHistoryType.REMOVED.getName().startsWith(historyType)) {
            return new HistoryCommand(CardHistoryType.REMOVED, maxDisplayCount);
        } else {
            throw new ParseInvalidArgumentException("Unknown history argument!", USAGE_HISTORY_COMMAND);
        }
    }
}
