package britton.brandyn.notetaking.helpers;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import britton.brandyn.notetaking.interfaces.NoteItemDoa;
import britton.brandyn.notetaking.models.NoteItem;

@Database(entities = {NoteItem.class}, version = 2)
public abstract class NotesDatabase extends RoomDatabase{
    public abstract NoteItemDoa doaAccess();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notes ADD time_created INTEGER NOT NULL DEFAULT -1");
            database.execSQL("ALTER TABLE notes ADD time_updated INTEGER NOT NULL DEFAULT -1");
        }
    };
}
