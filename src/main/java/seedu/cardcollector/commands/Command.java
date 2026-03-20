package seedu.cardcollector.commands;

import seedu.cardcollector.CardsList;
import seedu.cardcollector.Ui;

public abstract class Command {
    public abstract void execute(Ui ui, CardsList inventory);
}
