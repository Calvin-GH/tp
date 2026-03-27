package seedu.cardcollector.command;

public class ListCommand extends Command {
    @Override
    public CommandResult execute(CommandContext context) {
        context.getUi().printList(context.getTargetList());
        return new CommandResult(false);
    }
}
