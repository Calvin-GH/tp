package seedu.cardcollector.command;

public class CommandResult {
    private final boolean isExit;
    private final boolean shouldSave;

    public CommandResult(boolean isExit) {
        this(isExit, true);
    }

    public CommandResult(boolean isExit, boolean shouldSave) {
        this.isExit = isExit;
        this.shouldSave = shouldSave;
    }

    public boolean getIsExit() {
        return isExit;
    }

    public boolean shouldSave() {
        return shouldSave;
    }
}
