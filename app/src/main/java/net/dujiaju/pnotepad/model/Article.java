package net.dujiaju.pnotepad.model;

import java.util.UUID;

/**
 * Created by lilujia on 16/5/16.
 */
public class Article {
    private String mTitle;
    private UUID mID;

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
