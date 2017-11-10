package britton.brandyn.notetaking.ui.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.models.NoteItem;
import britton.brandyn.notetaking.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    public static final String TAG = "NoteFragment";

    private static final String ARG_NOTE_ID = "note_param";
    private int mNoteId;

    private TextView mTitleTV;
    private TextView mDescTV;

    private NoteItem mNote;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(int noteIDParam) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTE_ID, noteIDParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteId = getArguments().getInt(ARG_NOTE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_note, container, false);

        // Get the views
        mTitleTV = (TextView) view.findViewById(R.id.tv_note_display_title);
        mDescTV = (TextView) view.findViewById(R.id.tv_note_display_desc);

        // Load the info from database
        new LoadNoteTask().execute(mNoteId);

        // Show floating action button
        ((MainActivity) getActivity()).showFAB();

        // Enable options menu
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Override the default menu and load the one relevant to the new note
        inflater.inflate(R.menu.menu_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete_note:
                if (mNote != null) {
                    mNote.setCompleted(true);
                    new UpdateNoteTask().execute(mNote);
                }
                return true;
        }
        return false;
    }

    private class LoadNoteTask extends AsyncTask<Integer, Void, NoteItem> {

        @Override
        protected NoteItem doInBackground(Integer... ids) {
            NoteItem note = ((MainActivity) getActivity()).getDatabase().doaAccess().getSingleNote(ids[0]);
            return note;
        }

        @Override
        protected void onPostExecute(NoteItem noteItem) {
            if (noteItem != null) {
                mTitleTV.setText(noteItem.getTitle());
                mDescTV.setText(noteItem.getDescription());
                mNote = noteItem;
            }
        }
    }

    private class UpdateNoteTask extends AsyncTask<NoteItem, Void, Void> {

        @Override
        protected Void doInBackground(NoteItem... noteItems) {
            ((MainActivity) getActivity()).getDatabase().doaAccess().updateSingleNote(noteItems[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((MainActivity) getActivity()).loadFragment(NotesHomeFragment.newInstance(), "", false);
        }
    }

    public interface NoteFragmentInterface {
        public void OnNoteClicked(int noteId);
    }

}
