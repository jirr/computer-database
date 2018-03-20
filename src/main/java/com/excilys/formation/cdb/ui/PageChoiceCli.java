package com.excilys.formation.cdb.ui;

public enum PageChoiceCli {
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
    PageChoiceCli(String key) {
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
     * @return PageChoiceCli the label corresponding to the key
     */
    public static PageChoiceCli getById(String key) {
        for (PageChoiceCli e : values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        return DEFAULT;
    }
}
