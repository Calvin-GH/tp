package seedu.cardcollector.command;

public class RemoveCardByIndexCommand extends Command {
    private final int targetIndex;

    public RemoveCardByIndexCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        if (targetIndex < 0 || targetIndex >= inventory.getSize()) {
            ui.printInvalidIndex();
            return new CommandResult(false);
        }
        inventory.removeCardByIndex(targetIndex);
        ui.printRemoved(inventory,targetIndex);
        return new CommandResult(false);
    }
}
