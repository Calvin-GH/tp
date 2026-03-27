package seedu.cardcollector.command;

public abstract class Command {
    public abstract CommandResult execute(CommandContext context);
}
