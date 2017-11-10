package britton.brandyn.notetaking.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.security.InvalidParameterException;

import britton.brandyn.notetaking.interfaces.NoteItemDoa;

@Entity(tableName = "notes")
public class NoteItem {

    @Ignore
    public static final int MAX_TITLE_LENGTH = 140;

    // Basic note details
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String description;
    private String thumbnail;
    private boolean completed;

    public NoteItem(String title, String description, String thumbnail) {
        this.title = validateTitle(title);
        setDescription(description);
        setThumbnail(thumbnail);
        setCompleted(false);
    }

    /**
     * Switch task status
     */
    public void changeNoteStatus() {
        this.completed = !this.completed;
    }

    private String validateTitle(String inTitle) {
        // Remove any unnecessary whitespace
        inTitle = inTitle.trim();

        // Ensure title is less that the max size
        if (inTitle.length() > MAX_TITLE_LENGTH) {
            throw new InvalidParameterException("Invalid title size. Must be less than 140 characters");
        }

        return inTitle;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = validateTitle(title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
