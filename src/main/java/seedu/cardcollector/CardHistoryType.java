package seedu.cardcollector;

public enum CardHistoryType {
    ADDED("added"),
    REMOVED("removed"),
    MODIFIED("modified");

    private final String name;

    CardHistoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
