package seedu.cardcollector.command;

import java.io.IOException;
import java.nio.file.Path;

import seedu.cardcollector.AppState;
import seedu.cardcollector.Storage;

public class DownloadCommand extends Command {
    private final Path targetPath;

    public DownloadCommand(Path targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public CommandResult execute(CommandContext context) {
        try {
            Storage exportStorage = new Storage(targetPath);
            exportStorage.save(new AppState(context.getInventory(), context.getWishlist()));
            context.getUi().printDownloadSuccess(targetPath);
        } catch (IOException e) {
            context.getUi().printStorageTransferError("download", targetPath, e.getMessage());
        }
        return new CommandResult(false);
    }
}
