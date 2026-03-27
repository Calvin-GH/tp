package seedu.cardcollector.command;

public class RemoveCardByNameCommand extends Command {
    private final String targetName;

    public RemoveCardByNameCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        var ui = context.getUi();
        var inventory = context.getTargetList();
        boolean removed = inventory.removeCardByName(targetName);

        if (removed) {
            ui.printRemoveByNameSuccess(targetName, inventory);
        } else {
            ui.printCardNotFound(targetName);
        }

        return new CommandResult(false);
    }
}
