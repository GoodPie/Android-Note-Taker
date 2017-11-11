package britton.brandyn.notetaking;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import britton.brandyn.notetaking.helpers.NotesDatabase;

import static britton.brandyn.notetaking.helpers.NotesDatabase.MIGRATION_1_2;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB = "migration_test";

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                NotesDatabase.class.getCanonicalName(), new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void Migrate1To2() throws IOException {
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        String testTitle = "test";
        String testDesc = "test desc";
        String testThumb = null;
        int testCompleted = 0;

        // Create insert values
        ContentValues insertValues = new ContentValues();
        insertValues.put("title", testTitle);
        insertValues.put("description", testDesc);
        insertValues.putNull("thumbnail");
        insertValues.put("completed", 0);

        long id = db.insert("notes", 0, insertValues);
        db.close();

        // Reopen with 2 and test migration process
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true,  MIGRATION_1_2);
        Cursor cursor = db.query("SELECT * FROM notes WHERE id=" + id);
        cursor.moveToFirst();

        // Get values and test
        String title = cursor.getString(cursor.getColumnIndex("title"));
        assertEquals(title, testTitle);

        String desc = cursor.getString(cursor.getColumnIndex("description"));
        assertEquals(desc, testDesc);

        String thumb = cursor.getString(cursor.getColumnIndex("thumbnail"));
        assertEquals(thumb, testThumb);

        int completed = cursor.getInt(cursor.getColumnIndex("completed"));
        assertEquals(completed, testCompleted);

        // Test new values which should default to -1
        long dateCreated = cursor.getLong(cursor.getColumnIndex("time_created"));
        assertEquals(dateCreated, -1);

        long dateUpdated = cursor.getLong(cursor.getColumnIndex("time_updated"));
        assertEquals(dateUpdated, -1);

        cursor.close();
        db.close();

    }

}
