package britton.brandyn.notetaking.interfaces;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import britton.brandyn.notetaking.models.NoteItem;

@Dao
public interface NoteItemDoa {

    @Query("SELECT * FROM notes WHERE id=:id")
    public NoteItem getSingleNote(int id);

    @Query("SELECT * FROM NOTES")
    public List<NoteItem> getAllNotes();

    @Insert
    public void insertNotes(NoteItem... notes);

    @Update
    public void updateSingleNote(NoteItem note);

    @Update
    public void updateNotes(NoteItem... notes);

    @Delete
    public void deleteSingleNote(NoteItem item);

    @Delete
    public void deleteNotes(NoteItem... notes);

}
