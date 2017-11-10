package britton.brandyn.notetaking.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.models.NoteItem;
import britton.brandyn.notetaking.ui.activities.MainActivity;

public class NewNoteFragment extends Fragment {

    public static final String TAG = "NewNoteFragment";

    private Context mContext;

    private EditText mNewNoteTitleET;
    private EditText mNewNoteDescET;

    public NewNoteFragment() {
        // Required empty public constructor
    }

    public static NewNoteFragment newInstance() {
        return new NewNoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();

        // Enable the options menu to set custom options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        // Hide the floating action button
        ((MainActivity) getActivity()).hideFAB();

        // Get views
        mNewNoteTitleET = (EditText) view.findViewById(R.id.et_new_note_title);
        mNewNoteDescET = (EditText) view.findViewById(R.id.et_new_note_desc);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Override the default menu and load the one relevant to the new note
        inflater.inflate(R.menu.menu_add_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm_add_note:
                saveNote();
                return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void saveNote() {
        if (!validateTitle())
            return; // Note valid title so show error

        String title = mNewNoteTitleET.getText().toString();
        String description = mNewNoteDescET.getText().toString();

        NoteItem newNote = new NoteItem(title, description, null);
        new CreateNewNote().execute(newNote);
    }

    private boolean validateTitle()
    {
        boolean valid = true;
        String title = mNewNoteTitleET.getText().toString();
        if (title.trim().length() <= 0) {
            mNewNoteTitleET.setError("Title can't be empty");
            valid = false;
        } else if (title.length() >= NoteItem.MAX_TITLE_LENGTH) {
            mNewNoteTitleET.setError("Title should be less than " + NoteItem.MAX_TITLE_LENGTH + " characters");
            valid = false;
        }

        return valid;
    }

    private class CreateNewNote extends AsyncTask<NoteItem, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(NoteItem... notes) {
            ((MainActivity) getActivity()).getDatabase().doaAccess().insertNotes(notes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((MainActivity) getActivity()).loadFragment(NotesFragment.newInstance(), NotesFragment.TAG, false);
        }


    }

}
