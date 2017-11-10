package britton.brandyn.notetaking.ui.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.helpers.NotesDatabase;
import britton.brandyn.notetaking.ui.fragments.NewNoteFragment;
import britton.brandyn.notetaking.ui.fragments.NoteFragment;
import britton.brandyn.notetaking.ui.fragments.NotesHomeFragment;

public class MainActivity extends AppCompatActivity implements NoteFragment.NoteFragmentInterface {

    private NotesDatabase mDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init database
        mDatabase = getDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(NewNoteFragment.newInstance(), NewNoteFragment.TAG, true);
            }
        });

        // Load the initial fragment
        loadFragment(NotesHomeFragment.newInstance(), null, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_add_note:
                loadFragment(NewNoteFragment.newInstance(),
                        NewNoteFragment.TAG, true);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Loads a fragment to view
     *
     * @param newFragment   The fragment to load
     * @param tag           Tag related to the fragment
     * @param addToBack     Whether to add to back stack or not (tag ignore if false)
     */
    public void loadFragment(Fragment newFragment, String tag, boolean addToBack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment);
        if (addToBack) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    /**
     * Shows the floating action button
     */
    public void showFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    /**
     * Hides the floating action button
     */
    public void hideFAB()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }

    /**
     * Creates or retrieves the current database
     *
     * @return Database object
     */
    public NotesDatabase getDatabase()
    {
        if (mDatabase == null) {
            // Create database if it doesn't already exist
            mDatabase = Room.databaseBuilder(getApplicationContext(),
                    NotesDatabase.class, "notes-db").build();
        }

        return mDatabase;
    }

    @Override
    public void OnNoteClicked(int noteId) {
        // Create new notes fragment
        NoteFragment fragment = NoteFragment.newInstance(noteId);
        String tag = NoteFragment.TAG;

        // Load fragment to view
        loadFragment(fragment, tag, true);
    }
}
