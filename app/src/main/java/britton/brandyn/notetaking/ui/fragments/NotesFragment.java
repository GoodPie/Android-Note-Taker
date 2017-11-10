package britton.brandyn.notetaking.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.models.NoteItem;
import britton.brandyn.notetaking.ui.activities.MainActivity;
import britton.brandyn.notetaking.ui.adapters.NoteArrayAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";

    private Context mContext;
    private NoteArrayAdapter mAdapter;
    private ListView mListView;

    public NotesFragment() {
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Override the default menu and load the one relevant to the new note
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                ((MainActivity) mContext).loadFragment(NewNoteFragment.newInstance(),
                        NewNoteFragment.TAG, true);
                return true;
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Get the context
        mContext = getActivity().getApplicationContext();

        // Show the floating action button
        ((MainActivity) getActivity()).showFAB();

        // Load views
        mListView = (ListView) view.findViewById(R.id.lv_notes);
        mAdapter = new NoteArrayAdapter(mContext, R.layout.note_list_item, new ArrayList<NoteItem>());
        mListView.setAdapter(mAdapter);
        new LoadNotesTask().execute();

        return view;
    }

    private class LoadNotesTask extends AsyncTask<Void, Void, List<NoteItem>> {

        @Override
        protected List<NoteItem> doInBackground(Void... voids) {
            return ((MainActivity) getActivity()).getDatabase().doaAccess().getAllNotes();
        }

        @Override
        protected void onPostExecute(List<NoteItem> results) {
            mAdapter.setData(new ArrayList<NoteItem>(results));
        }
    }
}
