package seedu.cardcollector.command;

import seedu.cardcollector.CardHistoryType;

public class HistoryCommand extends Command {
    private final CardHistoryType historyType;
    private final int maxDisplayCount;

    public HistoryCommand(CardHistoryType historyType, int maxDisplayCount) {
        this.historyType = historyType;
        this.maxDisplayCount = maxDisplayCount;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        switch (historyType) {
        case ADDED -> ui.printAddedHistory(inventory, maxDisplayCount);
        case MODIFIED -> ui.printModifiedHistory(inventory, maxDisplayCount);
        case REMOVED -> ui.printRemovedHistory(inventory, maxDisplayCount);
        default -> {
            assert false : "Invalid history type";
        }
        }
        return new CommandResult(false);
    }
}
