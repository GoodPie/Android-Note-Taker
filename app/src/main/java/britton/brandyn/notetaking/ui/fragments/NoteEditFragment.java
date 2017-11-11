package britton.brandyn.notetaking.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.models.NoteItem;
import britton.brandyn.notetaking.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteEditFragmentInteractListener} interface
 * to handle interaction events.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    public static final String TAG = "NoteEditFragment";

    private static final String ARG_NOTE_ID = "paramNoteId";

    private int mNoteId;
    private NoteItem mNote = null;

    private EditText mTitleET;
    private EditText mDescET;


    private NoteEditFragmentInteractListener mListener;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    public static NoteEditFragment newInstance(int noteId) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NOTE_ID, noteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteId = getArguments().getInt(ARG_NOTE_ID);
        }

        // Enable the options menu to set custom options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create view
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);

        // Hide the floating action button
        ((MainActivity) getActivity()).hideFAB();

        // Load views
        mTitleET = (EditText) view.findViewById(R.id.et_new_note_title);
        mDescET = (EditText) view.findViewById(R.id.et_new_note_desc);

        // Load note info
        new LoadNoteTask().execute(mNoteId);

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
                mNote.setTitle(mTitleET.getText().toString());
                mNote.setDescription(mDescET.getText().toString());
                new UpdateNoteTask().execute(mNote);
                return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NoteEditFragmentInteractListener) {
            mListener = (NoteEditFragmentInteractListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NoteEditFragmentInteractListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class LoadNoteTask extends AsyncTask<Integer, Void, NoteItem> {

        @Override
        protected NoteItem doInBackground(Integer... ids) {
            return ((MainActivity) getActivity()).getDatabase().doaAccess().getSingleNote(ids[0]);
        }

        @Override
        protected void onPostExecute(NoteItem noteItem) {
            if (noteItem != null) {
                mTitleET.setText(noteItem.getTitle());
                mDescET.setText(noteItem.getDescription());
                mNote = noteItem;
            }
        }
    }

    private class UpdateNoteTask extends AsyncTask<NoteItem, Void, Void> {

        @Override
        protected Void doInBackground(NoteItem... noteItems) {
            NoteItem note = noteItems[0];
            ((MainActivity) getActivity()).getDatabase().doaAccess().updateSingleNote(note);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((MainActivity) getActivity()).loadFragment(NotesHomeFragment.newInstance(), null, false);
        }
    }

    public interface NoteEditFragmentInteractListener {
        void OnEditNote(int noteId);
    }
}
