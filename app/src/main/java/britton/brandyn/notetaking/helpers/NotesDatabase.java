package britton.brandyn.notetaking.helpers;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import britton.brandyn.notetaking.interfaces.NoteItemDoa;
import britton.brandyn.notetaking.models.NoteItem;

@Database(entities = {NoteItem.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase{
    public abstract NoteItemDoa doaAccess();

    // TODO: Add migration when required
}
