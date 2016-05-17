package net.dujiaju.pnotepad.model;

/**
 * Created by lilujia on 16/5/17.
 */
public class Folder {
    private String mTitle;
    private int mID;

    public Folder() {
    }

    public Folder(int id, String title) {
        this.mID = id;
        this.mTitle = title;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
