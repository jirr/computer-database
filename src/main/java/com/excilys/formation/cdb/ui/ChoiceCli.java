package com.excilys.formation.cdb.ui;

public enum ChoiceCli {
    LIST_COMPUTER("1"),
    LIST_COMPANY("2"),
    COMPUTER_DETAIL("3"),
    CREATE_COMPUTER("4"),
    UPDATE_COMPUTER("5"),
    DELETE_COMPUTER("6"),
    STOP_APP("7"),
    NEXT_PAGE("n"),
    PREVIOUS_PAGE("p"),
    FIRST_PAGE("f"),
    LAST_PAGE("l"),
    QUIT_PAGE("q"),
    DEFAULT("null");

    private String key;

    /**
     * @param key The key is a string that match an action
     */
    ChoiceCli(String key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to find
     * @return ChoiceCli the label corresponding to the key
     */
    public static ChoiceCli getById(String key) {
        for (ChoiceCli e : values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        return DEFAULT;
    }
}
