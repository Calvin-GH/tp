package seedu.cardcollector;

public class UploadUndoState {
    private AppState backupState;

    public void saveBackup(AppState state) {
        assert state != null : "Backup state should not be null";
        backupState = state.deepCopy();
    }

    public boolean hasBackup() {
        return backupState != null;
    }

    public AppState getBackup() {
        return backupState == null ? null : backupState.deepCopy();
    }

    public void clear() {
        backupState = null;
    }
}
