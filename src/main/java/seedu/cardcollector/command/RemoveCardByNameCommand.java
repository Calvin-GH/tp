package seedu.cardcollector.command;

import seedu.cardcollector.CardsList;
import seedu.cardcollector.Ui;

public class RemoveCardByNameCommand extends Command {
    private final String targetName;

    public RemoveCardByNameCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Ui ui, CardsList inventory) {
        boolean removed = inventory.removeCardByName(targetName);

        if (removed) {
            System.out.println("Card \"" + targetName + "\" removed successfully");
        }
        return new CommandResult(false);
    }
}
