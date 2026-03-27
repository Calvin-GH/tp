package seedu.cardcollector.command;

public class ExitCommand extends Command {
    @Override
    public CommandResult execute(CommandContext context) {
        context.getUi().printExit();
        return new CommandResult(true);
    }
}
